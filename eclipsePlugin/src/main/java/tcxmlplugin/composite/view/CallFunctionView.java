package tcxmlplugin.composite.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.ActionsModel;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.arguments.ArgumentFactory;
import tcxmlplugin.composite.view.arguments.StepArgument;

import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.widgets.Group;

public class CallFunctionView  extends StepView implements PropertyChangeListener{
	private DataBindingContext m_bindingContext;
	
	
	
	private CallFunctionViewModel  callfunctmodel;
	private Combo libcombo;
	private Combo funcombo;
	private Group argumentEditorGroup;



public static class CallFunctionViewModel {
	
	
	private String selectedLib;
	
	
	private String selectedFunction;
		
		private List<String> allLibs;
		private List<String> allFunctions;		
		

		
		
		public CallFunctionViewModel() {
			propertyChangeSupport = new PropertyChangeSupport(this);
		}
		

		public String getSelectedLib() {
			return selectedLib;
		}

		public void setSelectedLib(String selectedLib) {
			propertyChangeSupport.firePropertyChange("selectedLib", this.selectedLib, this.selectedLib = selectedLib);
			
		}

		public String getSelectedFunction() {
			return selectedFunction;
		}

		public void setSelectedFunction(String selectedFunction) {
			propertyChangeSupport.firePropertyChange("selectedFunction", this.selectedFunction,
					this.selectedFunction = selectedFunction);
			
		}


		private PropertyChangeSupport propertyChangeSupport;
		
		
		public List<String> getAllFunctions() {
			return allFunctions;
		}
		
		public List<String> getAllLibs() {
			return allLibs;
		}





		public void setAllLibs(List<String> allLibs) {
			propertyChangeSupport.firePropertyChange("allLibs", this.allLibs,
					this.allLibs = allLibs);
			
		}


		public void setAllFunctions(List<String> allFunctions) {
			propertyChangeSupport.firePropertyChange("allFunctions", this.allFunctions,
					this.allFunctions = allFunctions);
			
		}


	





	
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

		
		
	}
	
	

	

	public CallFunctionView(Composite parent, int style, TcXmlController controller) {
		super(parent,  style,controller);
	
		
		callfunctmodel = new CallFunctionViewModel();
		this.setLayout(new GridLayout(2, false));
		
		Label LibraryNameLabel = new Label(this, SWT.NONE);
		LibraryNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		LibraryNameLabel.setText("Library Name:");
		
		libcombo = new Combo(this, SWT.NONE);
		libcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label functionnameLabel = new Label(this, SWT.NONE);
		functionnameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		functionnameLabel.setText("Function name:");
		
		funcombo = new Combo(this, SWT.NONE);
		funcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		argumentEditorGroup = new Group(this, SWT.NONE);
		argumentEditorGroup.setText("Arguments");
		//argumentEditorGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		argumentEditorGroup.setLayout(new FillLayout());
		
		
		
		
		m_bindingContext = initDataBindings();
		
		callfunctmodel.addPropertyChangeListener("selectedLib", this);
		callfunctmodel.addPropertyChangeListener("selectedFunction", this);
		
		
	}





	




	@Override
	public void populate(Step mo) throws TcXmlException {
		super.populate(mo);
		
		ArrayList<String> li = new ArrayList<String>();
		
		
		
		setTitle(formatTitle(model.getIndex(), " Call Function " + model.getLibName() + "." + model.getFuncName()) );
		
		
		callfunctmodel.setSelectedFunction(model.getFuncName());
		callfunctmodel.setSelectedLib(model.getLibName());
		

		li.addAll(controller.getLibraries().keySet());
		callfunctmodel.setAllLibs(li);
		
		updateFunctionList(model.getLibName());
		
	
		
		
		
		
		
		
	}










	private void updateFunctionList(String libName) {
		try {
			List<String> listname = controller.getFunctionsNameForLib(model.getLibName());
			
			callfunctmodel.setAllFunctions(listname);
			
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("fail to load functions for library" + model.getLibName() , e);
		
		}
		
	}










	@Override
	public void playInteractive() throws TcXmlException {
		throw new TcXmlException("not implemented", new IllegalAccessException());
		
	}










	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	switch (evt.getPropertyName() ) {
	case "selectedLib" : updateFunctionList(evt.getNewValue().toString());break;
	case "selectedFunction" :updateArgument (evt.getNewValue().toString(), callfunctmodel.getSelectedLib());break;
	
	
	}
		
	}
	private void updateArgument(String functName, String libname) {
		
		
		StepArgument ar;
		try {
			ar = ArgumentFactory.getArgumentForFUnction(functName, this);
			setArgumentView(ar);
			TcXmlPluginController.getInstance().info("setting nw argument for function : " + functName);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("fail to create argument view for step", e);

		}
		
		
	
		
	}




	private void setArgumentView(StepArgument ar) {
		// remove oldone if necessary
		if (theArgument != null) {
			theArgument.dispose();

		}
		ar.setParent(argumentEditorGroup);
		argumentEditorGroup.layout();
		setTheArgument(ar);

		



	}
	
	






	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsLibcomboObserveWidget = WidgetProperties.items().observe(libcombo);
		IObservableList allLibsCallfunctmodelObserveList = BeanProperties.list("allLibs").observe(callfunctmodel);
		bindingContext.bindList(itemsLibcomboObserveWidget, allLibsCallfunctmodelObserveList, null, null);
		//
		IObservableList itemsFuncomboObserveWidget = WidgetProperties.items().observe(funcombo);
		IObservableList allFunctionsCallfunctmodelObserveList = BeanProperties.list("allFunctions").observe(callfunctmodel);
		bindingContext.bindList(itemsFuncomboObserveWidget, allFunctionsCallfunctmodelObserveList, null, null);
		//
		IObservableValue observeSelectionLibcomboObserveWidget = WidgetProperties.selection().observe(libcombo);
		IObservableValue selectedLibCallfunctmodelObserveValue = BeanProperties.value("selectedLib").observe(callfunctmodel);
		bindingContext.bindValue(observeSelectionLibcomboObserveWidget, selectedLibCallfunctmodelObserveValue, null, null);
		//
		IObservableValue observeSelectionFuncomboObserveWidget = WidgetProperties.selection().observe(funcombo);
		IObservableValue selectedFunctionCallfunctmodelObserveValue = BeanProperties.value("selectedFunction").observe(callfunctmodel);
		bindingContext.bindValue(observeSelectionFuncomboObserveWidget, selectedFunctionCallfunctmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
