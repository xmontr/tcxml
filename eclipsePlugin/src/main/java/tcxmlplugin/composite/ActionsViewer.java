package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.model.Step;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.layout.GridData;

import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.layout.FillLayout;

public class ActionsViewer extends Composite {
	private DataBindingContext m_bindingContext;
	
	
	
	private ActionsModel model = new ActionsModel();
	private Combo combo;



	private Composite actionContainer;

	public ActionsViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("actions:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		
		
		//ScrolledComposite sc = new ScrolledComposite(this,  SWT.BORDER | SWT.V_SCROLL);
	
		actionContainer = new Composite(this, SWT.NONE);
		actionContainer.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		//sc.setExpandVertical(true);
		//sc.setContent(actionContainer);
		actionContainer.setLayout(new GridLayout(1,false));
	
		
	
		m_bindingContext = initDataBindings();
		
		

		
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

	public void showSelectedAction(Step step) {
		//clean old one
		
		List<Step> list = step.getStep();
		for (Step step2 : list) { // add the step
			addStep(step2);
				
			
		}
		actionContainer.pack();
	}

	private void addStep(Step step2) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(actionContainer, SWT.NONE);
		stepviewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		stepviewer.setModel(step2);
		stepviewer.pack();
		
	}
	
}