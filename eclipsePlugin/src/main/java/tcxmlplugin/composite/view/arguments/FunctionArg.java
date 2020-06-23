package tcxmlplugin.composite.view.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.ArgModel;
import tcxml.model.FunctionArgModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class FunctionArg extends Composite {
	private DataBindingContext m_bindingContext;
	
	private FunctionArgModel thearg ;
	private Label lblName;
	private Combo comboType;
	
	 private static String[] allTypes = new String[] {"string", "integer","boolean" } ;
	

	public FunctionArg(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		
		lblName = new Label(this, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblName.setText("New Label");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Type:");
		
		comboType = new Combo(this, SWT.NONE);
		comboType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnOtional = new Button(this, SWT.CHECK);
		btnOtional.setText("optional");
		
		thearg = new FunctionArgModel("newarg");
		m_bindingContext = initDataBindings();
		comboType.setItems(allTypes);
	
	}
	
	
	public List<String>  getAllTypes() {
		
		return null;
		
	}
	
	

	public FunctionArgModel getThearg() {
		return thearg;
	}



	public void populate(FunctionArgModel newthearg) {
			thearg.setName(newthearg.getName());
			thearg.setType(newthearg.getType());
			thearg.setOptional(newthearg.isOptional());
			
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextLblNameObserveWidget = WidgetProperties.text().observe(lblName);
		IObservableValue nameTheargObserveValue = BeanProperties.value("name").observe(thearg);
		bindingContext.bindValue(observeTextLblNameObserveWidget, nameTheargObserveValue, null, null);
		//
		IObservableValue observeSelectionComboTypeObserveWidget = WidgetProperties.selection().observe(comboType);
		IObservableValue typeTheargObserveValue = BeanProperties.value("type").observe(thearg);
		bindingContext.bindValue(observeSelectionComboTypeObserveWidget, typeTheargObserveValue, null, null);
		//
		return bindingContext;
	}
}
