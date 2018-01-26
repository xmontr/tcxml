package tcxmlplugin.composite.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class EvaluateJavascriptView extends StepView{
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
	
	
	





	public EvaluateJavascriptView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);		
		evaljsmodel = new EvaluateJavascriptModel();
		this.setLayout(new GridLayout(2, false));
		
		Label codeLabel = new Label(this, SWT.NONE);
		codeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		codeLabel.setText("code");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	}




public void populate(Step mo  ) throws TcXmlException {	
	
super.populate(mo);	
evaljsmodel.setCode(controller.JSCodefromJSON(model.getArguments()));
	setTitle("Evaluate Javascript code " +  getShortCode());
	
	
	
}


private String getShortCode() {
	
	String co = evaljsmodel.getCode() ;
	String ret= co ;
	int size = co.length();
	if(size > 10) {
		StringBuffer sb = new StringBuffer();
		sb.append(co.substring(0, 4));
		sb.append(".......");
		sb.append(co.substring( size -4 , size )) ;
		ret = sb.toString();
	}
	




	return ret;
}





}
