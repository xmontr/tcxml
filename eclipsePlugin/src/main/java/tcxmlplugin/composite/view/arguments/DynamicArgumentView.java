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
import tcxmlplugin.composite.view.TextInputView;

public class DynamicArgumentView extends StepArgument{
	
	private List<String> requiredValues ;

	public DynamicArgumentView(Composite parent, int style, List<String> values) {
		super(parent, style);
		this.requiredValues = values;
		setLayout(new GridLayout(2, false));
	}
	
	@Override
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(argu);
		ensurePresenceOfValues();
		
	Set<String> keys = argu.keySet();
	for (String key : keys) {	
		
		
	 ArgModel att = argu.get(key);		
		populateSingleParameter(att);
	}
	
	}

	
	private void ensurePresenceOfValues() {
		for (String val : requiredValues) {
			
			if( !arg.containsKey(val)) { // value not present so add it with empty val to see it in the GUI
				ArgModel newmodel = new ArgModel(val);
				newmodel.setValue("");
				arg.put(val, newmodel);
				
				
			}
			
		}
		
	}

	private void populateSingleParameter(ArgModel att) {
	Label newName = new Label(this, SWT.NONE);
	newName.setText(att.getName());
	newName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
	TextInputView tinpu = new TextInputView(this,SWT.NONE);
		tinpu.SetArgModel(att);
	

	tinpu.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	
		
	}
	

	
	
	
}
