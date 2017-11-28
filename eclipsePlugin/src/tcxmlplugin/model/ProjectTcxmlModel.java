package tcxmlplugin.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ProjectTcxmlModel {
	
	private String projectName ;
	
	private String projectPath;
	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		propertyChangeSupport.firePropertyChange("projectPath", this.projectPath,
		this.projectPath = projectPath);
	}

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		propertyChangeSupport.firePropertyChange("projectName", this.projectName,
				this.projectName = projectName);
		
	}
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }
	


}