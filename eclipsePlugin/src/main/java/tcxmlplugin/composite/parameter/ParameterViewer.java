package tcxmlplugin.composite.parameter;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.parameter.StepParameter;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.model.ParameterViewerModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ParameterViewer extends Composite implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;
	
	
	
	
	private TcXmlController controller;
	
	
	private ParameterViewerModel model;
	private List list;




	private Composite editorArea;




	private ParameterEditor peditor;

	public ParameterViewer(Composite parent, int style) {
		super(parent, style);
buildGUI();

buildModel();
	
	}

	
	
	private void buildModel() {
		model = new ParameterViewerModel();
		model.addPropertyChangeListener(model.PARAMETER_SELECTED, this);
		
	}



	private void buildGUI() {
		setLayout(new GridLayout(2, false));
		
		Group grpParameterList = new Group(this, SWT.NONE);
		grpParameterList.setText("Parameter List");
		grpParameterList.setLayout(new GridLayout(1, false));
		grpParameterList.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		
		list = new List(grpParameterList, SWT.BORDER);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Button btnNew = new Button(grpParameterList, SWT.NONE);
		btnNew.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		btnNew.setText("new");
		
		Button btnDelete = new Button(grpParameterList, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		btnDelete.setText("delete");
		
		editorArea = new Composite(this, SWT.NONE);
		editorArea.setLayout(new FillLayout());
		editorArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
	}



	public ParameterViewer(Composite parent, int style, TcXmlController controller ) {
		super(parent, style);
	
		this.controller=controller;
		buildGUI();
		buildModel();
		m_bindingContext = initDataBindings();
		
		
	}



	public void populate(Map<String, StepParameter> parameters) throws TcXmlException {
		ArrayList<String> li = new ArrayList<String>();
		
	Set<String> en = parameters.keySet();
	
	li.addAll(en);
	model.setAllParameters(li);
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList allParametersModelObserveList = BeanProperties.list("allParameters").observe(model);
		bindingContext.bindList(itemsListObserveWidget, allParametersModelObserveList, null, null);
		//
		IObservableValue observeSelectionListObserveWidget = WidgetProperties.selection().observe(list);
		IObservableValue parameterSelectedModelObserveValue = BeanProperties.value("parameterSelected").observe(model);
		bindingContext.bindValue(observeSelectionListObserveWidget, parameterSelectedModelObserveValue, null, null);
		//
		return bindingContext;
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String paramName  =(String) evt.getNewValue() ;
		
		try {
			
			if(peditor != null) {
				
				peditor.delete();
			}
			StepParameter param = controller.getParameterByName(paramName);
		 peditor = ParamEditorFactory.createParamEditor(editorArea,param);
		 layout(true,true);
			
			
			
			
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("failure finding parameter " + paramName, e);
		}
		
		
		
		
	}
}
