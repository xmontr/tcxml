package tcxml.model;

public class AlternativeModel extends AbstractModel{
	
	
	private String activeStep ;

	public String getActiveStep() {
		return activeStep;
	}

	public void setActiveStep(String activeStep) {
		propertyChangeSupport.firePropertyChange("activeStep", this.activeStep,
				this.activeStep = activeStep);
		
	}

}
