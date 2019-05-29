package tcxml.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class LibraryModel extends AbstractModel{





	
	public static final String ALL_LIBRARY = "allLibraries";

	public static final String LIBRARY_SELECTED = "librarySelected";

	
	
	private List<String> allLibraries;
	
	private String librarySelected;
	
	
	public String getLibrarySelected() {
		return librarySelected;
	}








	public void setLibrarySelected(String actionSelected) {
		propertyChangeSupport.firePropertyChange(LIBRARY_SELECTED, this.librarySelected,
				this.librarySelected = actionSelected);
		
	}








	public LibraryModel() {
		
		super();
		allLibraries = new ArrayList<String>();
		
	}
	
	
	
	
	



	public List<String>getAllLibraries() {
		return allLibraries;
	}


	public void setAllLibraries(List<String> allLibraries2) {
		propertyChangeSupport.firePropertyChange(ALL_LIBRARY, this.allLibraries,
				this.allLibraries = allLibraries2);
		
		
		
		
	}









	
	
	






	
	
	
	
	
	
	
	
	
	

}
