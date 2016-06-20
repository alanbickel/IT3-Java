package iT3;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import iT3.ExceptionHandler;

public class SDconf {

	public static String DBpath;

	public static void setDbDirectoryPath(String path) {
		DBpath = path;
	}

	public static boolean writeItemListToSD(File SD_Directory) {

		boolean uploaded = false;

		// first check that we don't have too many active items
		// limits of array size on conrtol board!

		int activePrepItems = activeValuesInTable(Util.PREP_TABLE,
				"Active_Item");

		// do not allow snc if too many items in list. force user to limit
		// selections
		if (activePrepItems > 30) {
			JOptionPane
					.showMessageDialog(
							null,
							"MAXIMUM OF 30 ACTIVE PREP ITEMS EXCEEDED.\nPLEASE UPDATE THE NUMBER OF ACTIVE PREP ITEMS\nBEFORE UPLOADING",
							null, JOptionPane.ERROR_MESSAGE, null);
			return false;
		}

		DataAccessObject dao = new DataAccessObject(DBpath);

		Connection conn = dao.getConnection();

		Statement stmt = null;
		String query = "SELECT * FROM " + Util.PREP_TABLE
				+ " ORDER BY Item_Name";
		try {
			// http://stackoverflow.com/questions/18128802/convert-resultset-to-csv-file-using-java

			stmt = conn.createStatement(); // generate sql statement handler
			ResultSet rs = stmt.executeQuery(query);

			String prepFileName = SD_Directory + "/" + Util.ITEM_FILE;

			// build string to write
			String entry = "";

			while (rs.next()) {

				entry += rs.getString(2);
				entry += ",";
				entry += rs.getString(1);
				entry += "\r\n";

			}

			FileWriter fw = null;
			try {

				fw = new FileWriter(prepFileName, false);

				fw.write(entry);

				uploaded = true; // set flag to true, will check for each file
									// written

			} catch (Exception e) {
				// ExceptionHandler eH = new
				// ExceptionHandler("Error Writing Item List:\nPlease insert formatted SD card,\nensure you've selected the correct disk drive.",
				// e);
			} finally {
				try {
					fw.close();
				} catch (Exception e) {
					ExceptionHandler eH = new ExceptionHandler(
							"Error syncing prep item file\npossible corruption of .CSV\nPlease restart Sync process.\nPlease insert formatted SD card,\nSelect SD drive when prompted.",
							e);
				}
			}

		} catch (Exception e) {
			ExceptionHandler eH = new ExceptionHandler(
					"Error Writing Item List:\nPlease insert formatted SD card,\nSelect SD drive when prompted.",
					e);

		}
		return uploaded;
	}

	public static boolean writeEmployeeListToSD(File SD_Directory) {

		int activeEmployees = activeValuesInTable(Util.EMP_TABLE, "Active_Emp ");

		if (activeEmployees > 30) {
			JOptionPane
					.showMessageDialog(
							null,
							"MAXIMUM OF 30 ACTIVE EMPLOYEES EXCEEDED.\nPLEASE UPDATE THE NUMBER OF ACTIVE EMPLOYEES\nBEFORE UPLOADING",
							null, JOptionPane.ERROR_MESSAGE, null);
			return false;
		}

		boolean uploaded = false;

		DataAccessObject dao = new DataAccessObject(DBpath);
		Connection conn = dao.getConnection();

		Statement stmt = null;
		String query = "SELECT * FROM " + Util.EMP_TABLE + " ORDER BY Emp_Name";

		try {

			stmt = conn.createStatement(); // generate sql statement handler
			ResultSet rs = stmt.executeQuery(query);

			String employeeFileName = SD_Directory + "/" + Util.EMPLOYEE_FILE;

			// build string to write
			String entry = "";

			while (rs.next()) {

				entry += rs.getString(2);
				entry += ",";
				entry += rs.getString(1);
				entry += "\r\n";

			}
			FileWriter fw = null;
			try {

				fw = new FileWriter(employeeFileName, false);

				fw.write(entry);

				uploaded = true; // set flag to true, will check for each file
									// written

			} catch (Exception e) {
				// ExceptionHandler eH = new
				// ExceptionHandler("Error Writing Item List:\nPlease insert formatted SD card,\nensure you've selected the correct disk drive.",
				// e);
			} finally {
				try {
					fw.close();
				} catch (Exception e) {
					ExceptionHandler eH = new ExceptionHandler(
							"Error closing employee file\npossible corruption of .CSV\nPlease restart Sync process.",
							e);
				}
			}

		} catch (Exception e) {
			ExceptionHandler eH = new ExceptionHandler(
					"Database Connection Error: Please restart.", e);

		}
		return uploaded;
	}

	public static boolean writeActionListToSD(File SD_Directory) {

		int activeActions = activeValuesInTable(Util.CORRECTIVE_TBL,
				"Active_Action ");

		if (activeActions > 30) {
			JOptionPane
					.showMessageDialog(
							null,
							"MAXIMUM OF 30 CORRECTIVE ACTIONS EXCEEDED.\nPLEASE UPDATE THE NUMBER OF CORRECTIVE ACTIONS\nBEFORE UPLOADING",
							null, JOptionPane.ERROR_MESSAGE, null);
			return false;
		}

		boolean uploaded = false;

		DataAccessObject dao = new DataAccessObject(DBpath);
		Connection conn = dao.getConnection();

		Statement stmt = null;
		String query = "SELECT * FROM " + Util.CORRECTIVE_TBL
				+ " ORDER BY Action_Name";

		try {

			stmt = conn.createStatement(); // generate sql statement handler
			ResultSet rs = stmt.executeQuery(query);

			String actionFileName = SD_Directory + "/" + Util.ACTION_FILE;

			// build string to write
			String entry = "";

			while (rs.next()) {

				entry += rs.getString(2);
				entry += ",";
				entry += rs.getString(1);
				entry += "\r\n";

			}
			FileWriter fw = null;
			try {

				fw = new FileWriter(actionFileName, false);

				fw.write(entry);

				uploaded = true; // set flag to true, will check for each file
									// written

			} catch (Exception e) {
				// ExceptionHandler eH = new
				// ExceptionHandler("Error Writing Item List:\nPlease insert formatted SD card,\nensure you've selected the correct disk drive.",
				// e);
			} finally {
				try {
					fw.close();
				} catch (Exception e) {
					ExceptionHandler eH = new ExceptionHandler(
							"Error closing corrective action file\npossible corruption of .CSV\nPlease restart Sync process.",
							e);
				}
			}

		} catch (Exception e) {
			ExceptionHandler eH = new ExceptionHandler(
					"Database Connection Error: Please restart.", e);

		}
		return uploaded;
	}

	public static int activeValuesInTable(String tableName, String columnName) {

		int results = 0;

		DataAccessObject dao = new DataAccessObject(DBpath);
		Connection conn = dao.getConnection();

		String query = "SELECT * FROM " + tableName + " WHERE " + columnName
				+ " = 1";

		try {
			Statement stmt = conn.createStatement(); // generate sql statement
														// handler
			ResultSet rs = stmt.executeQuery(query);
			if (!(rs.next())) {
				System.out.println("NO RESULT SET FOUND!");
			} else {
				do {
					results++;
				} while (rs.next());
			}

		} catch (Exception e) {

			ExceptionHandler eH = new ExceptionHandler(
					"Error querying database. Ensure that all categories:\n(Prep, Employees, and Corrective actions)\nare populated before attempting Sync.",
					e);

		}

		return results;
	}
}
