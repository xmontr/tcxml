package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.TcXmlPluginException;
import tcxmlplugin.composite.stepViewer.MainStepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
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
	
	
	/***
	 * 
	 * 
	 *  parent composite for all actionView
	 * 
	 */

	private Composite stepContainer;



	private TcXmlController controller;



	private StackLayout actionlayout;



	private Composite maincontainer;



	private StackLayout maincontainerlayout;



	private Composite viewwithSnapshot;



	private Composite viewWitoutSnapshot;

/****
 * 
 *   parent composite for actionview when snapshot is  not visible
 * 
 */

	private Composite stepcontainerwithoutsnapshot;
	
	
	
	/****
	 * 
	 *   parent composite for actionview when snapshot is   visible
	 * 
	 */

		private Composite stepcontainerwithsnapshot ;
	



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
		
		stepContainer =  new Composite(maincontainer, getStyle());
		
		
		maincontainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		maincontainerlayout = new StackLayout();
		maincontainer.setLayout(maincontainerlayout);		
		actionlayout = new StackLayout();
		stepContainer.setLayout(actionlayout);
		
		viewwithSnapshot = createaViewWithsnapshotViewer();
		viewWitoutSnapshot = createViewWithoutsnapshotViewer();

	// default layout with snapshot viewer
		isSnapshotlayout=true;
		maincontainerlayout.topControl = viewwithSnapshot;
		stepContainer.setParent(stepcontainerwithsnapshot);
		
		
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
	ctrl.refreshSize();
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
	
	public void refresh(Map<String, Step> actionmap) {
	Collection<ActionView> allview = actionsView.values();
		for (Iterator iterator = allview.iterator(); iterator.hasNext();) {
			ActionView actionView = (ActionView) iterator.next();
			actionView.dispose();
			
		}
		actionsView.clear();
		buildAllActions(actionmap);
		
		
	}
	
	
	
	
	

	public ActionView getActionView(String actionName) {
		// TODO Auto-generated method stub
		return actionsView.get(actionName);
	}
	
	
	
	private Composite createViewWithoutsnapshotViewer() {

		Composite parentWithoutSnapshotViewer = new Composite(maincontainer, getStyle());
		parentWithoutSnapshotViewer.setLayout(new FillLayout());		
		SashForm sf = new SashForm(parentWithoutSnapshotViewer,SWT.HORIZONTAL);	
		// the list of step at the left
		stepcontainerwithoutsnapshot = new Composite(sf,sf.getStyle());
		stepcontainerwithoutsnapshot.setLayout(new FillLayout());
		//palette at the right
		Composite paletteContainer = new Composite(sf, getStyle());
		paletteContainer.setLayout(new FillLayout());
		ActionDesignPalette palette = new ActionDesignPalette(paletteContainer, getStyle());
		
				
	return parentWithoutSnapshotViewer;	
	}
	
	
private Composite createaViewWithsnapshotViewer() {
	
	Composite parentWithSnapshotViewer = new Composite(maincontainer, getStyle());
	parentWithSnapshotViewer.setLayout(new FillLayout());		
	SashForm sf = new SashForm(parentWithSnapshotViewer,SWT.HORIZONTAL);	
	// the list of step at the left
	stepcontainerwithsnapshot = new Composite(sf,sf.getStyle());
	stepcontainerwithsnapshot.setLayout(new FillLayout());
	//at the right the palette with the snapshotviewer
	PaletteAndSnapshotViewer plt = new PaletteAndSnapshotViewer(sf, getStyle());
	
	snapshotviewer = new SnapshotViewer(plt.getSnapshot(), getStyle(),controller);	
	ActionDesignPalette palette = new ActionDesignPalette(plt.getPalette(), getStyle());
			
	return parentWithSnapshotViewer;	
		
	}
	
	
	
	
	
	
	public void setSnapshotLayout( boolean  issnapshotlayout) {
		this.isSnapshotlayout=issnapshotlayout;
		ActionView currentaction = (ActionView) actionlayout.topControl;
		
		
		if(issnapshotlayout == false) { // view without snapshot viewer		
			if(currentaction != null) {
				
				currentaction.removePropertyChangeListener(snapshotviewer);
				
			}
		stepContainer.setParent(stepcontainerwithoutsnapshot);	
			
		maincontainerlayout.topControl = viewWitoutSnapshot;	
		
			
		}else { // view with snapshot viewer 
			if(currentaction != null) {
			//currentaction.setParent(stepContainer);
			currentaction.addPropertyChangeListener("currentStepExpanded", snapshotviewer);
			}
			
			stepContainer.setParent(stepcontainerwithsnapshot);
			maincontainerlayout.topControl = viewwithSnapshot;
		
		
	}
		maincontainer.layout(true,true);
layout(true,true);
	}
	
	
	
	
	public void synchronizeAllActions(IProgressMonitor monitor) throws TcXmlPluginException {
		Set<String> allactionsname = actionsView.keySet() ;
		for (Iterator iterator = allactionsname.iterator(); iterator.hasNext();) {
			String actname = (String) iterator.next();
			synchronizeAction(actname,monitor);
			
		}
		
		
		
		
	}

	private void synchronizeAction(String actname,IProgressMonitor monitor) throws TcXmlPluginException{
	 ActionView theview = actionsView.get(actname);
	 List<StepViewer> listViewer = theview.stepViwerChildren;
	 for (StepViewer stepViewer : listViewer) {
		 try {
			stepViewer.getViewer().saveModel();
		} catch (TcXmlException e) {
			controller.getLog().severe("fail to syncronize model from view for action " + actname);
			throw new TcXmlPluginException("fail to syncronize model from view for action " + actname, e);
		}
		
	}
		
	}

	public String getNextRecordingTransactionName() {
		String baseName = "Recorded_";
		String proposedName="Recorded_1";
		Set<String> actionList = controller.getActionMap().keySet();
		int count=0;
		do {
			count++;
		 proposedName = baseName + count ;
		
			
		}while(actionList.contains(proposedName));
		
		return proposedName;
	}
	
	
	

	
}
