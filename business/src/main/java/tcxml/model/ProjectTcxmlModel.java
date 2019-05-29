package tcxml.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;


public class ProjectTcxmlModel extends AbstractModel {
	
	private String projectName ;
	
	private String projectPath;
	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		propertyChangeSupport.firePropertyChange("projectPath", this.projectPath,
		this.projectPath = projectPath);
	}

	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		propertyChangeSupport.firePropertyChange("projectName", this.projectName,
				this.projectName = projectName);
		
	}


	
	

	


}