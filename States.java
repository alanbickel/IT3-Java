package iT3;
/**
 * @author Alan
 * this enum  array returns a string value that determines the 'state' of the user interface. (i.e. toggle JPanel visibility)
 * for example 'ADD_ITEM'state trggers user prompts for adding an item to the database. 
 * Two step process for execution.  From within a given action listener, the main ui object is passed a call to 'update state'
 * specified index of thie enum array passed as a value which is then passed to each child element of the main UI object. 
 * each child element's method 'updateState' handles various states accordingly.  This way, any element can alert every other element of 
 * what action is occuring, and allows each JPanel to govern it's own activity based on the user's choices. 
 * call as States.phase.INIT.value()
 */
public class States {

	  enum phase {
		INIT {

			public String toString() {
				return "INIT";
			}
		},
		VIEW_PREP{
			public String toString(){
				return "VIEW_PREP";
			}
		}, 
		VIEW_EMPLOYEES{
			public String toString(){
				return "VIEW_EMPLOYEES";
			}
		},
		VIEW_CORRECTIVES{
			public String toString(){
				return "VIEW_CORRECTIVES";
			}
		}
	}
}
