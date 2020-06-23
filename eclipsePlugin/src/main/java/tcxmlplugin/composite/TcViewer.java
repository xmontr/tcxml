package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Button;

import static org.hamcrest.Matchers.instanceOf;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.ResourceManager;

import stepWrapper.RunLogicWrapper;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.parameter.StepParameter;
import tcxml.model.Step;
import tcxml.model.Transaction;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.TcXmlPluginException;
import tcxmlplugin.composite.parameter.ParameterViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.TopStepContainer;
import tcxmlplugin.composite.view.CallActionView;
import tcxmlplugin.composite.view.FunctionView;
import tcxmlplugin.composite.view.RunBlockView;
import tcxmlplugin.job.PlayingJob;
import tcxmlplugin.wizzard.ExportScriptTcxmlWizzard;
import tcxml.model.ActionsModel;
import tcxml.model.LibraryModel;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.SelectionAdapter;

public class TcViewer extends Composite implements PropertyChangeListener, IJobChangeListener  {
	private ActionsViewer actionsViewer;
	
	
	private RunLogicViewer runLogicViewer ;
	
	
	private ParameterViewer parameterviewer;
	
	
	public TcXmlController getController() {
		return controller;
	}


	private TransactionViewer transactionViewer;

	private LibraryViewer libraryViewer;
	private TcXmlController controller;
	private CTabFolder tabFolder;


	private Map<String, Step> actionMap;


	private Map<String, TruLibrary> libraryMap;


	private ToolBar scriptToolbar;
	private CoolBar videoRecorderToolbar;
	private IFolder tcfolder;
	
	
	private TopStepContainer  currentTopStep=null;
	private Label statusbar;


	private VideoRecorderComposite videoRecorderComposite;


	private RunlogicAndPalettecomposite runlogicAndPaletteView;

	public TcViewer(Composite parent, int style, TcXmlController tccontroller, IFolder testcasefolder) {
		super(parent, style);

		setLayout(new GridLayout(1, false));
		scriptToolbar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		videoRecorderToolbar= new CoolBar(this, SWT.FLAT | SWT.RIGHT); 

		
		
		tabFolder = new CTabFolder(this, SWT.BORDER);
		this.tcfolder = testcasefolder;
		this.controller=tccontroller;
		

		
		actionsViewer = new ActionsViewer(tabFolder, SWT.BORDER,controller);
		this.libraryViewer = new LibraryViewer(tabFolder, SWT.BORDER,controller);
		
		//this.runLogicViewer = new RunLogicViewer(tabFolder, SWT.BORDER,controller);
		this.runlogicAndPaletteView = new RunlogicAndPalettecomposite(tabFolder, SWT.BORDER);
		this.runLogicViewer = new RunLogicViewer(this.runlogicAndPaletteView.getRunlogicview(), SWT.BORDER,controller);
		
		
		
		
		this.parameterviewer = new ParameterViewer(tabFolder, SWT.BORDER,controller);
		this.transactionViewer = new TransactionViewer(tabFolder, SWT.BORDER,controller);
		
		statusbar = new Label(this, SWT.NONE);
		statusbar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
		
		
		
		
		
		buildTabFolder();
	
		buildScriptToolbar();
		buildVideorecorderToolbar();

		
		
		
	}

	private void buildVideorecorderToolbar() {
		// record button that manage the start/stop of the video recording
		videoRecorderToolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		CoolItem coolItem = new CoolItem(videoRecorderToolbar, SWT.NONE);
		
		videoRecorderComposite = new VideoRecorderComposite(videoRecorderToolbar, SWT.NONE);
		
		coolItem.setControl(videoRecorderComposite);
		
		videoRecorderComposite.getCheckButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selection = ((Button)e.getSource()).getSelection();
				TcXmlPluginController.getInstance().manageVideo(selection, getController());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		videoRecorderComposite.pack();
		   org.eclipse.swt.graphics.Point size = videoRecorderComposite.getSize();
;
		    coolItem.setSize(coolItem.computeSize(size.x, size.y));
		
	
	
		
	}

	public IFolder getTcfolder() {
		return tcfolder;
	}

	private void buildTabFolder() {
		
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setTabPosition(SWT.BOTTOM);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem functionTab = new CTabItem(tabFolder, SWT.NONE);
		functionTab.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/function-icon_16.png"));
		functionTab.setText("Functions");
		functionTab.setControl(libraryViewer);
		
		CTabItem actionTab = new CTabItem(tabFolder, SWT.NONE);
		actionTab.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/script-icon_16.png"));
		actionTab.setText("Actions");
		actionTab.setControl(actionsViewer);
		

		
		
		
		
		CTabItem logicTab = new CTabItem(tabFolder, SWT.NONE);		
		logicTab.setText("Run Logic");
		logicTab.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/Gear-icon_16.png"));
		//logicTab.setControl(runLogicViewer);  
		logicTab.setControl(this.runlogicAndPaletteView);

		CTabItem CTabItemparameterTab = new CTabItem(tabFolder, SWT.NONE);
		CTabItemparameterTab.setText("Parameters");
		CTabItemparameterTab.setControl(parameterviewer);
		
		
		CTabItem CTabItemtransactionTab = new CTabItem(tabFolder, SWT.NONE);
		CTabItemtransactionTab.setText("transactions");
		CTabItemtransactionTab.setControl(transactionViewer);
		
	}

	private void buildScriptToolbar() {
		
		
		

		
		
		
		
		////
		ToolItem recorditem = new ToolItem(scriptToolbar, SWT.PUSH);
		recorditem.setToolTipText("Record");
		recorditem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-record-2.png"));
		
		
		
		
		
		ToolItem startitem = new ToolItem(scriptToolbar, SWT.NONE);
		startitem.setToolTipText("Play");
		
		startitem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				
				
				if(TcXmlPluginController.getInstance().isOnBreakpoint()) {
					TcXmlPluginController.getInstance().releaseBreakpoint();					
					
				} else { // lauch the script
					try {
					RunLogicViewer rlv = TcXmlPluginController.getInstance().getTcviewer().runLogicViewer ;
						
					rlv.play();
					} catch (TcXmlException e) {
						TcXmlPluginController.getInstance().error("failure in playing script ", e);
					}
					
					
					
				}
				
			}
		});
		
		
		
		
		
		startitem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		
		ToolItem pauseitem = new ToolItem(scriptToolbar, SWT.NONE);
		pauseitem.setToolTipText("Pause");
		pauseitem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-pause-2.png"));
		
		ToolItem saveItem = new ToolItem(scriptToolbar, SWT.NONE);
		saveItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TcXmlPluginController.getInstance().info("saving script to disk");
				TcXmlPluginController.getInstance().saveModel();
				
			}
		});
		saveItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/document-export.png"));
	
		
		ToolItem stopItem = new ToolItem(scriptToolbar, SWT.NONE);
		stopItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-stop-2.png"));
		
		
		ToolItem toolItem = new ToolItem(scriptToolbar, SWT.SEPARATOR);
		
		
		ToolItem snapshotToolItem = new ToolItem(scriptToolbar, SWT.CHECK);
		
		snapshotToolItem.setText("view snapshot");
		snapshotToolItem.setSelection(true);
		
		
		snapshotToolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selection = ((ToolItem)e.getSource()).getSelection();
				actionsViewer.setSnapshotLayout(selection);
				libraryViewer.setSnapshotLayout(selection);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ToolItem exportitem = new ToolItem(scriptToolbar, SWT.PUSH);
		exportitem.setToolTipText("Export current run Logic ");
		exportitem.setText("Export");
		exportitem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<StepViewer> li = runLogicViewer.stepViwerChildren ;
				
				ExportScriptTcxmlWizzard wiz = new ExportScriptTcxmlWizzard();
				
			WizardDialog dialog = new WizardDialog(getShell(), wiz);
			
		
			
			
	        if (dialog.open() == Window.OK) {
	        	
	        	Path expath = wiz.getExportPath() ;
	        	
	        	TcXmlPluginController.getInstance().export(runLogicViewer, libraryViewer,actionsViewer , expath);
	        	
	        	 TcXmlPluginController.getInstance().info("script exported in " + expath.toString() );
	        	
	            
	        } else {
	            TcXmlPluginController.getInstance().info("export cancelled");
	        }
			

				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	public LibraryViewer getLibraryViewer() {
		return libraryViewer;
	}

	private void populateAction(Map<String, Step> actionmap) {
		
		this.actionMap = actionmap;
		
	
		 
		 
		 
		 actionsViewer.buildAllActions(actionmap);		
		actionsViewer.getModel().addPropertyChangeListener(ActionsModel.ACTION_SELECTED, this);
		
			
	}
	
	
	private void populateLibrary(Map<String, TruLibrary> libmap) {
		
		
	this.libraryMap = libmap;
	
	libraryViewer.buildAllLibraries(libmap);

	 
	 libraryViewer.getModel().addPropertyChangeListener(LibraryModel.LIBRARY_SELECTED, this);
	
	
	 
	}
	
	
	
	
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ActionsModel.ACTION_SELECTED)) {
			
			String  selection = (String) evt.getNewValue();
			TcXmlPluginController.getInstance().info("selected action:" + selection);
			this.showSelectedAction(selection);
			
			
		}
		
		
		if(evt.getPropertyName().equals(LibraryModel.LIBRARY_SELECTED)) {
			
			String  selection = (String) evt.getNewValue();
			TcXmlPluginController.getInstance().info("selected library:" + selection);
			this.showSelectedLibrary(selection);
			
			
		}
		
		
	}

	private void showSelectedLibrary(String  libname) {
		libraryViewer.showSelectedLibrary(libname);
		
	}

	private void showSelectedAction(String actName) {
		
		
		
		actionsViewer.showSelectedAction( actName);
		
		
		
	}
	
	
	public String getcurrentVideoName() {
		
		return videoRecorderComposite.getVideoFilename();
		
	}

	public void populate() {
			

this.populateAction( controller.getActionMap());
this.populateLibrary(controller.getLibraries());
this.populateRunLogic(controller.getRunLogic());
this.populateParameter(controller.getParameters());
this.populateTransactions(controller.getAlltransactions());




videoRecorderComposite.setDefaultVideoName(TcXmlPluginController.getInstance().getDefaultVideoName());
		
	}

	private void populateTransactions(HashMap<Transaction, TruLibrary> hashMap) {
		try {
			transactionViewer.populate(hashMap);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error(" fail to load transactions", e);
		}
		
	}

	private void populateParameter(Map<String, StepParameter> parameters) {
		try {
			parameterviewer.populate(parameters);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error(" fail to load parameters", e);
		}	
		
	}

	private void populateRunLogic(RunLogicWrapper runLogic) {
		
		try {
			runLogicViewer.populate(runLogic);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error(" fail to load runlogic", e);
			e.printStackTrace();
		}
		
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
			
			
				
			}


		});
			

		
	}

	@Override
	public void awake(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done(IJobChangeEvent event) {
		
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
				
				
			}
		});
		
	
		
	}

	@Override
	public void running(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduled(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sleeping(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
/***
 *  show the lib in the viewer
 * 
 * @param libname
 */

	public void switch2function(String libname) {
		
		tabFolder.setSelection(0);
		libraryViewer.showLibrary(libname);
		
	}

	
	
	/***
	 * 
	 *  make sure that the step viewer is visible in the UI
	 * 
	 * @param stepviewer
	 */
	
public void ensureVisibility(StepViewer stepviewer) {
	
	
	updateTopContainer(stepviewer);
	stepviewer.expandSync();
	if(currentTopStep != null) {
		
		currentTopStep.showOnTop(stepviewer);
	}else {
		
		controller.getLog().warning("currenttopstep is null when ensur visibility of " + stepviewer.getTitle());
		
	}
	

	
	

	
	
}






/**
 *  switch between Functions / Actions and runlogic when 
 *  
 *  a call function is executed
 * a call action
 * 
 * 
 * @param stepviewer
 * @return
 */


	private void updateTopContainer(StepViewer stepviewer) {
		
		
		/**
		 *  find the topstepcontainer 
		 * 
		 */
		//Control currentControl = stepviewer;

			  currentTopStep =  stepviewer.getTopContainer();
			  
			  
				  
			  
			  if(currentTopStep instanceof RunLogicViewer) {				  
				  tabFolder.setSelection(2);
				
				  
			  }
	  if(currentTopStep instanceof LibraryView) {
		  
		  FunctionView view = null;
		  if(stepviewer.getViewer() instanceof FunctionView) { // step is a funcction viewer
			  view =(FunctionView) stepviewer.getViewer();	  
			  
		  } else {
			  
			  // step part of a function so find the first functonviewer parent
			view = stepviewer.getFunctionViewContainer();
			  
			  
		  }
		  
		  

		  
		  String libname =  view.getLibName() ;
		  switch2function(libname );
				  
				  
			  }
	  
	  
	  if(currentTopStep instanceof ActionView) {		  
		  
		  String actionName =((ActionView)
				  currentTopStep).getActionName();		  
		  switch2Action(actionName );
			  }
	  
	  

		
		
		
		/*
		 * StepView view = stepviewer .getViewer();
		 * 
		 * 
		 * if(view instanceof FunctionView) {// start execute newfunction also switch
		 * tab
		 * 
		 * String libname = ((FunctionView ) view).getLibName() ;
		 * switch2function(libname ); currentTopStep = (LibraryView)
		 * view.getViewer().getContainer();
		 * 
		 * return; }
		 * 
		 * if(view instanceof CallActionView || view instanceof RunBlockView) {
		 * switch2runlogic(); currentTopStep = runLogicViewer;
		 * 
		 * }
		 * 
		 * 
		 * StepContainer pa = stepviewer.getContainer(); if( pa instanceof ActionView )
		 * { String actionName =((ActionView)
		 * stepviewer.getContainer()).getActionName(); switch2Action(actionName );
		 * 
		 * currentTopStep = (ActionView)pa;
		 * 
		 */
		
		
		
		
		
		
		return;
		
		}
		
		
		

	

	private void switch2runlogic() {
		tabFolder.setSelection(2);
	
}

	public ActionsViewer getActionsViewer() {
		return actionsViewer;
	}

	private void switch2Action(String actionName) {
		tabFolder.setSelection(1);
		actionsViewer.showAction(actionName);
		
	}
	
	
	public void displayStatus(String status) {
		

		
		try {
		
	getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
				if(!statusbar.isDisposed()) {
					statusbar.setText(status);	
					
				}
				
				
				
			}
		});
		
		
		} catch(SWTException e)	{
			
			
		}
		
		
	}
	
	
	
	
	/**
	 * 
	 *  synchronize the view with the model for all actions of the script
	 * 
	 * 
	 * @param monitor
	 * @throws TcXmlPluginException 
	 */
	

	public void synchronizeActions(IProgressMonitor monitor) throws TcXmlPluginException {
		actionsViewer.synchronizeAllActions(monitor);
		
	}
	
	
	public void synchronizeLogic (IProgressMonitor monitor) throws TcXmlPluginException {
		
		runLogicViewer.synchronizeLogic(  monitor);
	}
	
	
	public void synchronizeLibraries (IProgressMonitor monitor) throws TcXmlPluginException {
		
		libraryViewer.synchronizeAllLibraries(monitor);
	}
	
	
	public Set<String> getAllLibrariesNames() {
		
		return libraryMap.keySet();
		
	}
	
	
	
	

}
