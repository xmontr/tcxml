package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandItem;

import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import org.eclipse.swt.layout.GridLayout;

import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.AStepContainer;
import tcxmlplugin.composite.StepToolBar;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.job.PlayingJob;

import java.beans.PropertyChangeListener;

import javax.sql.rowset.Joinable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;

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
	private Composite contentView;
	private Label lblNewLabel_1;
	private ExpandItem parentExpandItem;
	private StepContainer container;
	
	
	public StepContainer getContainer() {
		return container;
	}






	private void setContainer(StepContainer container) {
		this.container = container;
	}






	public void setParentExpandItem(ExpandItem parentExpandItem) {
		this.parentExpandItem = parentExpandItem;
	}

	public   StepViewer(Composite parent, int style, StepView view, StepContainer container) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblNewLabel.setText(" ");
		
		stepToolBar = new StepToolBar(this, SWT.NONE);
		stepToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stepToolBar.setStepviewer(this);
		
		contentView = new Composite(this, SWT.NONE);
		contentView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	contentView.setLayout(new GridLayout(1, false));
	new Label(this, SWT.NONE);
	
	lblNewLabel_1 = new Label(this, SWT.NONE);
	lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
	lblNewLabel_1.setText("   aaa");
	lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
	setView(view);
	setContainer(container);
		
	}







	
	
	private  void setView(StepView view) {
		this.view = view;
		view.setParent(contentView);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace=true;
		gd.grabExcessVerticalSpace=true;
		gd.horizontalAlignment=gd.FILL;
		gd.verticalAlignment=gd.FILL;
			this.view.setLayoutData(gd);
	
		
		layout();
		contentView.layout();
	}






	public  IJobChangeListener getInteractivePlayingJobListener() {
		
		return stepToolBar ;
		
	}
	
	
	
	
	
	
	public String getTitle() {
		return view.getTitle();
		
	}
	
	
	
	
	
	public void populate(Step mo  ) throws TcXmlException  {
		
		
		
		view.populate( mo  );
		
		
	}
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		view.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
			  view.removePropertyChangeListener(listener);
		  }
		  
		  
		  
		  public void hideToolbar() {
			  
			 stepToolBar.setVisible(false); 
			  
		  }
		  
		  
		  public void setPlayable(boolean isplayable) {
			  stepToolBar.setPlayable(isplayable);
			  
			  
			  
		  }



		  
		  
		  public PlayingContext play(PlayingContext ctx) throws TcXmlException {
			  
			PlayingContext ret = view.play(ctx);
			
			return ret;
			  
			  
		  }
		  



		public  PlayingJob  getplayInteractiveJob(PlayingContext ctx )  {
			
			
			PlayingJob j = new PlayingJob(this, ctx);
			return j;
			
			

			
			
		}
		
		
		
		
		public void expand() {
			
			parentExpandItem.setExpanded(true);
			
			
			
		}
		
		
		public void collapse() {
			
			
			parentExpandItem.setExpanded(false);
			
		}
		
		
		public TcXmlController getController() {
			
			return view.getController();
			
		}
		
}
