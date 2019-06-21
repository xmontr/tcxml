package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.ActionView;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.TcViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxml.model.ActionsModel;

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
	
	
	
	


	public CallActionView(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);
		// color for the viewer
		color=SWT.COLOR_DARK_CYAN ;
		
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
	
	
	
	// populate argument
	
	String selectedAction = controller.readStingArgumentByName(mo.getArguments(), "Action Name");
	
	actionmodel.setActionSelected(selectedAction);
	
	super.populate(mo);
}



	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		TcViewer tcviewer = TcXmlPluginController.getInstance().getTcviewer();
		
		String actionName = actionmodel.getActionSelected();
		
	ActionView action	= tcviewer.getActionsViewer().getActionView(actionName);
	
	PlayingContext ret = action.play(ctx);
		
		return ret;
	}







	@Override
	public String buildTitle() throws TcXmlException {
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



	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		
		StringBuffer sb = new StringBuffer("// ").append(buildTitle());
		pw.println(sb.toString());
		TcViewer tcviewer = TcXmlPluginController.getInstance().getTcviewer();
		
		String actionName = actionmodel.getActionSelected();
		
		sb= new StringBuffer(actionName).append("();");
		pw.println(sb.toString());

		
	}
}
