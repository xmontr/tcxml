package tcxml.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModel {
	
	
	
	protected PropertyChangeSupport propertyChangeSupport;
	
	
	
	
	protected AbstractModel () {
		
		propertyChangeSupport = new PropertyChangeSupport(this);
		
	}
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }
	

}
