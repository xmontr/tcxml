package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.MainStepContainer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.layout.GridData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ProgressBar;

public class ActionsViewer extends Composite  {
	private DataBindingContext m_bindingContext;
	
	
	
	private ActionsModel model = new ActionsModel();
	private Combo combo;


	private Map<String, ActionView> actionsView;

	private Composite stepContainer;



	private TcXmlController controller;



	private StackLayout actionlayout;
	
	
	public ActionsViewer(Composite parent, int style) {
	super(parent, style);	
		
		
	}

	public ActionsViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller =  controller ;		
		
		actionsView = new HashMap<String,ActionView>();	
		buildGUI();
		m_bindingContext = initDataBindings();
		
	}
	
	
	
	public void buildGUI() {
		
		setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("actions:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		
		
	
	
		//stepContainer = new MainStepContainer(this, SWT.NONE,controller );
		stepContainer = new Composite(this,this.getStyle());
		stepContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		actionlayout = new StackLayout();
		stepContainer.setLayout(actionlayout);
		
		
	}
	

	public ActionsModel getModel() {
		return model;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsModelObserveList = BeanProperties.list(ActionsModel.ALL_ACTIONS).observe(model);
		bindingContext.bindList(itemsComboObserveWidget, allActionsModelObserveList, null, null);
		//
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue actionSelectedModelObserveValue = BeanProperties.value(ActionsModel.ACTION_SELECTED).observe(model);
		bindingContext.bindValue(observeSelectionComboObserveWidget, actionSelectedModelObserveValue, null, null);
		//
		return bindingContext;
	}

	public void showSelectedAction(String  actname) {

	Control	ctrl = actionsView.get(actname);
	actionlayout.topControl = ctrl;
	layout(true,true);
	
		//stepContainer.showAction(ctrl);
		
	}
	
	
	
	
	
	public void showAction( String actName) {
		
		model.setActionSelected(actName);
		
	}

	public void buildAllActions(Map<String, Step> actionmap) {
		
		
		 List<String> allActions =    new ArrayList<String>(actionmap.keySet())    ;
		model.setAllActions(allActions);
		controller.getLog().info(("built actions :" + allActions.size()  ))   ;
		
		
		
	for (Iterator iterator = allActions.iterator(); iterator.hasNext();) {
		String name = (String) iterator.next();
		
		ActionView acv = new ActionView(name,stepContainer, this.getStyle(), controller);
		acv.buildAction(actionmap.get(name));
		actionsView.put(name, acv);
		
	}
		
		
		
	}

	public ActionView getActionView(String actionName) {
		// TODO Auto-generated method stub
		return actionsView.get(actionName);
	}




	
}
