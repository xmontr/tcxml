package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
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

import static org.hamcrest.Matchers.instanceOf;

import java.beans.PropertyChangeListener;

import javax.sql.rowset.Joinable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;

public  class StepViewer extends Composite  {
	
	public ExpandItem getParentExpandItem() {
		return parentExpandItem;
	}





	protected StepView view;
	protected StepRunner runner ;
	private StepToolBar stepToolBar;
	private Composite contentView;
	private Label horizontalLabel;
	private ExpandItem parentExpandItem;
	private StepContainer container;
	private Label verticalabel;
	private Object breakPoint;
	
	
	
	
	public StepContainer getContainer() {
		return container;
	}






	private void setContainer(StepContainer container) {
		this.container = container;
	}






	public void setParentExpandItem(ExpandItem parentExpandItem) {
		this.parentExpandItem = parentExpandItem;

	}
	
	/**
	 * 
	 *  expanditem is created with a fix height, may be expansed or not, and can be inside parent expanbar.
	 *  
	 *  reset the size of the expanditem
	 * 
	 */
	
	
	public void refreshSizeExpanditem() {
		//reset size of current
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				parentExpandItem.setHeight(computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
				parentExpandItem.getParent().redraw();
				
			}
		});
		// refresh size of parent
		StepContainer pa = getContainer();
		
		if( ! (pa instanceof TopStepContainer)) {
			StepView theview = (StepView) pa;
			theview.getViewer().refreshSizeExpanditem();
			
		} else { // resiize the top container
			
			TopStepContainer topc = (TopStepContainer) pa;
			topc.refreshSize();
		}
		

		

	
		
		
		
		
	}
	
	public   StepViewer( Composite parent , int style) {
		
		super(parent, style);
		buildGUI();
		
	}
	
	

	public   StepViewer( int style, StepView view, StepContainer container) {
		super(container.getBar(), style);
buildGUI();
	setView(view);
	setContainer(container);
		
	}


public void buildGUI() {
	setLayout(new GridLayout(2, false));
	
	verticalabel = new Label(this, SWT.NONE);
	verticalabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2));
	verticalabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
	verticalabel.setText(" ");
	
	stepToolBar = new StepToolBar(this, SWT.NONE);
	stepToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	stepToolBar.setStepviewer(this);
	
	contentView = new Composite(this, SWT.NONE);
	contentView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
contentView.setLayout(new GridLayout(1, false));
new Label(this, SWT.NONE);

horizontalLabel = new Label(this, SWT.NONE);
horizontalLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
horizontalLabel.setText("   aaa");
horizontalLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
horizontalLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));

setMenu(buildMenu());
	
}





	
	
	private Menu buildMenu() {
		   Menu popupMenu = new Menu(this);
		   
		   
		   
		
		   
		   
		   
		   
		   
		    MenuItem addbreakpointitem = new MenuItem(popupMenu, SWT.CASCADE);
		    addbreakpointitem.setText("set break point");
		    addbreakpointitem.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					setBreakPoint();
					
				}
			});
		    
		    
		    
		    MenuItem removebreakpointItem = new MenuItem(popupMenu, SWT.CASCADE);
		    removebreakpointItem.setText("remove break point");
		    removebreakpointItem.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					removeBreakpoint();
					
				}
			});
		    
		    
		    
		    
		    
		    

		    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
		    deleteItem.setText("Delete");
		    
		    
		    
		    
	return popupMenu;
}






	private  void setView(StepView view) {
		this.view = view;
		// color for the viewer
		horizontalLabel.setBackground(SWTResourceManager.getColor(view.getColor()));
		horizontalLabel.setForeground(SWTResourceManager.getColor(view.getColor()));
		verticalabel.setBackground(SWTResourceManager.getColor(view.getColor()));
		verticalabel.setForeground(SWTResourceManager.getColor(view.getColor()));
		
		view.setViewer(this);
		view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		view.setParent(contentView);
		view.layout();	
		contentView.layout();		
		layout();
		
	
		
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
			  
			  
			  if(breakPoint != null) {
				  
				  
				  synchronized (breakPoint) {
					 TcXmlPluginController.getInstance().info("waiting on break point step " + getTitle());
					  
					  try {
						  
						  TcXmlPluginController.getInstance().setCurrentBreakPoint(breakPoint);
						breakPoint.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						throw new TcXmlException(" failure waiting on breakpoint", e);
					}
					
				}
				  
			  }
			
			PlayingContext ret = view.play(ctx);
			
			return ret;
			  
			  
		  }
		  



		public  PlayingJob  getplayInteractiveJob(PlayingContext ctx )  {
			
			
			PlayingJob j = new PlayingJob(this, ctx);
			return j;
			
			

			
			
		}
		
		
		
		
		public void expand() {
			parentExpandItem.setExpanded(true);
			refreshSizeExpanditem();
	
			

			
			
			
		}
		
		
		public void collapse() {
			
			
			parentExpandItem.setExpanded(false);
			
		}
		
		
		public TcXmlController getController() {
			
			return view.getController();
			
		}














		
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return view.getTitle();
		}
		
		
		
		public StepView getViewer() {
			return view;
		}






		public StepRunner getRunner() {
			return runner;
		}
		
		
		public void setBreakPoint() {
			
			this.breakPoint=new Object();
			this.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
			
		}


public void removeBreakpoint() {
	
	this.breakPoint = null;
	this.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
	
}



	
		
}
