package tcxmlplugin.composite.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class TestObjectView extends StepView {
	private DataBindingContext m_bindingContext;
	
	private  TruLibrary library;
	

	public TruLibrary getLibrary() {
		return library;
	}
	public void setLibrary(TruLibrary library) {
		this.library = library;
	}
	public TestObjectView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		testobjectmodel = new TestObjectModel() ;
		this.setLayout(new GridLayout(2, false));
		
		Label actionLabel = new Label(this, SWT.NONE);
		actionLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		actionLabel.setText("action");
		
		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label xpathLabel = new Label(this, SWT.NONE);
		xpathLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		xpathLabel.setText("Xpath");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
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

	

		public String getXpath() {
			return xpath;
		}

		public void setXpath(String xpath) {
			propertyChangeSupport.firePropertyChange("xpath", this.xpath,
					this.xpath = xpath);
			
		}



		private PropertyChangeSupport propertyChangeSupport;
		
		private List<String> allActions ;
		
		private String selectedAction ; 
		
		private String xpath ;
		
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
	private Text text;
	private Combo combo;
	
	
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
		
		super.populate(mo); TestObject to = null;
		// find the testobject in the script or in the lib
		if(library == null) {
			to = controller.getTestObjectById(mo.getTestObject());	
			
		} else {
		
			to = controller.getTestObjectById(mo.getTestObject(), library);
			
		}
		 
		
		testobjectmodel.setAllActions(controller.getAvailableActionForTestobject(to));
		testobjectmodel.setSelectedAction(mo.getAction());
		testobjectmodel.setXpath(controller.getXpathForTestObject(to));
		
		setTitle(formatTitle(mo.getIndex(),mo.getAction() +" on " +  to.getAutoName()));
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsTestobjectmodelObserveList = BeanProperties.list("allActions").observe(testobjectmodel);
		bindingContext.bindList(itemsComboObserveWidget, allActionsTestobjectmodelObserveList, null, null);
		//
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue selectedActionTestobjectmodelObserveValue = BeanProperties.value("selectedAction").observe(testobjectmodel);
		bindingContext.bindValue(observeSelectionComboObserveWidget, selectedActionTestobjectmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
