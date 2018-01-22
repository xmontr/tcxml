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

import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;

public class TcViewer extends Composite implements PropertyChangeListener  {
	private ActionsViewer actionsViewer;
	private Map<String, Step> actionMap;
	private Map<String, TruLibrary> libraryMap;
	private LibraryViewer libraryViewer;

	public TcViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		
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
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setTabPosition(SWT.BOTTOM);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/function-icon_16.png"));
		tbtmNewItem.setText("Functions");
		
		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/script-icon_16.png"));
		tbtmNewItem_1.setText("Actions");
		
		actionsViewer = new ActionsViewer(tabFolder, SWT.BORDER);
		tbtmNewItem_1.setControl(actionsViewer);
		
		this.libraryViewer = new LibraryViewer(tabFolder, SWT.BORDER);
		tbtmNewItem.setControl(libraryViewer);
		
		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder, SWT.NONE);
		
		
		tbtmNewItem_2.setText("Run Logic");
		// TODO Auto-generated constructor stub
	}

	public void populateAction(Map<String, Step> actionmap) {
		
		this.actionMap = actionmap;
		
		 List<String> allActions =    new ArrayList<String>(actionmap.keySet())    ;
		 
		 TcXmlPluginController.getInstance().info(("fouded actions :" + allActions.size()  ))   ;
	
		 
		actionsViewer.getModel().setAllActions(allActions);
		
		actionsViewer.getModel().addPropertyChangeListener(ActionsModel.ACTION_SELECTED, this);
		
			
	}
	
	
	public void populateLibrary(Map<String, TruLibrary> libmap) {
	this.libraryMap = libmap;
	List<String> allLib =    new ArrayList<String>(libmap.keySet())    ;
	
	 TcXmlPluginController.getInstance().info(("fouded libraries :" + allLib.size()  ))   ;
	 
	 libraryViewer.getModel().setAllLibraries(allLib);
	 
	 libraryViewer.getModel().addPropertyChangeListener(LibraryModel.LIBRARY_SELECTED, this);
	
	
	 
	}
	
	
	
	
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ActionsModel.ACTION_SELECTED)) {
			
			String  selection = (String) evt.getNewValue();
			TcXmlPluginController.getInstance().info("selected action:" + selection);
			this.showSelectedAction(actionMap.get(selection));
			
			
		}
		
		
		if(evt.getPropertyName().equals(LibraryModel.LIBRARY_SELECTED)) {
			
			String  selection = (String) evt.getNewValue();
			TcXmlPluginController.getInstance().info("selected library:" + selection);
			this.showSelectedLibrary(libraryMap.get(selection));
			
			
		}
		
		
	}

	private void showSelectedLibrary(TruLibrary lib) {
		libraryViewer.showSelectedLibrary(lib);
		
	}

	private void showSelectedAction(Step step) {
		actionsViewer.showSelectedAction( step);
		
	}


	
	
	
	
	

}
