package tcxmlplugin.composite.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class EvaluateJavascriptView extends StepView{
	private DataBindingContext m_bindingContext;
	private Text text;
	
	
	private EvaluateJavascriptModel evaljsmodel;
	
		public static class EvaluateJavascriptModel {
		
		private String code ;
		
		private PropertyChangeSupport propertyChangeSupport;
		
		public EvaluateJavascriptModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);	
			
		}

		public String getCode() {
			return code;
		}




		public void setCode(String code) {
			propertyChangeSupport.firePropertyChange("code", this.code,
					this.code = code);
			this.code = code;
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }	
		
		
	}
	
	
	





	public EvaluateJavascriptView(Composite parent, int style, TcXmlController controller, TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);		
		evaljsmodel = new EvaluateJavascriptModel();
		this.setLayout(new GridLayout(2, false));
		
		Label codeLabel = new Label(this, SWT.NONE);
		codeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		codeLabel.setText("code");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		m_bindingContext = initDataBindings();
		
	}




public void populate(Step mo  ) throws TcXmlException {	
	
	super.populate(mo);
	
	ArgModel codeArg = argumentMap.get("Code");
	
evaljsmodel.setCode(codeArg.getValue());
	
	
	
	
	
}


private String getShortCode() {
	
	String co = argumentMap.get("Code").getValue();
	String ret= co ;
	int size = co.length();
	if(size > 30) {
		StringBuffer sb = new StringBuffer();
		sb.append(co.substring(0, 15));
		sb.append(".......");
		sb.append(co.substring( size -15 , size )) ;
		ret = sb.toString();
	}
	




	return ret;
}





	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue codeEvaljsmodelObserveValue = BeanProperties.value("code").observe(evaljsmodel);
		bindingContext.bindValue(observeTextTextObserveWidget, codeEvaljsmodelObserveValue, null, null);
		//
		return bindingContext;
	}




	@Override
	public PlayingContext  play( PlayingContext ctx) throws TcXmlException {
		
String code = evaljsmodel.getCode();
controller.evaluateJS(code , ctx.getCurrentExecutionContext());
controller.getLog().info("after evaljavascript step context is :");
ctx.getCurrentExecutionContext().dumpJsContext();
return ctx;
		
		
	}




	@Override
	public String buildTitle() {
		String ret = formatTitle(model.getIndex(), "Evaluate Javascript code " +  getShortCode());
		return ret;
	}




	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		pw.println(" // " + getTitle());
	
		pw.println(evaljsmodel.code);
		
	}





}
