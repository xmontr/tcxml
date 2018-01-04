package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.custom.ScrolledComposite;

public class DefaultStepViewer extends Composite{
	private DataBindingContext m_bindingContext;

	public DefaultStepViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		
		ExpandBar expandBar = new ExpandBar(this, SWT.NONE);
		
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText("New ExpandItem");
		
		
		
	//	ScrolledComposite scrolledComposite = new ScrolledComposite(expandBar, SWT.BORDER | SWT.V_SCROLL);
		Composite thecontent = new Composite(expandBar, SWT.NONE);
	//	scrolledComposite.setContent(thecontent);
		thecontent.setLayout(new GridLayout(2, false));
		
	//	scrolledComposite.setExpandHorizontal(true);
	//	scrolledComposite.setExpandVertical(true);

		
		
		Label typelabel = new Label(thecontent, SWT.NONE);
		typelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true, 1, 1));
		typelabel.setText("type");
		
		typetext = new Text(thecontent, SWT.BORDER);
		typetext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label stepidlabel = new Label(thecontent, SWT.NONE);
		stepidlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		stepidlabel.setText("stepid");
		
		stepidtext = new Text(thecontent, SWT.BORDER);
		stepidtext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label argumentlabel = new Label(thecontent, SWT.NONE);
		argumentlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		argumentlabel.setText("argument");
		
		argumenttext = new Text(thecontent, SWT.BORDER);
		argumenttext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label libnamelabel = new Label(thecontent, SWT.NONE);
		libnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		libnamelabel.setText("libname");
		
		libnametext = new Text(thecontent, SWT.BORDER);
		libnametext.setText("");
		libnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label funcnamelabel = new Label(thecontent, SWT.NONE);
		funcnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		funcnamelabel.setText("funcname");
		
		funcnametext = new Text(thecontent, SWT.BORDER);
		funcnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		xpndtmNewExpanditem.setControl(thecontent);
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		
		
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}
	
	
private Step model;
private Text typetext;
private Text argumenttext;
private Text stepidtext;
private Text libnametext;
private Text funcnametext;
	public Step getModel() {
		return model;
	}
	public void setModel(Step model) {
		this.model = model;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTypetextObserveWidget = WidgetProperties.text(SWT.Modify).observe(typetext);
		IObservableValue typebytesModelObserveValue = PojoProperties.value("type.bytes").observe(model);
		bindingContext.bindValue(observeTextTypetextObserveWidget, typebytesModelObserveValue, null, null);
		//
		IObservableValue observeTextStepidtextObserveWidget_1 = WidgetProperties.text(SWT.Modify).observe(stepidtext);
		IObservableValue stepIdModelObserveValue = PojoProperties.value("stepId").observe(model);
		bindingContext.bindValue(observeTextStepidtextObserveWidget_1, stepIdModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
