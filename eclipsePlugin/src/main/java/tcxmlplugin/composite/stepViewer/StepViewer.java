package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.StepRunner;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;
import tcxmlplugin.composite.StepToolBar;
import tcxmlplugin.composite.StepView;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.layout.GridData;

public  class StepViewer extends Composite{
	
	
	
	
	protected StepView view;
	
	
	public StepView getViewer() {
		return view;
	}






	public StepRunner getRunner() {
		return runner;
	}





	protected StepRunner runner ;
	private StepToolBar stepToolBar;
	
	
	

	public StepViewer(Composite parent, int style , StepView view) {
		super(parent, style);
		
		setLayout(new GridLayout(1, false));

		
		stepToolBar = new StepToolBar(this, SWT.NONE, this);
		stepToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		this.view=view;
		view.setParent(this);
	GridData gd = new GridData();
	gd.grabExcessHorizontalSpace=true;
	gd.grabExcessVerticalSpace=true;
	gd.horizontalAlignment=gd.FILL;
	gd.verticalAlignment=gd.FILL;
		this.view.setLayoutData(gd);
		
		
		
	}
	
	
	
	
	
	
	public String getTitle() {
		return view.getTitle();
		
	}
	
	
	
	
	
	public void populate(Step mo  ) throws TcXmlException  {
		
		
		
		view.populate( mo  );
		stepToolBar.setIndex(mo.getIndex());
		
	}
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		view.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
			  view.removePropertyChangeListener(listener);
		  }
	
	
	

	
	
	
	

	
	
	
	
	
	

}
