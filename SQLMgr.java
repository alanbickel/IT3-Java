package iT3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import iT3.DataAccessObject;
import iT3.ExceptionHandler;

public class SQLMgr {
	
	private String dbPath; // hold location of DB for connection

	public SQLMgr(String path){
		
		dbPath = path;
	}
	
	public int simpleIntValueUpdate(String table,String column, int value, String condition){
		int result = 0;
		
		String query = "UPDATE "+table+" SET "+column+" = "+value+" WHERE "+condition;
		
		DataAccessObject dao = new DataAccessObject(dbPath);
		
		Connection conn = dao.getConnection();
		
		Statement stmt = null;
		
		System.out.println("Query: "+query);
		
		try{
			
			stmt = conn.createStatement(); // generate sql statement handler
			int rs = stmt.executeUpdate(query);
			System.out.println("rs = "+rs);
			result = rs;
			return result;
		} catch(Exception e) {
			System.out.println(e.toString());
			return result;
		}
		
	}
	
	public  boolean simpleInsert(String table, String column, String value){
		boolean result = false; // default to failure, only true if query successful
		
		String query = "INSERT INTO "+ table + " ( " + column + " ) VALUES ( "+ value +")";
		//System.out.println(query);
		//get connection
		DataAccessObject dao = new DataAccessObject(dbPath);
		
		Connection conn = dao.getConnection();
		
		Statement stmt = null;
		try { 	//build it if it isnt there...
			
			stmt = conn.createStatement(); // generate sql statement handler
		
		stmt.executeUpdate(query); 
		
		
		//flag return to true
		result = true;
		} 
		catch(Exception e){ // handle exception nicely
			ExceptionHandler eH = new ExceptionHandler("Database Insert Error", e);
			System.out.println(eH.toString());
		}
		
			return result;
		
	}


	public String[] getTableColumnNames(String table){

		String[] results = null;
		DatabaseMetaData metadata = null;
		
		DataAccessObject dao = new DataAccessObject(dbPath);
		
		Connection conn = dao.getConnection();
		
		Statement stmt = null;
		String query = "SELECT * FROM "+ table;
		
		
		try{
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++ ) {
			  String name = rsmd.getColumnName(i);
			  results[i] = name;
			}
			
			
		} catch(Exception e){
			ExceptionHandler eH = new ExceptionHandler("Error querying database. Method: getTableColumnNames()", e);
		}
		return results;
	}


	public Object returnSQLresultSet(String query){

		Object results = null;
		
		DataAccessObject dao = new DataAccessObject(dbPath);
		
		Connection conn = dao.getConnection();
		
		Statement stmt = null;
		
		try{
			stmt = conn.createStatement(); // generate sql statement handler
			ResultSet rs = stmt.executeQuery(query);
			
			results = rs;

		} catch(Exception e){
			ExceptionHandler eH = new ExceptionHandler("Error querying database. returnSQLresultSet() ", e);
			System.out.println("query: "+query);
		}
		return results;
	}
}
