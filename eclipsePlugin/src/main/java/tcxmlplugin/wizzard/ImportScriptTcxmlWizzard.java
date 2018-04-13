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
		if(selection instanceof IStructuredSelection){
			
			IFolder testCaseFolder =(IFolder)((IStructuredSelection) selection).getFirstElement();
			   this.currentProject = testCaseFolder.getProject();
		
	}
	
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
			
			validateImportPage.showValidationData(chooseScriptPage.getSelectedDirectory());
			
			
			return validateImportPage;
		}
		
		return null;
		
		
	

	}
	
	
	
	@Override
	public boolean canFinish() {
		
		
		return false;
		
	}
	

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	

}
