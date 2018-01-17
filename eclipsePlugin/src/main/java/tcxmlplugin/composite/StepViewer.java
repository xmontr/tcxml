package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.Step;

public abstract class StepViewer extends Composite  {
	
	
	public StepViewer(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public  abstract String getTitle()  ;
	
	public  abstract  Step getModel() ;

	public abstract void populate(Step mo)  ;

}
