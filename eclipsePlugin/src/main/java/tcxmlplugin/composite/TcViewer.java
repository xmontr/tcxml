package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import org.eclipse.swt.widgets.ProgressBar;

public class TcViewer extends Composite implements PropertyChangeListener, IJobChangeListener  {
	private ActionsViewer actionsViewer;
	private Map<String, Step> actionMap;
	private Map<String, TruLibrary> libraryMap;
	private LibraryViewer libraryViewer;
	private TcXmlController controller;
	private ProgressBar progressBar;
	private CTabFolder tabFolder;

	public TcViewer(Composite parent, int style, TcXmlController tccontroller) {
		super(parent, style);

		setLayout(new GridLayout(1, false));
		
		this.controller=tccontroller;
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		
		ToolItem recorditem = new ToolItem(toolBar, SWT.PUSH);
		recorditem.setToolTipText("Record");
		recorditem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-record-2.png"));
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem startitem = new ToolItem(toolBar, SWT.NONE);
		startitem.setToolTipText("Play");
		startitem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		
		ToolItem pauseitem = new ToolItem(toolBar, SWT.NONE);
		pauseitem.setToolTipText("Pause");
		pauseitem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-pause-2.png"));
		
		ToolItem saveItem = new ToolItem(toolBar, SWT.NONE);
		saveItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/document-export.png"));
		
		ToolItem stopItem = new ToolItem(toolBar, SWT.NONE);
		stopItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-stop-2.png"));
		
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setVisible(false);
		
		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setTabPosition(SWT.BOTTOM);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/function-icon_16.png"));
		tbtmNewItem.setText("Functions");
		
		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/script-icon_16.png"));
		tbtmNewItem_1.setText("Actions");
		
		actionsViewer = new ActionsViewer(tabFolder, SWT.BORDER,controller);
		tbtmNewItem_1.setControl(actionsViewer);
		
		this.libraryViewer = new LibraryViewer(tabFolder, SWT.BORDER,controller);
		tbtmNewItem.setControl(libraryViewer);
		
		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder, SWT.NONE);
		
		
		tbtmNewItem_2.setText("Run Logic");
		
		
		
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

	public void populate() {
			

this.populateAction( controller.getActionMap());
this.populateLibrary(controller.getLibraries());
		
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
			progressBar.setVisible(true);	
			
				
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
				progressBar.setVisible(false);
				
				
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
	
	
	
	

}
