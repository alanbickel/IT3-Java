package iT3;

import javax.swing.JPanel;
import javax.swing.JTable;

public class TableFrame extends JPanel {

	private String state; // hold state of gui 
	private JPanel controlFrame = new JPanel(); // hold GUI objects
	private UserInterface parentWindow; // reference to main window
	private JPanel tablePanel; // holds tables
	private JTable currentTable;
	
	/*classes to handle specific table structures*/
	private PrepModel PrepObj;
	
	

	
	
	public TableFrame(UserInterface ui){
		parentWindow = ui;
		setCurrentTablePanel(new JPanel());
		
		//PrepObj = new TablePrep(ui);
	}
	
	public JPanel getTablePanel(){
		return tablePanel;
	}
	
	public void setCurrentTablePanel(JPanel j){
		tablePanel = j;
	}
	public void setCurrentTable(JTable t){
		currentTable = t;
		//refresh the jpanel
		//tablePanel.removeAll();
		//tablePanel.add(t);
		
	}
	public JTable getCurrentTable(){
		return currentTable;
	}
	
	public void showCurrentTable(){
		tablePanel.setVisible(true);
	}

	public void hideCurrentTable(){
		tablePanel.setVisible(false);
	}

	public void setPrepItemObject(PrepModel p){
		PrepObj = p;
	}

	public void setPrepModel(PrepModel p){
		 
		 
		 setCurrentTable(new JTable(p));
		 
		 // clear jpanel then add table to jpanel
		 getTablePanel().removeAll();
		 getTablePanel().add(getCurrentTable());
	}
	public void updateComponentState(String str) {
		setState(str);
		switch(state){
		case "INIT":{
			getTablePanel().setVisible(false);
			break;
		}
		case Util.VIEW_PREP:
		case Util.VIEW_EMPLOYEES:
		case Util.VIEW_CORRECTIVE:
		default :{
			getTablePanel().setVisible(true);
			break;
		}
		}
	}
	public void setState(String s){
		state = s;
	}
}
