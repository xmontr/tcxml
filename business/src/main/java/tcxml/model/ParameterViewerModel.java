package tcxml.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ParameterViewerModel extends AbstractModel {
	
	public static final String PARAMETER_SELECTED = "parameterSelected";

	
	public List<String> getAllParameters() {
		return allParameters;
	}


	public void setAllParameters(List<String> allParameters) {
		propertyChangeSupport.firePropertyChange("allParameters", this.allParameters,
				this.allParameters = allParameters);
		
	}


	public String getParameterSelected() {
		return parameterSelected;
	}


	public void setParameterSelected(String parameterSelected) {
		propertyChangeSupport.firePropertyChange(PARAMETER_SELECTED, this.parameterSelected,
				this.parameterSelected = parameterSelected);
		
		
	}

	private List<String> allParameters;
	
	private String parameterSelected;
	
	
	public ParameterViewerModel() {
		super();
		
	}


	
	

	
	
	
	

}
