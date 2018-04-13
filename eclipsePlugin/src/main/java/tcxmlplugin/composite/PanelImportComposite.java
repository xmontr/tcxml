package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;



public class PanelImportComposite extends Composite {
	private DataBindingContext m_bindingContext;
	
	
	

	
	private  ImportModel mo= new ImportModel();
	private Text scriptText;
	private Text text;
	private List librarieslist;
	private List parametersList;
	
	
	
	
	
	
	
	
	
	

	public PanelImportComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label scriptLabel = new Label(this, SWT.NONE);
		scriptLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		scriptLabel.setText("Script:");
		
		scriptText = new Text(this, SWT.BORDER);
		scriptText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label librariesLabel = new Label(this, SWT.NONE);
		librariesLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		librariesLabel.setText("Libraries:");
		
		librarieslist = new List(this, SWT.BORDER);
		librarieslist.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label parametersLabel = new Label(this, SWT.NONE);
		parametersLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		parametersLabel.setText("Parameters:");
		
		parametersList = new List(this, SWT.BORDER);
		parametersList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnProceed = new Button(this, SWT.NONE);
		btnProceed.setText("Proceed");
		new Label(this, SWT.NONE);
		
		Label lblMessages = new Label(this, SWT.NONE);
		lblMessages.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMessages.setText("messages");
		
		text = new Text(this, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.heightHint = 63;
		text.setLayoutData(gd_text);
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextScriptTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(scriptText);
		IObservableValue mainScriptMoObserveValue = BeanProperties.value("mainScript").observe(mo);
		bindingContext.bindValue(observeTextScriptTextObserveWidget, mainScriptMoObserveValue, null, null);
		//
		IObservableList itemsLibrarieslistObserveWidget = WidgetProperties.items().observe(librarieslist);
		IObservableList librariesMoObserveList = BeanProperties.list("libraries").observe(mo);
		bindingContext.bindList(itemsLibrarieslistObserveWidget, librariesMoObserveList, null, null);
		//
		IObservableList itemsParametersListObserveWidget = WidgetProperties.items().observe(parametersList);
		IObservableList parametersMoObserveList = BeanProperties.list("parameters").observe(mo);
		bindingContext.bindList(itemsParametersListObserveWidget, parametersMoObserveList, null, null);
		//
		return bindingContext;
	}
}
