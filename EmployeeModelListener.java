package iT3;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EmployeeModelListener  implements TableModelListener {
	
	public void tableChanged(TableModelEvent e) {
		
		int row = e.getFirstRow();
        int column = e.getColumn();
        EmployeeModel model = (EmployeeModel)e.getSource();
        String dbPath  = model.getParent().getDBpath();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        //get the new value based on user selection
        String activeChoice = data.toString();
        int itemActiveState;
        if(activeChoice.equals("false")){
        	itemActiveState = 0;
        }else {
        	itemActiveState = 1;
        }
        System.out.println("User selected: "+activeChoice );

        //get item number to modify
        int employeeIndex = Integer.parseInt(model.getValueAt(row, 0).toString()) ;
        
        System.out.println("on employee index "+employeeIndex );
        
        //pass to database
        SQLMgr sqlm = new SQLMgr(dbPath);
        String condition = "Emp_Index = "+employeeIndex;
        
        int updateSuccessful = sqlm.simpleIntValueUpdate("Employees", "Active_Emp",itemActiveState, condition );
        System.out.println("result: "+updateSuccessful);
	}
}
