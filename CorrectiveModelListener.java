package iT3;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class CorrectiveModelListener  implements TableModelListener {
	
	public void tableChanged(TableModelEvent e) {
		
		int row = e.getFirstRow();
        int column = e.getColumn();
        CorrectiveModel   model = (CorrectiveModel)e.getSource();
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

        //get item number to modify
        int ActionIndex = Integer.parseInt(model.getValueAt(row, 0).toString()) ;
        
        
        //pass to database
        SQLMgr sqlm = new SQLMgr(dbPath);
        String condition = "Action_Index = "+ActionIndex;
        
        int updateSuccessful = sqlm.simpleIntValueUpdate("Corrective_Action", "Active_Action",itemActiveState, condition );
        System.out.println("result: "+updateSuccessful);
	}
}
