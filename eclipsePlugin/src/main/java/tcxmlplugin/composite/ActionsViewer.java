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
import tcxml.model.ActionsModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Button;
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
	public Map<String, ActionView> getActionsView() {
		return actionsView;
	}






	private DataBindingContext m_bindingContext;
	
	
	
	private ActionsModel model = new ActionsModel();
	private Combo combo;


	private Map<String, ActionView> actionsView;

	private Composite stepContainer;



	private TcXmlController controller;



	private StackLayout actionlayout;



	private Composite maincontainer;



	private StackLayout maincontainerlayout;



	private Composite viewwithSnapshot;



	private Composite viewWitoutSnapshot;



	private Composite stepcontainerwithoutsnapshot;



	private SnapshotViewer snapshotviewer;



	private boolean isSnapshotlayout;
	
	
	public ActionsViewer(Composite parent, int style) {
	super(parent, style);	
	buildGUI();	
		
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
		
		maincontainer = new Composite(this,this.getStyle());
		maincontainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		maincontainerlayout = new StackLayout();
		maincontainer.setLayout(maincontainerlayout);		
		actionlayout = new StackLayout();
		
		viewwithSnapshot = createaViewWithsnapshotViewer();
		viewWitoutSnapshot = createViewWithoutsnapshotViewer();

	// default layout with snapshot viewer
		maincontainerlayout.topControl = viewwithSnapshot;
		isSnapshotlayout=true;
		
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

	ActionView	ctrl = actionsView.get(actname);
	
	ActionView old = (ActionView)actionlayout.topControl ;
	if(old != null) {
		old.removePropertyChangeListener(snapshotviewer);	
		
	}

	if(isSnapshotlayout) {
		ctrl.addPropertyChangeListener("currentStepExpanded", snapshotviewer);	
	}

	
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
	
	
	
	private Composite createViewWithoutsnapshotViewer() {
		Composite parent = new Composite(maincontainer, getStyle());
		parent.setLayout(new FillLayout());
		stepcontainerwithoutsnapshot = new Composite(parent, getStyle());
		stepcontainerwithoutsnapshot.setLayout(actionlayout);
		
		
		
		
	return parent;	
	}
	
	
private Composite createaViewWithsnapshotViewer() {
	
	Composite parent = new Composite(maincontainer, getStyle());
	parent.setLayout(new FillLayout());		
	SashForm sf = new SashForm(parent,SWT.HORIZONTAL);		
	stepContainer = new Composite(sf,sf.getStyle());
	stepContainer.setLayout(actionlayout);
	snapshotviewer = new SnapshotViewer(sf, getStyle(),controller);
	
			
		
	return parent;	
		
	}
	
	
	
	
	
	
	public void setSnapshotLayout( boolean  issnapshotlayout) {
		this.isSnapshotlayout=issnapshotlayout;
		ActionView currentaction = (ActionView) actionlayout.topControl;
		
		
		if(issnapshotlayout == false) { // view without snapshot viewer		
			if(currentaction != null) {
				currentaction.setParent(stepcontainerwithoutsnapshot);
				currentaction.removePropertyChangeListener(snapshotviewer);
				
			}
			
			
		maincontainerlayout.topControl = viewWitoutSnapshot;	
		
			
		}else { // view with snapshot viewer 
			if(currentaction != null) {
			currentaction.setParent(stepContainer);
			currentaction.addPropertyChangeListener("currentStepExpanded", snapshotviewer);
			}
			maincontainerlayout.topControl = viewwithSnapshot;
		
		
	}
		maincontainer.layout(true,true);
layout(true,true);
	}

	
}
