package tcxmlplugin.composite.view.arguments;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlException;
import tcxml.model.Step;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.json.JsonObject;

import org.eclipse.swt.SWT;
import tcxmlplugin.composite.view.TextInputView;
import org.eclipse.swt.layout.GridData;

public class TypeTextArgs extends StepArgument {
	
	
	public static class TypeTextArgsModel {
		
		private String text;
		
		private boolean isJavascript;
		
		
		
		public boolean isJavascript() {
			return isJavascript;
		}

		public void setJavascript(boolean isJavascript) {
			propertyChangeSupport.firePropertyChange("isJavascript", this.isJavascript,
					this.isJavascript = isJavascript);
			
		}

		private PropertyChangeSupport propertyChangeSupport;
		
		
		public TypeTextArgsModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);
			
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

			public String getText() {
				return text;
			}

			public void setText(String text) {
				propertyChangeSupport.firePropertyChange("text", this.text,
						this.text = text);
				
			}	
		
	}
	
	private TypeTextArgsModel typemodel;
	private TextInputView textInputView;
	

	public TypeTextArgs(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblText = new Label(this, SWT.NONE);
		lblText.setText("Text");
		
		textInputView = new TextInputView(this, SWT.NONE);
		textInputView.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		typemodel = new  TypeTextArgsModel() ;
	}
	
	@Override
	public void populate(Step model) throws TcXmlException {
		boolean isj=false;
		
		super.populate(model);
	JsonObject val = arg.getJsonObject("Value");
		String txt = val.getJsonString("value").getString();
		typemodel.setText(txt);
		if(val.containsKey("evalJavaScript")) {
			 isj = val.getBoolean("evalJavaScript",false);
			
		}
	 
	 typemodel.setJavascript(isj);
	textInputView.setJavascript(isj);
	textInputView.setInputData(txt);
	}

}
