package iT3;

import javax.swing.JOptionPane;

public class MenuHelper {

	private String helpType;
	
	public MenuHelper(){
		
	}
	
	public void setHelpType(String type){
		helpType = type;
	}
	
	public String getHelpType(){
		return helpType;
	}
	
	public void showHelper(){
		switch(helpType){
		case Util.SYNC_HELP:{
			
			String helpText = 	"*Select 'Import Data Records' to log temperature recordings from the SD card to \n'"
								+"permanent storage in the database\n\n\n"
								+"*Select 'Refresh Control Board Settings' from the Sync Menu to export active prep items,\n"
								+" corrective actions, and employees to the SD card. Once the SD card is placed in the\n "
								+"control board, you will have the option to refresh settings and make these changes available.\n"
								+"\n\nSelect 'Purge SD Records' to clear all temperature data from the SD card.\n"
								+"Make sure you've imported the most recent temperature data before purging, as these records\n"
								+"CANNOT be recovered once deleted!";
			JOptionPane.showMessageDialog(null, helpText, "SD Menu Help", 1);
		}
			break;
			
		}
	}
}
