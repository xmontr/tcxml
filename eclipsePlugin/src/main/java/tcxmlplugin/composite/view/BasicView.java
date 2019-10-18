package tcxmlplugin.composite.view;

import java.io.PrintWriter;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;

public class BasicView   extends StepView {
	
	
	private DataBindingContext m_bindingContext;
	


	public BasicView(Composite parent, int style, TcXmlController controller ,TruLibrary truLibrary) {
		
	super(parent, style,controller,truLibrary);
		this.setLayout(new GridLayout(2, false));
		


	
		
		Label typelabel = new Label(this, SWT.NONE);
		typelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		typelabel.setText("type");
		
		typetext = new Text(this, SWT.BORDER);
		typetext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		categorynamelabel = new Label(this, SWT.NONE);
		categorynamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		categorynamelabel.setText("categoryname");
		
		categorynametext = new Text(this, SWT.BORDER);
		categorynametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label stepidlabel = new Label(this, SWT.NONE);
		stepidlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		stepidlabel.setText("stepid");
		
		stepidtext = new Text(this, SWT.BORDER);
		stepidtext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		argumentlabel = new Label(this, SWT.NONE);
		argumentlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		argumentlabel.setText("argument");
		
		argumenttext = new Text(this, SWT.BORDER);
		argumenttext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label libnamelabel = new Label(this, SWT.NONE);
		libnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		libnamelabel.setText("libname");
		
		libnametext = new Text(this, SWT.BORDER);
		
		libnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label funcnamelabel = new Label(this, SWT.NONE);
		funcnamelabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		funcnamelabel.setText("funcname");
		
		funcnametext = new Text(this, SWT.BORDER);
		funcnametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		actionlabel = new Label(this, SWT.NONE);
		actionlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		actionlabel.setText("action");
		
		actiontext = new Text(this, SWT.BORDER);
		actiontext.setText("");
		actiontext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		levellabel = new Label(this, SWT.NONE);
		levellabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		levellabel.setText("level");
		
		leveltext = new Text(this, SWT.BORDER);
		leveltext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		indexlabel = new Label(this, SWT.NONE);
		indexlabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		indexlabel.setText("index");
		
		indextext = new Text(this, SWT.BORDER);
		indextext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblComment = new Label(this, SWT.NONE);
		lblComment.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblComment.setText("comment");
		
		commenttext = new Text(this, SWT.BORDER);
		commenttext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
	
		
		
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}
	
	

private Text typetext;
private Text argumenttext;
private Text stepidtext;
private Text libnametext;
private Text funcnametext;
private Label actionlabel;
private Text actiontext;
private Label levellabel;
private Text leveltext;
private Label indexlabel;
private Text indextext;
private Label argumentlabel;
private Label categorynamelabel;
private Text categorynametext;
private Label lblComment;
private Text commenttext;


	






	
	
	
	
	
	
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextStepidtextObserveWidget_1 = WidgetProperties.text(SWT.Modify).observe(stepidtext);
		IObservableValue stepIdModelObserveValue = BeanProperties.value("stepId").observe(model);
		bindingContext.bindValue(observeTextStepidtextObserveWidget_1, stepIdModelObserveValue, null, null);
		//
		IObservableValue observeTextTypetextObserveWidget_1 = WidgetProperties.text(SWT.Modify).observe(typetext);
		IObservableValue typeModelObserveValue = BeanProperties.value("type").observe(model);
		bindingContext.bindValue(observeTextTypetextObserveWidget_1, typeModelObserveValue, null, null);
		//
		IObservableValue observeTextActiontextObserveWidget = WidgetProperties.text(SWT.Modify).observe(actiontext);
		IObservableValue actionModelObserveValue = BeanProperties.value("action").observe(model);
		bindingContext.bindValue(observeTextActiontextObserveWidget, actionModelObserveValue, null, null);
		//
		IObservableValue observeTextLeveltextObserveWidget = WidgetProperties.text(SWT.Modify).observe(leveltext);
		IObservableValue levelModelObserveValue = BeanProperties.value("level").observe(model);
		bindingContext.bindValue(observeTextLeveltextObserveWidget, levelModelObserveValue, null, null);
		//
		IObservableValue observeTextIndextextObserveWidget = WidgetProperties.text(SWT.Modify).observe(indextext);
		IObservableValue indexModelObserveValue = BeanProperties.value("index").observe(model);
		bindingContext.bindValue(observeTextIndextextObserveWidget, indexModelObserveValue, null, null);
		//
		IObservableValue observeTextFuncnametextObserveWidget = WidgetProperties.text(SWT.Modify).observe(funcnametext);
		IObservableValue funcNameModelObserveValue = BeanProperties.value("funcName").observe(model);
		bindingContext.bindValue(observeTextFuncnametextObserveWidget, funcNameModelObserveValue, null, null);
		//
		IObservableValue observeTextLibnametextObserveWidget = WidgetProperties.text(SWT.Modify).observe(libnametext);
		IObservableValue libNameModelObserveValue = BeanProperties.value("libName").observe(model);
		bindingContext.bindValue(observeTextLibnametextObserveWidget, libNameModelObserveValue, null, null);
		//
		IObservableValue observeTextArgumenttextObserveWidget = WidgetProperties.text(SWT.Modify).observe(argumenttext);
		IObservableValue argumentsModelObserveValue = BeanProperties.value("arguments").observe(model);
		bindingContext.bindValue(observeTextArgumenttextObserveWidget, argumentsModelObserveValue, null, null);
		//
		IObservableValue observeTextCategorynametextObserveWidget = WidgetProperties.text(SWT.Modify).observe(categorynametext);
		IObservableValue categoryNameModelObserveValue = BeanProperties.value("categoryName").observe(model);
		bindingContext.bindValue(observeTextCategorynametextObserveWidget, categoryNameModelObserveValue, null, null);
		//
		IObservableValue observeTextCommenttextObserveWidget = WidgetProperties.text(SWT.Modify).observe(commenttext);
		IObservableValue commentNameModelObserveValue = BeanProperties.value("comment").observe(model);
		bindingContext.bindValue(observeTextCommenttextObserveWidget, commentNameModelObserveValue, null, null);
		
		return bindingContext;
	}
	
	
/*	@Override
	public Step getModel() {
		// TODO Auto-generated method stub
		return model;
	}*/
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
	
super.populate(mo);		
	
		
		
	}





	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		throw new TcXmlException("not implemented", new IllegalAccessException());
	}





	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		pw.println(getTitle());
		
	}



	
	
	
	

}
