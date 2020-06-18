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
import util.TcxmlUtils;

import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.EvalJavascriptWrapper;
import stepWrapper.ForWrapper;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class EvaluateJavascriptView extends StepView{
	private DataBindingContext m_bindingContext;
	private TextInputView text;
	
	
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
	
	
	





	public EvaluateJavascriptView(Composite parent, int style ) {
		super(parent, style);		
		evaljsmodel = new EvaluateJavascriptModel();
		this.setLayout(new GridLayout(2, false));
		
		Label codeLabel = new Label(this, SWT.NONE);
		codeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		codeLabel.setText("code");
		
		text = new TextInputView(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	
		
	}



@Override
public void populate( AbstractStepWrapper stepWrapper2 ) throws TcXmlException {
	
	
	if(! (stepWrapper2 instanceof EvalJavascriptWrapper )) {
		throw new TcXmlException("evaljavascript  view can only be populated by from a evaljavascript wrapper ", new IllegalArgumentException());
		
	}
	
	ArgModel codejs = ((EvalJavascriptWrapper)stepWrapper2).getJsCode();
	text.SetArgModel(codejs);
	
		
	

	

	
	
//m_bindingContext = initDataBindings();	
	
	
}

@Override
public void saveModel() throws TcXmlException {
	super.saveModel();
	/*
	 * EvalJavascriptWrapper wr = (EvalJavascriptWrapper) stepWrapper;
	 * 
	 * wr.saveArguments();
	 */
	
}













	@Override
	public PlayingContext  doplay( PlayingContext ctx) throws TcXmlException {
		
saveModel();		
		PlayingContext ct = stepWrapper.play(ctx);
		return ct ;
		
		
	}









	@Override
	public void export(PrintWriter pw) throws TcXmlException {
stepWrapper.export(pw);
		
	}





}
