package tcxmlplugin.composite.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.arguments.ArgumentFactory;
import tcxmlplugin.composite.view.arguments.StepArgument;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class BrowserActionView extends StepView implements PropertyChangeListener{
	private DataBindingContext m_bindingContext;
	
	
	private BrowserActionModel browseractionmodel ;
	private Combo actionCombo;

	public BrowserActionView(Composite parent, int style, TcXmlController controller) {
		

		super(parent, style, controller);
		this.setLayout(new GridLayout(2, false));
		browseractionmodel = new BrowserActionModel();
		
		browseractionmodel.addPropertyChangeListener("actionSelected", this);
		
		Label lblAction = new Label(this, SWT.NONE);
		lblAction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAction.setText("action");
		
		actionCombo = new Combo(this, SWT.NONE);
		actionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		
		}
	
	
	
	public static class BrowserActionModel {
		
		private PropertyChangeSupport propertyChangeSupport;
		
		private String actionSelected;
		
		
		private List<String> allActions ;
		
		
		public BrowserActionModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);
			
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

			public String getActionSelected() {
				return actionSelected;
			}

			public void setActionSelected(String actionSelected) {
				
				propertyChangeSupport.firePropertyChange("actionSelected", this.actionSelected,
						this.actionSelected = actionSelected);
				
			}

			public List<String> getAllActions() {
				return allActions;
			}

			public void setAllActions(List<String> allActions) {
				propertyChangeSupport.firePropertyChange("allActions", this.allActions,
						this.allActions = allActions);
				
			}	
		
		
	}
	
	
	@Override
	public void populate(Step mo) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(mo);
		
		setTitle(formatTitle(mo.getIndex(), mo.getAction()));
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	String newAction = (String)	evt.getNewValue();
	StepArgument ar = ArgumentFactory.getArgument(newAction ,this);
	setArgumentView(ar);
	
		
	}


	private void setArgumentView(StepArgument ar) {
		// TODO Auto-generated method stub
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsActionComboObserveWidget = WidgetProperties.items().observe(actionCombo);
		IObservableList allActionsBrowseractionmodelObserveList = BeanProperties.list("allActions").observe(browseractionmodel);
		bindingContext.bindList(itemsActionComboObserveWidget, allActionsBrowseractionmodelObserveList, null, null);
		//
		IObservableValue observeSelectionActionComboObserveWidget = WidgetProperties.selection().observe(actionCombo);
		IObservableValue actionSelectedBrowseractionmodelObserveValue = BeanProperties.value("actionSelected").observe(browseractionmodel);
		bindingContext.bindValue(observeSelectionActionComboObserveWidget, actionSelectedBrowseractionmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
