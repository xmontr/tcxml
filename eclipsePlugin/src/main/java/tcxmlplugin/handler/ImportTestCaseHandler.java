package tcxmlplugin.handler;

import java.awt.Dialog;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.wizzard.ImportScriptTcxmlWizzard;

public class ImportTestCaseHandler  extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ImportScriptTcxmlWizzard wiz = new ImportScriptTcxmlWizzard();
	
		ISelection theSelection = HandlerUtil.getCurrentSelection(event);
		
		if(theSelection instanceof IStructuredSelection){
			
			IFolder testCaseFolder =(IFolder)((IStructuredSelection) theSelection).getFirstElement();
			  IProject currentProject = testCaseFolder.getProject();
			  wiz.setCurrentProject(currentProject);
			  wiz.setTestCaseFolder(testCaseFolder);
			  
			  
			  
	
		
		
		Shell curentshell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		
		
		
		WizardDialog dialog = new WizardDialog(curentshell,wiz );
		
		
		
		
        if (dialog.open() == Window.OK) {
        	
        	wiz.proceedToImport();
        	
            
        } else {
            TcXmlPluginController.getInstance().info("import cancelled");
        }
		
		
	
		

}
	
		return null;
	}
	
}