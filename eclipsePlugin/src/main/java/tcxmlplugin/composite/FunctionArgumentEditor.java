package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.FunctionArgModel;
import tcxmlplugin.composite.view.arguments.FunctionArg;

import org.eclipse.swt.layout.FillLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;

public class FunctionArgumentEditor extends Composite{

	public FunctionArgumentEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		// TODO Auto-generated constructor stub
	}
	
	

	
	public void edit(HashMap<String, FunctionArgModel> model ) {
		
	Set<String> listarg = model.keySet();
	for (String argname : listarg) {
		
		FunctionArgModel theargmodel = model.get(argname);
		FunctionArg theargview = new FunctionArg(this, getStyle());
		theargview.setThearg(theargmodel);
		
	}
		
		
		
	}
	
	

}
