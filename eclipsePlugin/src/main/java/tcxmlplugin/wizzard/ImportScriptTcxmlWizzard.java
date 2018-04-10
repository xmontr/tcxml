package tcxmlplugin.wizzard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ImportProjectTcxmlWizzard  extends  Wizard implements IImportWizard {
	
	
	private ImportScriptPage formpage;



	public ImportProjectTcxmlWizzard() {
		
		setWindowTitle("Import truclient script");
		
		
	}
	
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	@Override
	public void addPages() {
		
		formpage = new ImportScriptPage("choose the script");
		
		addPage(formpage);

	}
	
	

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	

}
