package iT3;
import iT3.MenuListener;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class MenuBar {

	private JPanel controlFrame = new JPanel(); // hold link objects
	
	private UserInterface parentWindow; // reference to main window
		
	
	/*toggle component visibility, on state change*/
	private String state;
	
	
	/*Accessors*/
	public UserInterface getParent(){
		return parentWindow;
	}
	
	/**
	 * 
	 * @param ui  pass UserInterface instance to give reference to object actions, toggle view mode
	 */
	public MenuBar(UserInterface ui){ //pass parent window
		
		parentWindow = ui;
		
		JMenuBar menubar = new JMenuBar();
		
		/* BUILD FILE MENU */
		JMenu fileMenu = new JMenu("File ");
		
		/*Build Sync menu for SD storage*/
		JMenu sync = new JMenu("Sync SD");
		
		//construct helper object
		MenuListener menuHelp = new MenuListener(Util.SYNC_HELP, getParent());
		
		JMenuItem importData = new JMenuItem("Import Data Records");
		JMenuItem exportData = new JMenuItem("Update Control Board Settings");
		MenuListener exportListener =  new MenuListener(Util.SYNC_SD,getParent() );
		exportData.addActionListener(exportListener);
		JMenuItem purgeRecords = new JMenuItem("Purge SD Records (free SD memory)");
				  purgeRecords.setForeground(Color.RED);
		JMenuItem sdHelp = new JMenuItem("Sync Help");
		sdHelp.addActionListener(menuHelp);
		
		sync.add(importData);
		sync.add(exportData);
		sync.add(new JSeparator(JSeparator.HORIZONTAL));
		sync.add(purgeRecords);
		sync.add(new JSeparator(JSeparator.HORIZONTAL));
		sync.add(sdHelp);
		
		/*add sync menu to file menu*/
		fileMenu.add(sync);
		
		/*add exit option to file menu*/
		JMenuItem exit = new JMenuItem("Exit"); // exit option
		fileMenu.add(exit);
		
		/*build data records menu*/
		JMenu recordMenu = new JMenu("Data Records");
		JMenuItem allRecords = new JMenuItem("View All Recods");
		JMenuItem allViolations = new JMenuItem("View Violation History");
		JMenuItem allWarnings = new JMenuItem("View Predictive Warning History");
		
		/*submenu for searching records*/
		JMenu searchRecords = new JMenu("Search");
		JMenuItem searchDate = new JMenuItem("By Date");
		JMenuItem searchItem = new JMenuItem("By Item");
		JMenuItem searchKeyword = new JMenuItem("Keyword Search");
		searchRecords.add(searchDate);
		searchRecords.add(new JSeparator(JSeparator.HORIZONTAL));
		searchRecords.add(searchItem);
		searchRecords.add(new JSeparator(JSeparator.HORIZONTAL));
		searchRecords.add(searchKeyword);
		
		recordMenu.add(allRecords);
		recordMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		recordMenu.add(allViolations);
		recordMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		recordMenu.add(allWarnings);
		recordMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		recordMenu.add(searchRecords);

		
		/*Build Items Menu*/
		JMenu itemMenu = new JMenu("Prep Items");
		
		MenuListener prepListener =  new MenuListener(Util.ADD_ITEM,getParent() );
		JMenuItem addItem = new JMenuItem("Add Item");
		addItem.addActionListener(prepListener);
		JMenuItem activeItem = new JMenuItem("View Active Items");
		MenuListener prepViewListener =  new MenuListener(Util.VIEW_PREP,getParent() );
		activeItem.addActionListener(prepViewListener);
		JMenuItem itemHelp = new JMenuItem("Item Help ");
		itemMenu.add(addItem);
		
		itemMenu.add(activeItem);
		itemMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		itemMenu.add(itemHelp);
		
		/*Build Corrective Actions Menu*/
		JMenu correctiveMenu = new JMenu("Corrective Actions");
		JMenuItem addCorrective = new JMenuItem("Add Action");
		MenuListener correctiveListener =  new MenuListener(Util.ADD_CORRECTIVE,getParent() );
		addCorrective.addActionListener(correctiveListener);
		JMenuItem activeAction = new JMenuItem("View Active Actions");
		MenuListener correctiveViewListener =  new MenuListener(Util.VIEW_CORRECTIVE,getParent() );
		activeAction.addActionListener(correctiveViewListener);
		JMenuItem actionHelp = new JMenuItem("Action Help ");
		correctiveMenu.add(addCorrective);
		correctiveMenu.add(activeAction);
		correctiveMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		correctiveMenu.add(actionHelp);
		
		/*Build Employee menu*/
		JMenu employeeMenu = new JMenu("Employees");
		// action listener
		MenuListener EmployeeListener =  new MenuListener(Util.ADD_EMPLOYEE,getParent() );
		JMenuItem addEmp = new JMenuItem("Add Employee");
		addEmp.addActionListener(EmployeeListener);
		JMenuItem activeEmp = new JMenuItem("View Active Employees");
		MenuListener viewEmpListener =  new MenuListener(Util.VIEW_EMPLOYEES,getParent() );
		activeEmp.addActionListener(viewEmpListener);
		JMenuItem empHelp = new JMenuItem("Employee Help ");
		employeeMenu.add(addEmp);
		employeeMenu.add(activeEmp);
		employeeMenu.add(new JSeparator(JSeparator.HORIZONTAL));
		employeeMenu.add(empHelp);
		
		
		
		
		/*add file menu to main menu*/
		menubar.add(fileMenu);
		menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		///*add sync menu to main menu*/
		//menubar.add(sync);
		//menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		/*add search to main menu*/
		menubar.add(recordMenu);
		menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		/*add item menu to main menu*/
		menubar.add(itemMenu);
		menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		/*add action menu to main menu*/
		menubar.add(correctiveMenu);
		menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		/*add action menu to main menu*/
		menubar.add(employeeMenu);
		menubar.add(new JSeparator(JSeparator.VERTICAL));// button divider
		
		/*parent component that holds menubar*/
		controlFrame.setLayout(new FlowLayout(FlowLayout.LEFT)); // left justify
		controlFrame.add(menubar);

		
	}
	
	/*toggle display for state change*/
	public void updateComponentState(String str) {
		setState(str);
		switch (state) {
		
		case "INIT": {
			controlFrame.setVisible(true);
			break;
		}
		default: {
			controlFrame.setVisible(true);
			break;
		}

		}
	}
	/*disallow outer classes to alter the state of this element.*/
	private void setState(String str){
		state = str;
	}
	
	public JPanel getMenuBar(){
		return controlFrame;
	}
}
