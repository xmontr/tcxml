package tcxmlplugin.composite.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.ActionsModel;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class CallActionView extends StepView {
	private DataBindingContext m_bindingContext;
	
	
	private ActionsModel  actionmodel = new ActionsModel();
	private Combo comboActions;
	
	
	
	


	public CallActionView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		this.controller = controller;
		setLayout(new GridLayout(2, false));
		
		Label lblAction = new Label(this, SWT.NONE);
		lblAction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAction.setText("action");
		
		comboActions = new Combo(this, SWT.NONE);
		comboActions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
		
		 Map<String, Step> actionmap = controller.getActionMap();
		//build the actions model
		 List<String> allActions =    new ArrayList<String>(actionmap.keySet())    ;
		 actionmodel.setAllActions(allActions);
		 m_bindingContext = initDataBindings();
		
		
		
	}



@Override
public void populate(Step mo) throws TcXmlException {
	
	super.populate(mo);
	
	// populate argument
	
	String selectedAction = controller.readStingArgumentByName(mo.getArguments(), "Action Name");
	
	actionmodel.setActionSelected(selectedAction);
}



	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public String buildTitle(Step mo) throws TcXmlException {
		String ret = formatTitle(model.getIndex(), " Call Action" );
		return ret;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboActionsObserveWidget = WidgetProperties.items().observe(comboActions);
		IObservableList allActionsActionmodelObserveList = BeanProperties.list("allActions").observe(actionmodel);
		bindingContext.bindList(itemsComboActionsObserveWidget, allActionsActionmodelObserveList, null, null);
		//
		IObservableValue observeSelectionComboActionsObserveWidget = WidgetProperties.selection().observe(comboActions);
		IObservableValue actionSelectedActionmodelObserveValue = BeanProperties.value("actionSelected").observe(actionmodel);
		bindingContext.bindValue(observeSelectionComboActionsObserveWidget, actionSelectedActionmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
