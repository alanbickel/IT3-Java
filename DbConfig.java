package iT3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import iT3.QueryUtils;


/**
 * 
 * @author Alan
 *Class checks for existence of database, and builds if not exists. 
 *default install in subdir of user home directory
 *writes tables/columns needed, and finalizes connection url for other components to use.
 */
public class DbConfig {

	//build system location for DB
	private String userHomeDir = System.getProperty("user.home", ".");
    private String systemDir = userHomeDir + "\\IT3TempTracking/.it3db";
    
    /*Table names for DB*/
    private final String itemTable = "Prep_Items";
    private final String correctionTable = "Corrective_Action";
    private final String tempTable = "Temp_Monitor";
    private final String employeeTable = "Employees";
    
    
    /*construct*/
	public DbConfig(){
		
		// Set the db system directory.
	    System.setProperty("derby.system.home", systemDir);
	    
	    // set full path for database connection within dao object
	 	DataAccessObject dao = new DataAccessObject(systemDir);
	 		
	 	/*establish database connection, this will create DB if not present on user system
	 	 * data access object handles connection specifics, and returns valid connection*/
		Connection conn = dao.getConnection();	
		
		try { // close preliminary connection
				conn.close();
			} catch (SQLException e) {
				/*TODO alert user of connnection termination issue*/
				System.out.println("Error closing database connection.");
				e.printStackTrace();
			}
			/*close MySQL statement*/
			dao.closeStatement();
			dao = null; // destroy data access object
			
			/*up next, new connection info for checking on tables in db*/
			DataAccessObject configObj = new DataAccessObject(systemDir); //new d.a.o. for configuration queries
			Connection confConn = configObj.getConnection(); // connection object for queries

			/*Next, configure needed tables in the DB. check for existence, and create as needed...*/
			
			Statement stmt = null;
			
			if(!(QueryUtils.tableExists(configObj, itemTable))){ // check for existence of prep item table
			
				try { 	//build it if it isnt there...
					
					 stmt = confConn.createStatement(); // generate sql statement handler
					
					String  itemSqlStmt = "CREATE TABLE Prep_Items (";
					itemSqlStmt +="Item_Index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ";
					itemSqlStmt += "Item_Name VARCHAR(40), ";
					itemSqlStmt += "Active_Item INT)"; 
					
					stmt.executeUpdate(itemSqlStmt); // excecute create prep item table
					
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
				
				
				if(!(QueryUtils.tableExists(configObj, correctionTable))){ // check for existence of corrective action table
					
					try { 	//build it if it isnt there...
					stmt = confConn.createStatement(); // generate sql statement handler
					
					String  actionSqlStmt = "CREATE TABLE Corrective_Action (";
					actionSqlStmt +="Action_Index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ";
					actionSqlStmt += "Action_Name VARCHAR(50), ";
					actionSqlStmt += "Active_Action INT)"; 
					
					stmt.executeUpdate(actionSqlStmt); // excecute create prep item table
					}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				}
				
				if(!(QueryUtils.tableExists(configObj, tempTable))){ // check for existence of Data record table
					
					try { 	//build it if it isnt there...
					stmt = confConn.createStatement(); // generate sql statement handler
					
					String  actionSqlStmt = "CREATE TABLE Temp_Monitor (";
					actionSqlStmt +="Record_Index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ";
					actionSqlStmt += "Item_Index VARCHAR(20), ";
					actionSqlStmt += "Timestamp TIMESTAMP, ";
					actionSqlStmt += "Temperature FLOAT, "; 
					actionSqlStmt += "Violation INT, ";
					actionSqlStmt += "Corrective_Action INT, ";
					actionSqlStmt += "Employee_Num INT, "; 
					actionSqlStmt += "Cooling_Length FLOAT )"; 

					stmt.executeUpdate(actionSqlStmt); // excecute create prep item table
					}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				}
	if(!(QueryUtils.tableExists(configObj, employeeTable))){ // check for existence of employee table
					
					try { 	//build it if it isnt there...
					stmt = confConn.createStatement(); // generate sql statement handler
					
					String  actionSqlStmt = "CREATE TABLE Employees (";
					actionSqlStmt +="Emp_Index INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ";
					actionSqlStmt += "Emp_Name VARCHAR(30), ";
					actionSqlStmt += "Active_Emp INT, ";
					actionSqlStmt += "Emp_Num INT)"; 
					
					stmt.executeUpdate(actionSqlStmt); // excecute create prep item table
					}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				}
				
				
				
			}   
			
			
	
	
	//retrieve system directory
	public String getSystemDir(){
		return systemDir;
	}
}
