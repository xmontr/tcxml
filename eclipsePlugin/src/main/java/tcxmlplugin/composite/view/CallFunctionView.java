package tcxmlplugin.composite.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class CallFunctionView  extends StepView{
	private DataBindingContext m_bindingContext;
	
	
	
	private CallFunctionViewModel callfunctmodel;
	private Combo libcombo;
	private Combo funcombo;



public static class CallFunctionViewModel {
		
		private List<String> allLibs;
		private List<String> allFunctions;
		
		
		public CallFunctionViewModel() {
			propertyChangeSupport = new PropertyChangeSupport(this);
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
		super(parent, style,controller);
	
		
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
		
		
		
		
		m_bindingContext = initDataBindings();
		

		
		
	}





	




	@Override
	public void populate(Step mo) throws TcXmlException {
		super.populate(mo);
		
		ArrayList<String> li = new ArrayList<String>();
		
		
		
		setTitle(formatTitle(model.getIndex(), " Call Function " + model.getLibName() + "." + model.getFuncName()) );
		
		callfunctmodel.setAllLibs(li);
		

		li.addAll(controller.getLibraries().keySet());
		
		
		
	try {
		List<String> listname = controller.getFunctionsNameForLib(model.getLibName());
		
		callfunctmodel.setAllFunctions(listname);
		
	} catch (TcXmlException e) {
		TcXmlPluginController.getInstance().error("fail to load functions for library" + model.getLibName() , e);
	
	}	
		
		
		
		
		
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsLibcomboObserveWidget = WidgetProperties.items().observe(libcombo);
		IObservableList allLibsCallfunctmodelObserveList = BeanProperties.list("allLibs").observe(callfunctmodel);
		bindingContext.bindList(itemsLibcomboObserveWidget, allLibsCallfunctmodelObserveList, null, null);
		//
		IObservableValue observeSelectionLibcomboObserveWidget_1 = WidgetProperties.selection().observe(libcombo);
		IObservableValue libNameGetModelObserveValue = BeanProperties.value("libName").observe(getModel());
		bindingContext.bindValue(observeSelectionLibcomboObserveWidget_1, libNameGetModelObserveValue, null, null);
		//
		IObservableList itemsFuncomboObserveWidget = WidgetProperties.items().observe(funcombo);
		IObservableList allFunctionsCallfunctmodelObserveList = BeanProperties.list("allFunctions").observe(callfunctmodel);
		bindingContext.bindList(itemsFuncomboObserveWidget, allFunctionsCallfunctmodelObserveList, null, null);
		//
		IObservableValue observeSelectionFuncomboObserveWidget = WidgetProperties.selection().observe(funcombo);
		IObservableValue funcNameGetModelObserveValue = BeanProperties.value("funcName").observe(getModel());
		bindingContext.bindValue(observeSelectionFuncomboObserveWidget, funcNameGetModelObserveValue, null, null);
		//
		return bindingContext;
	}










	@Override
	public void playInteractive() throws TcXmlException {
		throw new TcXmlException("not implemented", new IllegalAccessException());
		
	}
}
