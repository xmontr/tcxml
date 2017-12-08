package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;

public class DefaultStepViewer extends Composite{
	private DataBindingContext m_bindingContext;

	public DefaultStepViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label typelabel = new Label(this, SWT.NONE);
		typelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		typelabel.setText("type");
		
		typetext = new Text(this, SWT.BORDER);
		typetext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label stepidlabel = new Label(this, SWT.NONE);
		stepidlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		stepidlabel.setText("stepid");
		
		stepidtext = new Text(this, SWT.BORDER);
		stepidtext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label argumentlabel = new Label(this, SWT.NONE);
		argumentlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		argumentlabel.setText("argument");
		
		argumenttext = new Text(this, SWT.BORDER);
		argumenttext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label libnamelabel = new Label(this, SWT.NONE);
		libnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		libnamelabel.setText("libname");
		
		libnametext = new Text(this, SWT.BORDER);
		libnametext.setText("");
		libnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label funcnamelabel = new Label(this, SWT.NONE);
		funcnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		funcnamelabel.setText("funcname");
		
		funcnametext = new Text(this, SWT.BORDER);
		funcnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label levellabel = new Label(this, SWT.NONE);
		levellabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		levellabel.setText("level");
		
		leveltext = new Text(this, SWT.BORDER);
		leveltext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label actionlabel = new Label(this, SWT.NONE);
		actionlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		actionlabel.setText("action");
		
		actiontext = new Text(this, SWT.BORDER);
		actiontext.setText("");
		actiontext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label indexlabel = new Label(this, SWT.NONE);
		indexlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		indexlabel.setText("index");
		
		indextext = new Text(this, SWT.BORDER);
		indextext.setText("");
		indextext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label categorynamelabel = new Label(this, SWT.NONE);
		categorynamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		categorynamelabel.setText("categoryname");
		
		categorynametext = new Text(this, SWT.BORDER);
		categorynametext.setText("");
		categorynametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}
	
	
private Step model;
private Text typetext;
private Text argumenttext;
private Text stepidtext;
private Text libnametext;
private Text funcnametext;
private Text leveltext;
private Text actiontext;
private Text indextext;
private Text categorynametext;
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTypetextObserveWidget = WidgetProperties.text(SWT.Modify).observe(typetext);
		IObservableValue typebytesModelObserveValue = PojoProperties.value("type.bytes").observe(model);
		bindingContext.bindValue(observeTextTypetextObserveWidget, typebytesModelObserveValue, null, null);
		//
		IObservableValue observeTextStepidtextObserveWidget = WidgetProperties.text(SWT.Modify).observe(stepidtext);
		bindingContext.bindValue(observeTextStepidtextObserveWidget, typebytesModelObserveValue, null, null);
		//
		return bindingContext;
	}
	public Step getModel() {
		return model;
	}
	public void setModel(Step model) {
		this.model = model;
	}
}
