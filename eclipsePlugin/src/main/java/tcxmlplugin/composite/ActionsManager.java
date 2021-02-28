package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ActionsModel;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;

import org.eclipse.swt.widgets.ToolBar;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ActionsManager extends Composite{
	private DataBindingContext m_bindingContext;
	
	
	
	
	private TcXmlController controller;
	private ActionsModel model;
	private List list;
	private Text createInput;
	private Text updateInput;

	public ActionsManager(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller = controller;
		model = new ActionsModel();
		buildGUI();
		buildModel();
		

}

	private void buildModel() {
		
		Map<String, Step> actionMap = controller.getActionMap();
		
		
		java.util.List<String> allactions = new ArrayList<String>();
		allactions.addAll(actionMap.keySet());
		model.setAllActions(allactions);
		
	}

	private void buildGUI() {
		setLayout(new GridLayout(1, false));
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.setText("remove");
		
		Group grpActions = new Group(this, SWT.NONE);
		grpActions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpActions.setText("Actions");
		grpActions.setLayout(new GridLayout(1, false));
		
		list = new List(grpActions, SWT.BORDER);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpCreateEdit = new Group(grpActions, SWT.NONE);
		grpCreateEdit.setLayout(new GridLayout(2, false));
		grpCreateEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpCreateEdit.setText("create / edit");
		
		createInput = new Text(grpCreateEdit, SWT.BORDER);
		createInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		createInput.setBounds(0, 0, 76, 21);
		
		Button btnCreate = new Button(grpCreateEdit, SWT.NONE);
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {				
					TcXmlPluginController.getInstance().createAction(createInput.getText());
				
				
			}
		});
		btnCreate.setText("create");
		
		updateInput = new Text(grpCreateEdit, SWT.BORDER);
		updateInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnRename = new Button(grpCreateEdit, SWT.NONE);
		btnRename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				
				TcXmlPluginController.getInstance().info(" renaming action " + model.getActionSelected() + " in "+ updateInput.getText());
			}
		});
		btnRename.setText("rename");
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList allActionsModelObserveList = BeanProperties.list("allActions").observe(model);
		bindingContext.bindList(itemsListObserveWidget, allActionsModelObserveList, null, null);
		//
		IObservableValue observeSelectionListObserveWidget = WidgetProperties.selection().observe(list);
		IObservableValue actionSelectedModelObserveValue = BeanProperties.value("actionSelected").observe(model);
		bindingContext.bindValue(observeSelectionListObserveWidget, actionSelectedModelObserveValue, null, null);
		//
		IObservableValue observeSelectionListObserveWidget_1 = WidgetProperties.selection().observe(list);
		IObservableValue observeTextUpdateInputObserveWidget = WidgetProperties.text(SWT.Modify).observe(updateInput);
		bindingContext.bindValue(observeSelectionListObserveWidget_1, observeTextUpdateInputObserveWidget, null, null);
		//
		return bindingContext;
	}
}
