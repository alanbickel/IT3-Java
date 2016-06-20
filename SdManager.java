package iT3;

import java.io.File;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.filechooser.*;
import javax.swing.text.html.HTMLDocument.Iterator;

public class SdManager {
	
	private FileSystemView fileSystem;
	private File[] fileArray;
	private Vector<File> dirOptions; 
	private File SD_Directory;
	
	public SdManager(){
		fileSystem = FileSystemView.getFileSystemView();
		fileArray = fileSystem.getRoots();
		dirOptions = new Vector<File>();
		
	      File[] f = File.listRoots(); // get list of directory roots on the computer
	      
	      //iterate through directories
	      for (int i = 0; i < f.length; i++){
	    	  
	    	  //if it is indeed a drive
	    	  if(fileSystem.isDrive(f[i])){
	    		  // can we read from it?
	    		  if(f[i].canRead()){
	    			  // can we write to it?
	    			  if(f[i].canWrite()){ 
	    					// add it to the list of potentials to show the user
		    				  dirOptions.add(f[i]);
	    			  }
	    		  }
	    		  
	    	  }
	        /*
	    	  System.out.println("Drive: " + f[i]);
	        System.out.println("Display name: " + fileSystem.getSystemDisplayName(f[i]));
	        System.out.println("Is drive: " + fileSystem.isDrive(f[i]));
	        System.out.println("Is floppy: " + fileSystem.isFloppyDrive(f[i]));
	        System.out.println("Readable: " + f[i].canRead());
	        System.out.println("Writable: " + f[i].canWrite());
	        */
	      }
	      // get the directory user wants
	       int vectorIndex = getStorageDirectoryFromUser();
	    // set the directory for future reference
	       if(!(vectorIndex == -1)){
	    	   SD_Directory = dirOptions.elementAt(vectorIndex);
	       }
	       System.out.println("Vector: "+ dirOptions.elementAt(vectorIndex));
	       System.out.println("directory: "+ SD_Directory); 
	       
	       boolean uploadSuccessful = false;  // set to true if all file upload methods return true.
	       
	       boolean itemsLoaded = SDconf.writeItemListToSD(SD_Directory);
	       
	       if(itemsLoaded){
	    	   
	    	   boolean employeesLoaded = SDconf.writeEmployeeListToSD(SD_Directory);
	    	   
	    	   if(employeesLoaded){
	    		   
	    		   boolean actionsLoaded = SDconf.writeActionListToSD(SD_Directory);
	    		   
	    		   if(actionsLoaded){
	    			   
	    			   uploadSuccessful = true;
	    			   
	    		   }
	    	   }
	       
	       } 
	       
	       if(uploadSuccessful){
	    	   JOptionPane.showMessageDialog(null, "SD sync successful!");
	       } else {
	    	   JOptionPane.showMessageDialog(null, "Sync attempt unsucessful.");
	       }
	       
		}
		
	public int getStorageDirectoryFromUser(){
		
		// get index of array user selected
		int userInput = JOptionPane.showOptionDialog(null, 
												"Select SD storage drive:\n", 
												"Drive Select", 
												JOptionPane.OK_CANCEL_OPTION, 
												JOptionPane.QUESTION_MESSAGE, 
												null,
												getStringArrayOfDirectories(),
												dirOptions.elementAt(0));
		System.out.println("Chose: "+ userInput);
		
		return userInput;
	}
	
	
	public String[] getStringArrayOfDirectories(){
		
		String[] files = new String[dirOptions.size()];
		
		for (int i = 0; i < dirOptions.size(); i++){
			
			files[i] = (String)dirOptions.elementAt(i).toString();
			System.out.println("files:"+files[i]);
		}
		return files;
	}
	
	public void writeSystemDataToSDcard(){
		
	}
	}

	
