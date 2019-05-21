package tcxmlplugin.composite.view.arguments;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

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
import tcxml.model.ArgModel;

public class NavigateArgs extends StepArgument{
	
	
	private NavigateArgsModel navmodel;
	private TextInputView textInputView;
	
	public static class NavigateArgsModel {
		
		private ArgModel location;

		
		
		
		public NavigateArgsModel() {
			
		this.location = new ArgModel("Location");
			
		}
		
	

			

			public ArgModel getLocation() {
				return location;
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
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		
		super.populate(argu);	
		textInputView.SetArgModel(argu.get("Location"));

	}
	
	
	
	
	
	

}
