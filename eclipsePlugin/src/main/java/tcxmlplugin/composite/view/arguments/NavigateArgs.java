package tcxmlplugin.composite.view.arguments;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.json.JsonObject;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import tcxmlplugin.composite.view.TextInputView;

public class NavigateArgs extends StepArgument{
	
	
	private NavigateArgsModel navmodel;
	private TextInputView textInputView;
	
	public static class NavigateArgsModel {
		
		private String location;
		
		private boolean isJavascript;
		
		
		
		public boolean isJavascript() {
			return isJavascript;
		}

		public void setJavascript(boolean isJavascript) {
			propertyChangeSupport.firePropertyChange("isJavascript", this.isJavascript,
					this.isJavascript = isJavascript);
			
		}

		private PropertyChangeSupport propertyChangeSupport;
		
		
		public NavigateArgsModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);
			
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				propertyChangeSupport.firePropertyChange("location", this.location,
						this.location = location);
				
			}	
		
	}
	
	
	

	public NavigateArgs(Composite parent, int style) {
		super(parent, style);
		this.navmodel = new NavigateArgsModel();
		setLayout(new GridLayout(2, false));
		
		Label lblLocation = new Label(this, SWT.NONE);
		lblLocation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLocation.setText("Location");
		
		textInputView = new TextInputView(this, SWT.NONE);
		textInputView.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
	}
	
	
	
	@Override
	public void populate(String jsonarg) throws TcXmlException {
		
		super.populate(jsonarg);
	JsonObject locobj = arg.getJsonObject("Location");
		String location = locobj.getJsonString("value").getString();
		navmodel.setLocation(location);
	 boolean isj = locobj.getBoolean("evalJavaScript");
	navmodel.setJavascript(isj);
	textInputView.setJavascript(isj);
	textInputView.setInputData(location);
	}
	
	
	
	
	
	

}
