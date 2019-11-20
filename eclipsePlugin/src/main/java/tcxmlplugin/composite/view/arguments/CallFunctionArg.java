package tcxmlplugin.composite.view.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlException;
import tcxmlplugin.composite.view.TextInputView;
import tcxml.model.ArgModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import tcxml.model.CallFunctionAttribut;

public class CallFunctionArg extends StepArgument {
	
	
	private List<CallFunctionAttribut>   callArguments;

	public CallFunctionArg(Composite parent, int style, HashMap<String, ArgModel> arguuu) {
		super(parent, style, arguuu);
		
		callArguments = new ArrayList<CallFunctionAttribut>();
		
		setLayout(new GridLayout(2, false));
		
		Label headerLabelName = new Label(this, SWT.NONE);
		headerLabelName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		headerLabelName.setText("parameter Name");
		
		Label headerLabelValue = new Label(this, SWT.NONE);
		headerLabelValue.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		headerLabelValue.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		headerLabelValue.setText("Value");


		populate();
	}
	
	
	
	public void populate()  {
		// TODO Auto-generated method stub
		
		
		
	Set<String> keys = arg.keySet();
	for (String key : keys) {
		
	
		
		
	 ArgModel att = arg.get(key);
		
		
		String val = att.getValue();		
		Boolean evalJavaScript = att.getIsJavascript();
		Boolean isparam = att.getIsParam();
		
		
		CallFunctionAttribut callatt = new CallFunctionAttribut(key,val,evalJavaScript,isparam);
		
		
		
		callArguments.add(callatt);
		
		populateSingleParameter(callatt);
	}
		
	}


	public List<CallFunctionAttribut> getCallArguments() {
		return callArguments;
	}


	private void populateSingleParameter(CallFunctionAttribut callatt) {
	Label newName = new Label(this, SWT.NONE);
	newName.setText(callatt.getName());
	newName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
	TextInputView tinpu = new TextInputView(this,SWT.NONE);
	ArgModel newArgmo = new ArgModel(callatt.getName());
	newArgmo.setIsJavascript(callatt.isJs());
	newArgmo.setName(callatt.getName());
	newArgmo.setValue(callatt.getValue());
	tinpu.SetArgModel(newArgmo);
	
/*	tinpu.setInputData(callatt.getValue());
	tinpu.setJavascript(callatt.isJs());
	
	*/
	tinpu.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
		
	}

}

