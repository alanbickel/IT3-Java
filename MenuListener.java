package iT3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import iT3.Util;
import iT3.UserInterface;

/**
 * multi purpose listener class for the menu bar UI. pass which button is
 * calling ~ pass to switch in construct to define default behavior for each
 * instance
 * 
 * @author Alan
 *
 */
public class MenuListener implements ActionListener {

	private String buttonId;
	private UserInterface uiObj;
	private MenuHelper mHelp = new MenuHelper();

	public MenuListener(String type, UserInterface ui) {
		buttonId = type;
		uiObj = ui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand(); // get action command

		switch (buttonId) {
		// sync db info to sd card
		case Util.SYNC_SD : {
			SdManager sdm = new SdManager();
			
			// set static 
			
			break;
		}
		/* ADD PREP ITEM */
		case Util.ADD_ITEM: {
			/* get the name of the item to add */
			String newItem = (String) JOptionPane.showInputDialog(null,
					"Enter Item Name:",
					"Be careful, item names can't be changed...",
					JOptionPane.INFORMATION_MESSAGE);

			if (!(newItem == null)) { // make sure they've not cancelled

				if ((newItem.length() > 2)&&(newItem.length() < 21)) { // must be between 3 and 20 characters
					if (!(Util.isGoodItemName(newItem))) { // if invalid
															// characters in
															// attempted file
															// name
						JOptionPane
								.showMessageDialog(
										null,
										"Illegal item name. Only alpha-numeric characters permitted",
										"! Entry Error",
										JOptionPane.ERROR_MESSAGE);
						return;
					}
					// confirm before inserting...
					int confirm = JOptionPane.showConfirmDialog(null, "Add '"
							+ newItem + "' to database?\n"
							+ "Item names cannot be changed once logged.",
							"log item?", JOptionPane.OK_CANCEL_OPTION);

					if (confirm == 0) { // log item to DB

						// sql manager & set DB path
						SQLMgr sql = new SQLMgr(uiObj.getDBpath());

						// log to DB
						boolean insertResult = sql.simpleInsert("Prep_Items",
								"Item_Name, Active_Item", "'" + newItem
										+ "' , 1");

						if (insertResult) {

							// refresh the active items table if it exists
							TableFrame uiTableFrame = uiObj.getTableFrame();

							if (uiTableFrame != null) {
								// get tableFrame reference from parent window
								// get a new prepModel object
								PrepModel p = new PrepModel(uiObj);
								p.addTableModelListener(new PrepModelListener());
								// make a new table
								JTable table = new JTable(p);
								// pass to parent UI to refresh table jpanel
								uiObj.refreshTable(table);
							}

						} else { // something's wrong, alert user item not added
							JOptionPane.showMessageDialog(null,
									"Item not added.");
						}
					}

				} else { // don't let empty/very short string be inserted!
					JOptionPane.showMessageDialog(null,
							"Length must be between 3 and 20 characters",
							"! Entry Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		}
		/* END ADD PREP ITEM */

		/* VIEW PREP ITEMS */
		case Util.VIEW_PREP: {

			// get tableFrame reference from parent window
			TableFrame t = uiObj.getTableFrame();
			// get a new prepModel object
			PrepModel p = new PrepModel(uiObj);
			p.addTableModelListener(new PrepModelListener());
			// make a new table
			JTable table = new JTable(p);
			// pass to parent UI to refresh table jpanel
			uiObj.refreshTable(table);

			uiObj.changeUIState(Util.VIEW_PREP);
			break;
		}
		/* END VIEW PREP ITEMS */

		/* ADD EMPLOYEE */
		case Util.ADD_EMPLOYEE: {
			/* get the name of the Employee to add */
			String newEmployee = (String) JOptionPane.showInputDialog(null,
					"Enter Employee Name:", "Add employee",
					JOptionPane.INFORMATION_MESSAGE);

			if (!(newEmployee == null)) { // make sure they've not cancelled

				if (newEmployee.length() > 2) { // if they've entered something,
					// make sure it's not just a letter
					// or two
					if (!(Util.isGoodItemName(newEmployee))) { // if invalid
																// characters in
																// attempted
																// name
						JOptionPane
								.showMessageDialog(
										null,
										"Illegal employee name. Only alpha characters permitted",
										"! Entry Error",
										JOptionPane.ERROR_MESSAGE);
						return;
					}
					// get employee id number
					long empNum = Long.parseLong(JOptionPane.showInputDialog(
							null, "Enter Employee Number:", "Employee number",
							JOptionPane.INFORMATION_MESSAGE));
					// ensure there is a positive number (use long to accept
					// social security number as value(this is derby db int
					// max...)
					if ((empNum > 0) && (empNum < 2147483648L)) {

						// confirm before inserting...
						int confirm = JOptionPane
								.showConfirmDialog(
										null,
										"Add '"
												+ newEmployee
												+ "', #"
												+ empNum
												+ " to database?\n"
												+ "Employee information cannot be changed once logged.",
										"log employee?",
										JOptionPane.OK_CANCEL_OPTION);

						if (confirm == 0) { // log item to DB

							// sql manager & set DB path
							SQLMgr sql = new SQLMgr(uiObj.getDBpath());

							// log to DB
							boolean insertResult = sql.simpleInsert(
									"Employees",
									"Emp_Name, Active_Emp, Emp_Num ", "'"
											+ newEmployee + "' , 1, " + empNum);

							if (insertResult) {
								// refresh the active items table if it exists
								TableFrame uiTableFrame = uiObj.getTableFrame();

								if (uiTableFrame != null) {
									EmployeeModel eM = new EmployeeModel(uiObj);
									eM.addTableModelListener(new EmployeeModelListener());
									// make a new table
									JTable table = new JTable(eM);

									// pass to parent UI to refresh table jpanel
									uiObj.refreshTable(table);
								}

							} else { // something's wrong, alert user item not
										// added
								JOptionPane.showMessageDialog(null,
										"Employee not added.");
							}
						}
					} else {
						// warn user of invalid number entry
						JOptionPane
								.showMessageDialog(
										null,
										"Invalid entry. Employee number must be numeric value",
										"! Entry Error",
										JOptionPane.ERROR_MESSAGE);

					}

				} else { // don't let empty/very short string be inserted!
					JOptionPane.showMessageDialog(null,
							"Employee name must be at least 3 characters",
							"! Entry Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		}
		/* END ADD EMPLOYEE */

		/* VIEW EMPLOYEE */
		case Util.VIEW_EMPLOYEES: {
			// tell parent we want to look at the employees table

			// get tableFrame reference from parent window
			TableFrame t = uiObj.getTableFrame();

			// get a new employee Table Model object
			EmployeeModel eM = new EmployeeModel(uiObj);
			eM.addTableModelListener(new EmployeeModelListener());
			// make a new table
			JTable table = new JTable(eM);

			// pass to parent UI to refresh table jpanel
			uiObj.refreshTable(table);

			// t.setCurrentTable(table);
			// t.showCurrentTable();

			uiObj.changeUIState(Util.VIEW_EMPLOYEES);
			break;

		}
		/* END VIEW EMPLOYEE */

		/* ADD CORRECTIVE ACTION */

		case Util.ADD_CORRECTIVE: {
			/* get the name of the action to add */
			String newAction = (String) JOptionPane.showInputDialog(null,
					"Enter Corrective Action:",
					"Be careful, action names can't be changed...",
					JOptionPane.INFORMATION_MESSAGE);

			if (!(newAction == null)) { // make sure they've not cancelled

				if (newAction.length() > 2) { // if they've entered something,
												// make sure it's not just a
												// letter
												// or two
					if (!(Util.isGoodItemName(newAction))) { // if invalid
																// characters in
																// attempted
																// file
																// name
						JOptionPane
								.showMessageDialog(
										null,
										"Illegal action name. Only alpha-numeric characters permitted",
										"! Entry Error",
										JOptionPane.ERROR_MESSAGE);
						return;
					}
					// confirm before inserting...
					int confirm = JOptionPane.showConfirmDialog(null, "Add '"
							+ newAction + "' to database?\n"
							+ "Action names cannot be changed once logged.",
							"log action?", JOptionPane.OK_CANCEL_OPTION);

					if (confirm == 0) { // log item to DB

						// sql manager & set DB path
						SQLMgr sql = new SQLMgr(uiObj.getDBpath());

						// log to DB
						boolean insertResult = sql.simpleInsert(
								"Corrective_Action",
								"Action_Name, Active_Action", "'" + newAction
										+ "' , 1");

						if (insertResult) {

							// refresh the active items table if it exists
							TableFrame uiTableFrame = uiObj.getTableFrame();

							if (uiTableFrame != null) {
								// get tableFrame reference from parent window
								// get a new prepModel object
								CorrectiveModel c = new CorrectiveModel(uiObj);
								c.addTableModelListener(new CorrectiveModelListener());
								// make a new table
								JTable table = new JTable(c);
								// pass to parent UI to refresh table jpanel
								uiObj.refreshTable(table);

								// show the records
								uiObj.changeUIState(Util.VIEW_CORRECTIVE);
							}

						} else { // something's wrong, alert user item not added
							JOptionPane.showMessageDialog(null,
									"Action not added.");
						}
					}

				} else { // don't let empty/very short string be inserted!
					JOptionPane.showMessageDialog(null,
							"Item name must be at least 3 characters",
							"! Entry Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		}
		/* END ADD CORRECTIVE ACTION */

		/* view corrective actions */
		case Util.VIEW_CORRECTIVE: {

			// get tableFrame reference from parent window
			TableFrame t = uiObj.getTableFrame();

			// get a new employee Table Model object
			CorrectiveModel cA = new CorrectiveModel(uiObj);
			cA.addTableModelListener(new CorrectiveModelListener());
			// make a new table
			JTable table = new JTable(cA);

			// pass to parent UI to refresh table jpanel
			uiObj.refreshTable(table);

			// t.setCurrentTable(table);
			// t.showCurrentTable();

			uiObj.changeUIState(Util.VIEW_CORRECTIVE);
			break;

		}
		// menu help functions
		case Util.SYNC_HELP: {
			mHelp.setHelpType(Util.SYNC_HELP);
			mHelp.showHelper();
		}
		case Util.DATA_HELP: {

		}
		}

	}

	public void addPrepItem() {
		
		/* get New Item Name from user */
		String newItem = (String) JOptionPane.showInputDialog(null,
				"Enter Item Name:",
				"Be careful, item names can't be changed...",
				JOptionPane.INFORMATION_MESSAGE);

		if (!(newItem == null)) { // make sure they've not cancelled

			if (newItem.length() > 2) { //make sure at least 3 chars
				if (!(Util.isGoodItemName(newItem))) { // if invalid
														// characters in
														// attempted file
														// name
					JOptionPane
							.showMessageDialog(
									null,
									"Illegal item name. Only alpha-numeric characters permitted",
									"! Entry Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// confirm before inserting...
				int confirm = JOptionPane.showConfirmDialog(null, "Add '"
						+ newItem + "' to database?\n"
						+ "Item names cannot be changed once logged.",
						"log item?", JOptionPane.OK_CANCEL_OPTION);

				if (confirm == 0) { // log item to DB

					// sql manager & set DB path
					SQLMgr sql = new SQLMgr(uiObj.getDBpath());

					// log to DB
					boolean insertResult = sql.simpleInsert("Prep_Items",
							"Item_Name, Active_Item", "'" + newItem + "' , 1");

					if (insertResult) {

						// refresh the active items table if it exists
						TableFrame uiTableFrame = uiObj.getTableFrame();

						if (uiTableFrame != null) {
							// get tableFrame reference from parent window
							// get a new prepModel object
							PrepModel p = new PrepModel(uiObj);
							p.addTableModelListener(new PrepModelListener());
							// make a new table
							JTable table = new JTable(p);
							// pass to parent UI to refresh table jpanel
							uiObj.refreshTable(table);
						}

					} else { // something's wrong, alert user item not added
						JOptionPane.showMessageDialog(null, "Item not added.");
					}
				}

			} else { // don't let empty/very short string be inserted!
				JOptionPane.showMessageDialog(null,
						"Item name must be at least 3 characters",
						"! Entry Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Generic constructor for basic data table types (prep, employee, or corrective actions)
	 * pass string arg to tell what data set to retrieve from database.
	 * 
	 * @param dataType - which information we need to pull from DB
	 */
	public void setUIwindowTable(String dataType){
		
		// get tableFrame reference from parent window
					TableFrame t = uiObj.getTableFrame();
					// get a new prepModel object
					
				switch(dataType){
				
				case Util.PREP_TABLE :{
					
				}
				}
					PrepModel p = new PrepModel(uiObj);
					p.addTableModelListener(new PrepModelListener());
					// make a new table
					JTable table = new JTable(p);
					// pass to parent UI to refresh table jpanel
					uiObj.refreshTable(table);
		
		
		
	}
}
