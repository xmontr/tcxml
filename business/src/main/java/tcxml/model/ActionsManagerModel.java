package tcxml.model;

import java.util.List;

public class ActionsManagerModel extends AbstractModel {
	
	private List<String> allActions ;
	
	private String actionselectedSelected ; 
	
	public String getActionselectedSelected() {
		return actionselectedSelected;
	}

	public void setActionselectedSelected(String actionselectedSelected) {
		propertyChangeSupport.firePropertyChange(ACTION_SELECTED, this.actionselectedSelected,
				this.actionselectedSelected = actionselectedSelected);
		
	}

	public static final String ACTION_SELECTED = "actionselectedSelected";
	

	public List<String> getAllActions() {
		return allActions;
	}

	public void setAllActions(List<String> allActions) {
		propertyChangeSupport.firePropertyChange("allActions", this.allActions,
				this.allActions = allActions);
		
	}

}
