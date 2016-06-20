package iT3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

public class CorrectiveModel extends AbstractTableModel {

	private UserInterface parentWindow;
	private int colCount;
	private int rows;
	private Object[][] cache;
	private String[] headers;

	
	/*construct*/
	public CorrectiveModel(UserInterface ui){
		parentWindow = ui;
		
		try{
			SQLMgr sqlm = new SQLMgr(parentWindow.getDBpath());
			/*get row count for this query*/
			String query = "SELECT * FROM "+ Util.CORRECTIVE_TBL;
			
			rows = Util.countRowsForQuery(query, parentWindow.getDBpath());
			
			ResultSet rs = (ResultSet) sqlm.returnSQLresultSet("SELECT * FROM "
					+ Util.CORRECTIVE_TBL+ " ORDER BY Action_Name");
		      ResultSetMetaData meta = rs.getMetaData();
		      
		      colCount = meta.getColumnCount();
		      cache = new Object[rows][];
		      
		      headers = new String[] {"Action Index", "Corrective Action", "Active"};
		      
		  //  fill the cache with the records from  query.
		      int rowCount = 0;
		      while (rs.next()) {
		    	  Object[] data = new Object[colCount];
		    	  for (int i = 0; i < colCount; i++) {
		    		  
		    		  if(i == 2){
		    		  if(rs.getString(i + 1).equals("1")){
		    			  data[i] =new Boolean(true);
		    			  
	        		  } else {
	        			  data[i] =new Boolean(false);
	        		  }
		    		  } else {
		    			  data[i] = rs.getString(i + 1);
		    			  
		    		  }
		    		  
		    	  }
		    	  //System.out.println("rows:"+rowCount);
		    	  cache[rowCount] = data;
		    	  rowCount++;
 
		      }    	
		      fireTableChanged(null); // notify everyone that we have a new table.
		      
		}
		catch(Exception e){ // cope with exceptions
			ExceptionHandler eH = new ExceptionHandler("Corrective Action Table Model Error", e);
		}
	}
	
	
	public UserInterface getParent(){
		return parentWindow;
	}
	public int getColumnCount() {
	    return colCount;
	  }
	public String getColumnName(int column) {
	    return headers[column];
	  }
	public int getRowCount() {
	    return cache.length;
	  }
	public Object getValueAt(int row, int column) {
	    return cache[row][column];
	  }
	public Class getColumnClass(int column) {
	    return (getValueAt(0, column).getClass());
	  }
	public void setValueAt(Object value, int row, int column) {
	    cache[row][column] = value;
	    fireTableCellUpdated(row, column);

	  }
	public boolean isCellEditable(int row, int column) {
	    return (column == 2);
	  }
}
