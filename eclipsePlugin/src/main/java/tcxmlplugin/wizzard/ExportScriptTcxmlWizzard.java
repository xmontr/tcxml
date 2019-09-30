package tcxmlplugin.wizzard;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ExportScriptTcxmlWizzard extends  Wizard implements IImportWizard {
	
	
	 private ChooseScriptPage chooseScriptPage;


	public ExportScriptTcxmlWizzard() {
		setWindowTitle("Export truclient script as Protractor Script");
	}
	
	
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean performFinish() {
		if(! ( chooseScriptPage.getSelectedDirectory()  == null  ||  chooseScriptPage.getSelectedDirectory().isEmpty() ) )
		return true;
		else return false ;
	}
	
	
	@Override
	public void addPages() {
		
		chooseScriptPage = new ChooseScriptPage("choose the script");
		
		addPage(chooseScriptPage);
		
		
		

		
		
	}
	
	
	public Path getExportPath()  {
		
		return Paths.get(chooseScriptPage.getSelectedDirectory());
		
	}
	
	
	

}
