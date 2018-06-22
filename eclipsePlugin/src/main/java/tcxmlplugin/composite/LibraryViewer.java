package tcxmlplugin.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.eclipse.swt.widgets.Control;
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
	
	
	private Map<String, LibraryView> librariesView;
	
	private FunctionContainer functionContainer;
	private TcXmlController controller;
	
	public LibraryViewer(Composite parent, int style) {
		
		super(parent, style);
	}
	

	public LibraryViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		this.controller = controller;
		librariesView= new HashMap<String,LibraryView>();
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("library:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		
		
	
	
		functionContainer = new FunctionContainer(this,SWT.NONE,controller);
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





	public void showSelectedLibrary(String libname) {
		
		Control	ctrl = librariesView.get(libname);
		functionContainer.showAction(ctrl);
		//setLibrary(truLibrary);
		

		
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
		
	
		
	return librariesView.get(libName).getFunction( libName,  funcName) ;	
	}
	
	public void showLibrary(String libName) {
		
		model.setLibrarySelected(libName);
		
	}



	public void buildAllLibraries(Map<String, TruLibrary> libmap) {
		List<String> allLib =    new ArrayList<String>(libmap.keySet())    ;
		
		controller.getLog().info(("found libraries :" + allLib.size()  ))   ;
		 
		 
		 model.setAllLibraries(allLib);
		 
			for (Iterator iterator = allLib.iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				
				LibraryView libv = new LibraryView(name,functionContainer, this.getStyle(), controller);
				libv.buildLibrary(libmap.get(name));
				librariesView.put(name, libv);
				
			}
		
	}



	

}
