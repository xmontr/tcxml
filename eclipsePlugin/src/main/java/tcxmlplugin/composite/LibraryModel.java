package tcxmlplugin.composite;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class LibraryModel {





	
	public static final String ALL_LIBRARY = "allLibraries";

	public static final String LIBRARY_SELECTED = "librarySelected";

	private PropertyChangeSupport propertyChangeSupport;
	
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
		
		propertyChangeSupport = new PropertyChangeSupport(this);
		allLibraries = new ArrayList<String>();
		
	}
	
	
	
	
	



	public List<String>getAllLibraries() {
		return allLibraries;
	}


	public void setAllLibraries(List<String> allLibraries2) {
		propertyChangeSupport.firePropertyChange(ALL_LIBRARY, this.allLibraries,
				this.allLibraries = allLibraries2);
		
		
		
		
	}
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }






	
	
	
	
	
	
	
	
	
	

}
