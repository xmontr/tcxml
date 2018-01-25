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
	
	
	private String code ;
	
	private PropertyChangeSupport propertyChangeSupport;

	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		propertyChangeSupport.firePropertyChange("code", this.code,
				this.code = code);
		this.code = code;
	}




	public EvaluateJavascriptView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);		
		this.setLayout(new GridLayout(2, false));
		
		Label codeLabel = new Label(this, SWT.NONE);
		codeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		codeLabel.setText("code");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		propertyChangeSupport = new PropertyChangeSupport(this);
	}




public void populate(Step mo  ) throws TcXmlException {	
	
super.populate(mo);		
	setTitle("Evaluate Javascript code " +  getShortCode());
	
	
}


private String getShortCode() {
	// TODO Auto-generated method stub
	return null;
}


public void addPropertyChangeListener(String propertyName,
	      PropertyChangeListener listener) {
	    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	  }

	  public void removePropertyChangeListener(PropertyChangeListener listener) {
	    propertyChangeSupport.removePropertyChangeListener(listener);
	  }


}
