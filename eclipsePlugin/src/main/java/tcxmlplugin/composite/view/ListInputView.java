package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.ActionsModel;
import tcxml.model.ListArgModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ListInputView extends Composite implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;
	
	
	ActionsModel model ;
	private Combo combo;
	
	private ListArgModel  argmodel;
	

	public ListArgModel getArgmodel() {
		return argmodel;
	}
	
	
	public void setArgmodel(ListArgModel argmodel) {
		this.argmodel = argmodel;
		setListValue(argmodel.getValueList());
		setValue(argmodel.getValue());
		
		model.addPropertyChangeListener("actionSelected", this);
		
		
	}
	public ListInputView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		model = new ActionsModel();
		m_bindingContext = initDataBindings();
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsModelObserveList = BeanProperties.list("allActions").observe(model);
		bindingContext.bindList(itemsComboObserveWidget, allActionsModelObserveList, null, null);
		//
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue actionSelectedModelObserveValue = BeanProperties.value("actionSelected").observe(model);
		bindingContext.bindValue(observeSelectionComboObserveWidget, actionSelectedModelObserveValue, null, null);
		//
		return bindingContext;
	}
	
	
	public void  setListValue(List<String>  li) {
		
		model.setAllActions(li);
	}
	
	
	public String getValueSelected() {
		
		return model.getActionSelected();
	}
	
	
	public void setValue(String val) {
		model.setActionSelected(val);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("actionSelected")) {
			argmodel.setValue((String) evt.getNewValue());
			
		}
		
	}
}
