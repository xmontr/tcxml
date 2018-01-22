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

import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.stepViewer.MainStepContainer;

public class LibraryViewer extends Composite {
	
	
	private LibraryModel model = new LibraryModel() ; 
	private Combo combo;
	
	private DataBindingContext m_bindingContext;
	
	private MainStepContainer functionContainer;
	

	public LibraryViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("library:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		
		
	
	
		functionContainer = new MainStepContainer(this, SWT.NONE);
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
			functionContainer.addStep(step2);
				
			
		}
	
	}



	public void showSelectedLibrary(TruLibrary lib) {
		//clean old one
		functionContainer.clean();
		List<Step> list = lib.getStep().getStep();
		for (Step step2 : list) { // add the step
			functionContainer.addStep(step2);
				
			
		}
		
		
	}



	

}
