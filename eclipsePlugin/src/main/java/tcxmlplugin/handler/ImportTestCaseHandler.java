package tcxmlplugin.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class ImportTestCaseHandler  extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		ISelection theSelection = HandlerUtil.getCurrentSelection(event);
		if(theSelection instanceof IStructuredSelection){
			
			IFolder selectedFolder =(IFolder)((IStructuredSelection) theSelection).getFirstElement();
			  IProject project = selectedFolder.getProject();
			  
			  Shell curentshell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
			  
		
		
		
		return null;
	}

}
