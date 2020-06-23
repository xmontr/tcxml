package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.FunctionArgModel;
import tcxmlplugin.composite.view.arguments.FunctionArg;

import org.eclipse.swt.layout.FillLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;

public class FunctionArgumentEditor extends Composite{
	
	
	private List<FunctionArg> listArgumentView;

	public FunctionArgumentEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		listArgumentView = new ArrayList<FunctionArg>() ;
	}
	
	

	
	public void edit(HashMap<String, FunctionArgModel> model ) {
		
	Set<String> listarg = model.keySet();
	for (String argname : listarg) {
		
		FunctionArgModel theargmodel = model.get(argname);
		FunctionArg theargview = new FunctionArg(this, getStyle());
		theargview.populate(theargmodel);
		listArgumentView.add(theargview);
		
	}
		
		
		
	}
	
	
	public HashMap<String, FunctionArgModel> getModel() {
		HashMap<String, FunctionArgModel> model = new HashMap<String, FunctionArgModel>();
		
		for (FunctionArg functionArg : listArgumentView) {
			FunctionArgModel newarg = functionArg.getThearg();
			model.put(newarg.getName(), newarg);
			
		}
		return model;
		
	}
	
	

}
