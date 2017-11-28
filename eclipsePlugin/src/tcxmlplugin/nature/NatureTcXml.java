package tcxmlplugin.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import tcxmlplugin.Activator;

public class NatureTcXml implements  IProjectNature 
{
	
	public  static final String NATUREID = "tcxml.natureid";
	
	private IProject project;
	

	@Override
	public void configure() throws CoreException {
		StringBuilder sb =new StringBuilder();
		sb.append("Configuring montrigen Nature  for project ").append(getProject().getName());
		Activator.getDefault().log(sb.toString(), IStatus.INFO, null);
IProjectDescription desc = project.getDescription();
		
		
		
		desc.setComment("a project performing selenium automation");
		
	}

	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
		
	}

}
