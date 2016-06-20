package iT3;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.omg.CORBA.portable.ApplicationException;

/**
 * Maintain database connections outside User Interface Classes.
 *
 * */
public class DataAccessObject {
	
	private Connection connection; 
    private Statement statement;
    private String path;
    
    /*pass full db path to DAO*/
    public DataAccessObject(String p) { 
    	path = p;
    	
    }
    public DataAccessObject(){}
    
    public Connection getConnection(){
    	return createConnection();
    }
    
    public void createDatabase(String dbName)  {
        String query = "CREATE DATABASE IF NOT EXISTS " + dbName;
        try {
            connection = createConnection();
            
           statement = connection.createStatement();
            statement.executeUpdate(query);
            SQLWarning warning = statement.getWarnings();
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        } finally { // close connections
        	
        	closeConnection();
        	
        	closeStatement();
            }
        }
    

    /*create mysql connection*/
    private Connection createConnection() {
        Connection connection = null;
        try {
            String strUrl = "jdbc:derby:it3db;create=true;";

            connection = DriverManager.getConnection(strUrl);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
            System.out.println(e.toString());
        }
        return connection;
    }
    
    public void closeConnection(){
    	if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
    	}
    }
    
    public void closeStatement(){
    	if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
	
}
