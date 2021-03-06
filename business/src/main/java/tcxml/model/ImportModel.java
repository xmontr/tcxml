package tcxml.model;

import java.util.List;



import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


public class ImportModel extends AbstractModel {
	


	
	
	private List<String> parameters;
	
	private String mainScript;
	
	
	
	private List<String> libraries ;
	
	
	private List<String> snapshots;
	
	private List<String> extrafiles ; 
	
	
	public List<String> getExtrafiles() {
		return extrafiles;
	}








	public void setExtrafiles(List<String> extrafiles) {
		propertyChangeSupport.firePropertyChange("extrafiles", this.extrafiles,
				this.extrafiles = extrafiles);
		
	}








	public String getMainScript() {
		return mainScript;
	}








	public List<String> getSnapshots() {
		return snapshots;
	}








	public void setSnapshots(List<String> snapshots) {
		
		propertyChangeSupport.firePropertyChange("snapshots", this.snapshots,
				this.snapshots = snapshots);
	}








	public void setMainScript(String mainScript) {
		propertyChangeSupport.firePropertyChange("mainScript", this.mainScript,
				this.mainScript = mainScript);
		
	}








	public ImportModel() {
		
		super();
		parameters = new ArrayList<String>();
		libraries = new ArrayList<String>();
		
	}
	
	
	
	
	



	public List<String> getLibraries() {
		return libraries;
	}








	public void setLibraries(List<String> libraries) {
		propertyChangeSupport.firePropertyChange("libraries", this.libraries,
				this.libraries = libraries);
	
	}








	public List<String>getParameters() {
		return parameters;
	}


	public void setParameters(List<String> parameters) {
		propertyChangeSupport.firePropertyChange("parameters", this.parameters,
				this.parameters = parameters);
		
		
		
		
	}









	
	



}


