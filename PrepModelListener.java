package iT3;
import iT3.PrepModel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/*listen to changes made on prep items table*/
public class PrepModelListener implements TableModelListener {
	
	public void tableChanged(TableModelEvent e) {
		
		int row = e.getFirstRow();
        int column = e.getColumn();
        PrepModel model = (PrepModel)e.getSource();
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
        int itemNumber = Integer.parseInt(model.getValueAt(row, 0).toString()) ;
        
        System.out.println("on item # "+itemNumber );
        
        //pass to database
        SQLMgr sqlm = new SQLMgr(dbPath);
        String condition = "Item_Index = "+itemNumber;
        
        int updateSuccessful = sqlm.simpleIntValueUpdate("Prep_Items", "Active_Item",itemActiveState, condition );
        System.out.println("result: "+updateSuccessful);
	}

}
