package tcxmlplugin.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.dialog.AddTestCaseDialog;

public class addTestCaseHandler  extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		
		ISelection theSelection = HandlerUtil.getCurrentSelection(event);
		if(theSelection instanceof IStructuredSelection){
			
			IFolder selectedFolder =(IFolder)((IStructuredSelection) theSelection).getFirstElement();
			  IProject project = selectedFolder.getProject();
		
		
			
		try{
			
			
		Shell curentshell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		AddTestCaseDialog dialog = new AddTestCaseDialog(curentshell ,selectedFolder);
		
		if(dialog.open() == Window.OK){
			
			TcXmlPluginController.getInstance().info("adding Test case to project");
			TcXmlPluginController.getInstance().addTestCase2project(project, dialog.getNewTestCaseName());
		
		}
			
			
			
			
			
		
		} catch(Exception e){
			TcXmlPluginController.getInstance().error("exception when adding Test case", e);
			
		}
		
		
		}
		
		
		return null;
		
		
		
		
		
		

}
	
	
}
