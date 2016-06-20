package iT3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

class PrepTableModel extends AbstractTableModel {
	
	private UserInterface parentWindow;
	private int colCount;
	private int rows;
	private Object[][] cache;
	private String[] headers;
	
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
	  }
	public boolean isCellEditable(int row, int column) {
	    return (column ==2);
	  }
	public PrepTableModel(UserInterface ui){
		parentWindow = ui;
		
		try{
			// Execute the query and store the result set and its metadata
						// sql manager
						SQLMgr sqlm = new SQLMgr(parentWindow.getDBpath());

						ResultSet rs = (ResultSet) sqlm.returnSQLresultSet("SELECT * FROM "
								+ Util.PREP_TABLE+ " ORDER BY Item_Name");
					      ResultSetMetaData meta = rs.getMetaData();
					      
					      colCount = meta.getColumnCount();
					      cache = new Object[colCount][];
					      
					   // build the headers array with column names
					      headers = new String[colCount];
					      for (int h = 1; h <= colCount; h++) {
					        headers[h - 1] = meta.getColumnName(h);
					       
					      }
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
					    	  cache[rowCount] = data;
					    	  rowCount++;
 	  	  
					      }    
					      rows = rowCount;
					      fireTableChanged(null); // notify everyone that we have a new table.
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
	