package tcxmlplugin.composite.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;

public class TestObjectView extends StepView {

	public TestObjectView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		testobjectmodel = new TestObjectModel() ;
	}
	
	
	public static class TestObjectModel {
		
		
		public List<String> getAllActions() {
			return allActions;
		}

		public void setAllActions(List<String> allActions) {
			propertyChangeSupport.firePropertyChange("allActions", this.allActions,
					this.allActions = allActions);
			
			
			
		}

		public String getSelectedAction() {
			return selectedAction;
		}

		public void setSelectedAction(String selectedAction) {
			propertyChangeSupport.firePropertyChange("selectedAction", this.selectedAction,
					this.selectedAction = selectedAction);
			
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			propertyChangeSupport.firePropertyChange("name", this.name,
					this.name = name);
			this.name = name;
		}

		private PropertyChangeSupport propertyChangeSupport;
		
		private List<String> allActions ;
		
		private String selectedAction ; 
		
		private String name ;
		
		public TestObjectModel() {
			
			
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
	
	
	
	private TestObjectModel testobjectmodel;
	
	
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
		
		super.populate(mo);
	}

}
