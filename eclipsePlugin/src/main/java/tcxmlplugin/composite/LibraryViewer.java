package tcxmlplugin.composite;

import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import model.CallFunctionAttribut;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.FunctionContainer;
import tcxmlplugin.composite.stepViewer.MainStepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class LibraryViewer extends Composite {
	
	
	private LibraryModel model = new LibraryModel() ; 
	private Combo combo;
	
	private DataBindingContext m_bindingContext;
	
	private FunctionContainer functionContainer;
	private TcXmlController controller;
	

	public LibraryViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		this.controller = controller;
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("library:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		
		
	
	
		functionContainer = new FunctionContainer(this, SWT.NONE,controller);
		functionContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

	
		
	
		m_bindingContext = initDataBindings();
		
		
		
	}
	
	

	public LibraryModel getModel() {
		return model;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsModelObserveList = BeanProperties.list(LibraryModel.ALL_LIBRARY).observe(model);
		bindingContext.bindList(itemsComboObserveWidget, allActionsModelObserveList, null, null);
		//
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue actionSelectedModelObserveValue = BeanProperties.value(LibraryModel.LIBRARY_SELECTED).observe(model);
		bindingContext.bindValue(observeSelectionComboObserveWidget, actionSelectedModelObserveValue, null, null);
		//
		return bindingContext;
	}

	public void showSelectedAction(Step step) {
		//clean old one
		functionContainer.clean();
		List<Step> list = step.getStep();
		for (Step step2 : list) { // add the step
			try {
				functionContainer.addStep(step2);
			} catch (TcXmlException e) {
TcXmlPluginController.getInstance().error("fail to show selected action", e);
			}
				
			
		}
	
	}



	public void showSelectedLibrary(TruLibrary lib) {
		//clean old one
		functionContainer.clean();
		functionContainer.setLibrary(lib);
		List<Step> list = lib.getStep().getStep();
		for (Step step2 : list) { // add the step
			try {
				functionContainer.addStep(step2);
			} catch (TcXmlException e) {
				// TODO Auto-generated catch block
				TcXmlPluginController.getInstance().error("fail to show selected library", e);
			}
				
			
		}
		
		
	}

/**
 * 
 * execution interactive of the function with the list of attributs 
 * 
 * 
 * @param libName
 * @param funcName
 * @param ctx
 * @throws TcXmlException 
 */

	public StepViewer getFunction(String libName, String funcName) throws TcXmlException {
		
	
		
	return functionContainer.getFunction( libName,  funcName) ;	
	}
	
	public void showLibrary(String libName) {
		
		model.setLibrarySelected(libName);
		
	}



	

}
