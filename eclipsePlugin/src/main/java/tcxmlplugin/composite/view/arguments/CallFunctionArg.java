package tcxmlplugin.composite.view.arguments;

import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlException;
import tcxmlplugin.composite.view.TextInputView;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import model.CallFunctionAttribut;

public class CallFunctionArg extends StepArgument {

	public CallFunctionArg(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new GridLayout(2, false));
		
		Label headerLabelName = new Label(this, SWT.NONE);
		headerLabelName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		headerLabelName.setText("parameter Name");
		
		Label headerLabelValue = new Label(this, SWT.NONE);
		headerLabelValue.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		headerLabelValue.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		headerLabelValue.setText("Value");
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void populate(String jsonArg) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(jsonArg);
		
		
	Set<String> keys = arg.keySet();
	for (String key : keys) {
		JsonObject att = arg.getJsonObject(key);
		JsonString val = att.getJsonString("value");
		CallFunctionAttribut callatt = new CallFunctionAttribut(key,val.getString(),false);
		
		populateSingleParameter(callatt);
	}
		
	}


	private void populateSingleParameter(CallFunctionAttribut callatt) {
	Label newName = new Label(this, SWT.NONE);
	newName.setText(callatt.getName());
	newName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
	TextInputView tinpu = new TextInputView(this,SWT.NONE);
	tinpu.setInputData(callatt.getValue());
	tinpu.setJavascript(callatt.isJs());
	tinpu.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
		
	}

}

