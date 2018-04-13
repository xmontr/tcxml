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

import tcxmlplugin.wizzard.ImportScriptTcxmlWizzard;

public class ImportTestCaseHandler  extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		
		
		Shell curentshell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		WizardDialog dialog = new WizardDialog(curentshell, new ImportScriptTcxmlWizzard());
		
		
		
		
        if (dialog.open() == Window.OK) {
            System.out.println("Ok pressed");
        } else {
            System.out.println("Cancel pressed");
        }
		
		
		return null;
		

}
	

	
}