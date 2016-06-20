package iT3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * utility functions for general db queries
 * */
public class QueryUtils {

	/* check if a table exists from a given db connection */
	public static boolean tableExists(DataAccessObject dao, String tableName) {

		/*copy connection to keep parent connection alive*/
		Connection methodConnection = dao.getConnection();
		boolean result = true; // flag set default positive

		try {

			DatabaseMetaData dbmd = methodConnection.getMetaData(); // meta data about tables
														// from DB
			if(dbmd == null){
				System.out.println("null db result");
			}

			ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),
					null); // query for table

			if (!(rs.next())) { // only if successful query returns false can we
								// be sure the table doesn't exist

				result = false;
				try {
					methodConnection.close(); // close connection before 
					
					return result;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error closing database connection.");
					
					e.printStackTrace();
					return result;
				}
				
			}

			/*handle exceptions and close connection*/
		} catch (SQLException e) {
			System.out.println(e.toString());
			System.out.println("error in db query 'tableExists()'.");
		} finally {
			try {
				methodConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
				System.out.println("Error closing database connection.");
				e.printStackTrace();
			}
		}
		return result;
	}

	
}
