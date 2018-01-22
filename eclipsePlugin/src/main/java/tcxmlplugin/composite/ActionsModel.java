package tcxmlplugin.composite;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


public class ActionsModel {
	
	public static final String ALL_ACTIONS = "allActions";

	public static final String ACTION_SELECTED = "actionSelected";

	private PropertyChangeSupport propertyChangeSupport;
	
	private List<String> allActions;
	
	private String actionSelected;
	
	
	public String getActionSelected() {
		return actionSelected;
	}








	public void setActionSelected(String actionSelected) {
		propertyChangeSupport.firePropertyChange(ACTION_SELECTED, this.actionSelected,
				this.actionSelected = actionSelected);
		
	}








	public ActionsModel() {
		
		propertyChangeSupport = new PropertyChangeSupport(this);
		allActions = new ArrayList<String>();
		
	}
	
	
	
	
	



	public List<String>getAllActions() {
		return allActions;
	}


	public void setAllActions(List<String> allActions2) {
		propertyChangeSupport.firePropertyChange(ALL_ACTIONS, this.allActions,
				this.allActions = allActions2);
		
		
		
		
	}
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }










}
