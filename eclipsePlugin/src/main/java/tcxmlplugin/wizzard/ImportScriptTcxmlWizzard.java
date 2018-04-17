package tcxmlplugin.wizzard;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ImportScriptTcxmlWizzard  extends  Wizard implements IImportWizard {
	
	
	private ChooseScriptPage chooseScriptPage;
	
	
	private IProject currentProject;
	
	
	private IFolder testCaseFolder ;


	private ValidateImportPage validateImportPage;
	
	
	



	public ImportScriptTcxmlWizzard() {
		
		setWindowTitle("Import truclient script");
		
		
		
		
		
		
		
	}
	
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	
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



	@Override
	public void addPages() {
		
		chooseScriptPage = new ChooseScriptPage("choose the script");
		
		addPage(chooseScriptPage);
		
		
		
		validateImportPage = new ValidateImportPage("validate import");
		
		
		
		addPage(validateImportPage);
		
		
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		
		if(page == chooseScriptPage) {
			
			validateImportPage.showValidationData(chooseScriptPage.getSelectedDirectory(),currentProject,testCaseFolder);
			
			
			return validateImportPage;
		}
		
		return null;
		
		
	

	}
	
	
	
	@Override
	public boolean canFinish() {
		
		
		return true;
		
	}
	

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	

}
