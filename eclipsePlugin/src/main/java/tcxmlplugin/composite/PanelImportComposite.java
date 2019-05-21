package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import tcxml.core.TcXmlException;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.TcXmlPluginException;
import tcxml.model.ImportModel;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;

import java.util.ArrayList;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Path;



public class PanelImportComposite extends Composite {
	private DataBindingContext m_bindingContext;
	
	
	

	
	private  ImportModel model= new ImportModel();
	private Text scriptText;
	private List librarieslist;
	private List parametersList;
	
	
	private IProject currentProject;
	
	
	private IFolder testCaseFolder ;
	
	
	private String tcName;
	private List snapshotlist;
	private List extraFileslist;
	
	
	
	
	
	
	

	public String getTcName() {
		return tcName;
	}
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	public IProject getCurrentProject() {
		return currentProject;
	}
	public void setCurrentProject(IProject currentProject) {
		this.currentProject = currentProject;
	}
	public IFolder getTestCaseFolder() {
		return testCaseFolder;
	}
	public void setTestCaseFolder(IFolder testCaseFolder) {
		this.testCaseFolder = testCaseFolder;
	}
	public PanelImportComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label scriptLabel = new Label(this, SWT.NONE);
		scriptLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		scriptLabel.setText("Script:");
		
		scriptText = new Text(this, SWT.BORDER);
		scriptText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label librariesLabel = new Label(this, SWT.NONE);
		librariesLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		librariesLabel.setText("Libraries:");
		
		librarieslist = new List(this, SWT.BORDER);
		librarieslist.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label parametersLabel = new Label(this, SWT.NONE);
		parametersLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		parametersLabel.setText("Parameters:");
		
		parametersList = new List(this, SWT.BORDER);
		parametersList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblExtrafiles = new Label(this, SWT.NONE);
		lblExtrafiles.setText("ExtraFiles");
		
		extraFileslist = new List(this, SWT.BORDER);
		extraFileslist.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSnapshots = new Label(this, SWT.NONE);
		lblSnapshots.setText("snapshots:");
		
		snapshotlist = new List(this, SWT.BORDER);
		snapshotlist.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}
	public void proceedToImport() {
		
		
		Job jobimport = new Job("import test case") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IStatus ret = Status.OK_STATUS;
		try {	
			
		
			TcXmlPluginController.getInstance().importTestcase(tcName, model, currentProject, testCaseFolder, monitor);
		
		ret = Status.OK_STATUS;
			} catch (Exception e1) {
				ret=Status.CANCEL_STATUS;
				TcXmlPluginController.getInstance().error("fail import testcase", e1);
				
			
			}
		
		return ret;		
			
		}	

	};	
		
		
	
	jobimport.schedule();
	
		
		
		
		
		
		
	}
	public void populate(String selectedDirectory) throws TcXmlPluginException {
		
	 IPath p = new org.eclipse.core.runtime.Path(selectedDirectory);
	 String tcnewname= p.lastSegment() ;
	 
	 if(  TcXmlPluginController.getInstance().isAlreadyExistingTestCase(tcnewname, testCaseFolder)) {
		 
		throw new TcXmlPluginException("Testcase with the same name " + tcnewname + " already exist", new IllegalArgumentException()) ;
		 
	 }
	 
	 setTcName(tcnewname);
	IPath mainfilpath = TcXmlPluginController.getInstance().findMainFile(selectedDirectory);
	model.setMainScript(mainfilpath.toOSString());

	
	java.util.List<String> paramfilelist = TcXmlPluginController.getInstance().findParameterFiles(selectedDirectory);
	model.setParameters(paramfilelist);
	
	
	java.util.List<String> listlib = TcXmlPluginController.getInstance().getLibraries(selectedDirectory);
		model.setLibraries(listlib);
		
		
		java.util.List<String> listsnap = TcXmlPluginController.getInstance().getSnapshots(selectedDirectory);
		model.setSnapshots(listsnap);
		
		java.util.List<String> listextrafiles = TcXmlPluginController.getInstance().getExtraFiles(selectedDirectory);
		model.setExtrafiles(listextrafiles);
	
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextScriptTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(scriptText);
		IObservableValue mainScriptMoObserveValue = BeanProperties.value("mainScript").observe(model);
		bindingContext.bindValue(observeTextScriptTextObserveWidget, mainScriptMoObserveValue, null, null);
		//
		IObservableList itemsLibrarieslistObserveWidget = WidgetProperties.items().observe(librarieslist);
		IObservableList librariesMoObserveList = BeanProperties.list("libraries").observe(model);
		bindingContext.bindList(itemsLibrarieslistObserveWidget, librariesMoObserveList, null, null);
		//
		IObservableList itemsParametersListObserveWidget = WidgetProperties.items().observe(parametersList);
		IObservableList parametersMoObserveList = BeanProperties.list("parameters").observe(model);
		bindingContext.bindList(itemsParametersListObserveWidget, parametersMoObserveList, null, null);
		//
		IObservableList itemsSnapshotlistObserveWidget = WidgetProperties.items().observe(snapshotlist);
		IObservableList snapshotsModelObserveList = BeanProperties.list("snapshots").observe(model);
		bindingContext.bindList(itemsSnapshotlistObserveWidget, snapshotsModelObserveList, null, null);
		//
		IObservableList itemsExtraFileslistObserveWidget = WidgetProperties.items().observe(extraFileslist);
		IObservableList extraFilesModelObserveList = BeanProperties.list("extrafiles").observe(model);
		bindingContext.bindList(itemsExtraFileslistObserveWidget, extraFilesModelObserveList, null, null);
		return bindingContext;
	}
}
