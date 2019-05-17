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
import tcxmlplugin.model.ArgModel;

import org.eclipse.swt.layout.GridData;

public class TypeTextArgs extends StepArgument {
	
	
	public static class TypeTextArgsModel {
		
		private ArgModel text;
		
		
		
		
		
	

	

		
		
		
		public TypeTextArgsModel() {
			
			text = new ArgModel("text");
			
			
			
		}
		
	

			

			public ArgModel getText() {
				return text;
			}

			public void setText(ArgModel text) {
			
						this.text = text;
				
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
	public void populate(String jsonargl) throws TcXmlException {
		boolean isj=false;
		
		super.populate(jsonargl);
		typemodel.getText().populateFromJson(arg.getJsonObject("Value"));
		textInputView.SetArgModel(typemodel.getText());
		

	}

}
