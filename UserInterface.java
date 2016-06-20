package iT3;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iT3.Util;
import iT3.MenuBar;
import iT3.States;
import iT3.TableFrame;

public class UserInterface extends JFrame {

		// MainWindow Dimensions
		private final int WINDOW_WIDTH = 800;
		private final int WINDOW_HEIGHT = 600;
		
		/*table panel dimensions*/
		private final int TABLE_WIDTH  = 500;
		private final int TABLE_HEIGHT  = 400;
		
		/*hold current phase of user interface*/
		private String PHASE;
		
		/*chold objects to control during phase changes*/
		private MenuBar MENUBAR;
		
		/*hold table panel*/
		private TableFrame TABLE_FRAME;
		
		/*direct access to table frame's jpanel object*/
		JPanel tablePanel;
		
		/*system DB path*/
		private String dbPath;
		
		
		
		/* visible components*/
		private JPanel menu; //hold menu bar object
		
		
		//get DB path
		public String getDBpath(){
			return dbPath;
		}
		
		// set db path
		public void setDBpath(String p){
			dbPath = p;
		}
		
		/*CONSTRUCT*/
		public UserInterface() {
			super("Integrated Time and Temperature Tracking"); // build frame
			setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // set size
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set window close
															// behavior
		}
		
		/*set menu bar object reference*/
		public void setMenuObject(MenuBar m){
			MENUBAR = m;
		}
		
		public void refreshTable(JTable t){
			tablePanel.removeAll();
			JScrollPane scrollpane = new JScrollPane(t);
			scrollpane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
			tablePanel.add(scrollpane);
			tablePanel.revalidate();
		}
		
		
		
		/*CHANGE STATE OF INTERFACE, TOGGLCE VIEWABLE COMPONENTS*/
		public void changeUIState(String state) {

			// keep up with potential date change while system is running
			Util.refreshDate();
			
			/*update program state*/
			PHASE = state;
			
			/*toggle component visibility */
			MENUBAR.updateComponentState(state);
			TABLE_FRAME.updateComponentState(state);
			
			
		}
		
		private void setMenu(JPanel m){
			menu = m;
		}
		private JPanel getMenu(){
			return menu;
		}
		
		private void setTableFrame(TableFrame t){
			TABLE_FRAME = t;
		}
		public TableFrame getTableFrame(){
			return TABLE_FRAME;
		}
		private void setTablePanel(JPanel j){
			tablePanel = j;
		}
		private JPanel getTablePanel(){
			return tablePanel;
		}
		
			public static void main(String[] args) {
				
				//initialize  database structure/write as necessary
				DbConfig dbc = new DbConfig();
				
				// extract user system directory
				String dbUrl = dbc.getSystemDir();
								
				
				//construct user interface
				final UserInterface UI = new UserInterface();
				
				// construct table frame
				TableFrame tF = new TableFrame(UI);
				// set UI reference to TablePrepObject
				UI.setTableFrame(tF);
				//static container(table contents wil change) from tableFrame
				UI.setTablePanel(tF.getTablePanel());  
				
				
				
				
				
				
				//construct menu bar
				MenuBar mbar = new MenuBar(UI);
				// set ui element reference
				UI.setMenu(mbar.getMenuBar());
				// set object reference
				UI.setMenuObject(mbar);
				
				/*set db path reference*/
				UI.setDBpath(dbUrl);
				// static reference for SD card writing
				SDconf.setDbDirectoryPath(dbUrl);
				
				/* Layout Manager */
				UI.setLayout(new BorderLayout());
				
				// add menu bar to window
				UI.add(UI.getMenu(), BorderLayout.NORTH);
				// add table frame to layout
				UI.add(UI.getTablePanel(), BorderLayout.CENTER);
				
				
				/* Center main window to screen */
				UI.setLocationRelativeTo(null);
				
				/*initialize system 'state' to toggle visibility of all ui elements*/
				UI.changeUIState(Util.INIT);
				
				// show the window
				UI.setVisible(true);
				
				
				
			}
		

}
