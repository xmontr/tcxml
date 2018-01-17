package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.StepRunner;
import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;
import tcxmlplugin.composite.StepToolBar;
import tcxmlplugin.composite.StepViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.layout.GridData;

public abstract class AbstractStepViewer extends Composite{
	
	
	
	
	protected StepViewer viewer;
	
	
	public StepViewer getViewer() {
		return viewer;
	}






	public StepRunner getRunner() {
		return runner;
	}





	protected StepRunner runner ;
	
	
	

	public AbstractStepViewer(Composite parent, int style , StepViewer viewer) {
		super(parent, style);
		
		setLayout(new GridLayout(1, false));

		
		StepToolBar stepToolBar = new StepToolBar(this, SWT.NONE, this);
		stepToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		this.viewer=viewer;
		viewer.setParent(this);
	GridData gd = new GridData();
	gd.grabExcessHorizontalSpace=true;
	gd.grabExcessVerticalSpace=true;
	gd.horizontalAlignment=gd.FILL;
	gd.verticalAlignment=gd.FILL;
		this.viewer.setLayoutData(gd);
		
		
		
	}
	
	
	
	
	
	
	public String getTitle() {
		return viewer.getTitle();
		
	}
	
	
	
	
	
	public void populate(Step mo  ) {
		
		
		
		viewer.populate( mo  );
	}
	
	
	

	
	
	
	

	
	
	
	
	
	

}
