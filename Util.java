package iT3;

import iT3.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Util {

	/* SYSTEM PUBLIC CONSTANTS */
	
	//db table names
	public static final String PREP_TABLE = "Prep_Items";
	public static final String EMP_TABLE = "Employees";
	public static final String CORRECTIVE_TBL = "Corrective_Action";

	//date stuff
	public static String CURRENT_DATE = getCurrentDateString();
	public static String PREVIOUS_DAY = getYesterdayDateString();
	
	//menu button types
	public static final String INIT = "INIT";
	public static final String ADD_ITEM = "addItem";
	public static final String VIEW_PREP = "VIEW_PREP";
	public static final String ADD_EMPLOYEE = "addEmployee";
	public static final String VIEW_EMPLOYEES = "VIEW_EMPLOYEES";
	public static final String ADD_CORRECTIVE = "addCorrective";
	public static final String VIEW_CORRECTIVE = "VIEW_CORRECTIVE";
	public static final String SYNC_SD = "SYNC_SD";

	
	/*help type requests*/
	public static final String SYNC_HELP = "sync";
	public static final String DATA_HELP = "data";
	public static final String ITEM_HELP = "item";
	public static final String ACTION_HELP = "action";
	public static final String EMPLOYEE_HELP = "employee";
	
	
	//SD card file names
	public static final String ITEM_FILE = "itm.csv";
	public static final String EMPLOYEE_FILE = "emp.csv";
	public static final String ACTION_FILE = "act.csv";
	public static final String DATA_LOG = "log.csv";


	public static boolean isGoodFileName(String inputFileName){
		boolean isGood = false;
		if(inputFileName.matches(".*[^\\w -._].*") ){
			isGood = false;
		} else {
			isGood = true;
		}
		
		return isGood;
	}
	public static boolean isGoodItemName(String inputFileName){
		boolean isGood = false;
		if (!inputFileName.matches("[a-zA-Z0-9.\\s]*")){
			isGood = false;
		} else {
			isGood = true;
		}
		System.out.println("result: "+isGood);
		return isGood;
	}
	
	public static int countRowsForQuery(String query, String filePath){
		//System.out.println("query "+ query);
		int result = 0;
		try{
		SQLMgr sqlm = new SQLMgr(filePath);
		ResultSet rs = (ResultSet) sqlm.returnSQLresultSet(query);
		while(rs.next()){
			result++;
		}
		//System.out.println("query rows:"+ result);
		
		} catch (Exception e){
			System.out.println(e.toString());
			System.out.println("db connect error: countRowsForQuery() ");
		}
		return result;
	}
	
	public static boolean appendStringToFile(String input, String filePath) {
		// File file = new File(filePath);
		FileWriter fw = null;
		ExceptionHandler eH = null; // handle exceptions gracefully
		boolean result = false;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(input);
			result = true;

		} catch (Exception e) {
			eH = new ExceptionHandler("File I/O Error", e);
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				eH = new ExceptionHandler("File I/O Error", e);
			}
		}
		return result;
	}

	public static File[] getDirectoryContents(String dirName) {
		File dir = new File(dirName);

		File[] directoryListing = dir.listFiles(); // get list of files in
													// directory

		return directoryListing;
	}

	public static void refreshDate() {

		CURRENT_DATE = getCurrentDateString();
		PREVIOUS_DAY = getYesterdayDateString();
	}

	public static String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}

	public static String getCurrentDateString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE);
		return dateFormat.format(cal.getTime());
	}

	
	public static String[] readFileToArray(String fileName) {

		BufferedReader fileIn = null; // read buffer
		String[] contents = new String[20]; // hold file contents
		String line;
		int i = 0;

		try {

			fileIn = new BufferedReader(new FileReader(fileName)); // file
																	// stream
																	// obj.

			while ((line = fileIn.readLine()) != null) {
				contents[i] = line;
				i++;
			}

		} catch (IOException e) {

			ExceptionHandler eH = new ExceptionHandler("Unable to read file: "
					+ fileName, e);

		} finally {

			try {
				fileIn.close();
			} catch (IOException e) {

				ExceptionHandler eH = new ExceptionHandler(
						"Unable to close file: " + fileName, e);

			}

		}
		return contents;
	}

	public static void createDirectory(String directory) {
		File newUserDir = new File(directory);
		// if the directory does not exist, create it
		if (!newUserDir.exists()) {

			try {
				newUserDir.mkdir();

			} catch (SecurityException se) {

				ExceptionHandler eH = new ExceptionHandler(
						"Unable to create user directory", se);
			}
		}
	}


	public static String getMonthNameFromStringNumber(String mon) {
		String textMonth = "";

		switch (mon) {

		case "01":
			textMonth = "January";
			break;
		case "02":
			textMonth = "February";
			break;
		case "03":
			textMonth = "March";
			break;
		case "04":
			textMonth = "April";
			break;
		case "05":
			textMonth = "May";
			break;
		case "06":
			textMonth = "June";
			break;
		case "07":
			textMonth = "July";
			break;
		case "08":
			textMonth = "August";
			break;
		case "09":
			textMonth = "September";
			break;
		case "10":
			textMonth = "October";
			break;
		case "11":
			textMonth = "November";
			break;
		case "12":
			textMonth = "December";
			break;
		}

		return textMonth;
	}

	public static String getStringMonthNumberFromName(String mon) {
		String monthNum = "";

		switch (mon) {

		case "January":
			monthNum = "01";
			break;
		case "February":
			monthNum = "02";
			break;
		case "March":
			monthNum = "03";
			break;
		case "April":
			monthNum = "04";
			break;
		case "May":
			monthNum = "05";
			break;
		case "June":
			monthNum = "06";
			break;
		case "July":
			monthNum = "07";
			break;
		case "August":
			monthNum = "08";
			break;
		case "September":
			monthNum = "09";
			break;
		case "October":
			monthNum = "10";
			break;
		case "November":
			monthNum = "11";
			break;
		case "December":
			monthNum = "12";
			break;
		}

		return monthNum;
	}
}
