package tcxmlplugin.wizzard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import tcxmlplugin.Activator;
import tcxmlplugin.TcXmlController;

public class ProjectTcxmlWizzard  extends Wizard    implements IWorkbenchWizard {
	
	private ProjectTcxmlFormPage formpage;
	
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean performFinish() {
		boolean ret=true;
		
		Job j = new Job("Tcxml project creation") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				IStatus ret = Status.OK_STATUS;
				try {
					
					TcXmlController.getInstance().createSkeletonProject(formpage.getProjectName(), monitor);
				} catch (Exception e) {
					//
					//RemoteJmxConnectionManager.getInstance()
					Activator.getDefault().log(e.getMessage(), IStatus.ERROR, e);
					ret = Status.CANCEL_STATUS;
				}
				
				finally {
					monitor.done();
					
				}
				return ret;
			}
		};
		
		
		
		
		j.schedule();

		
		return ret;
	}
	
	

	
	
	
	
	
public ProjectTcxmlWizzard () {
	setWindowTitle("JMX Montrigen project creation");
}


@Override
public void addPages() {
	formpage = new ProjectTcxmlFormPage("main");
	addPage(formpage );
}

	

}