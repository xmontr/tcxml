package tcxmlplugin.composite.view.arguments;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.CallFunctionAttribut;
import tcxml.model.ListArgModel;
import tcxmlplugin.composite.view.ListInputView;
import tcxmlplugin.composite.view.TextInputView;

public class DynamicArgumentView extends StepArgument{
	


	public DynamicArgumentView(Composite parent, int style,HashMap<String, ArgModel> arg ) throws TcXmlException {
		super(parent, style,arg);
		
		setLayout(new GridLayout(2, false));
		populate();
	}
	

	public void populate() throws TcXmlException {
		// TODO Auto-generated method stub

		
	Set<String> keys = arg.keySet();
	for (String key : keys) {	
		
		
	 ArgModel att = arg.get(key);		
		populateSingleParameter(att);
	}
	
	}

	


	private void populateSingleParameter(ArgModel att) {
	Label newName = new Label(this, SWT.NONE);
	newName.setText(att.getName());
	newName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

	
	if(att instanceof ListArgModel ) { // list value
		
		ListInputView linput = new ListInputView(this,SWT.NONE);
		linput.setListValue(((ListArgModel)att).getValueList() );
		linput.setValue(att.getValue());
		
		
	}else { // single value
		
		TextInputView tinpu = new TextInputView(this,SWT.NONE);
		tinpu.SetArgModel(att);
	

	tinpu.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		
	}

	
		
	}
	

	
	
	
}
