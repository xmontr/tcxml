package tcxmlplugin.model;

public class ForModel extends AbstractModel {
	
	
	
	
	private String init;
	
	private String condition;
	
	
	private String increment ;


	public String getInit() {
		return init;
	}


	public void setInit(String init) {
		propertyChangeSupport.firePropertyChange("init", this.init,
				this.init = init);

	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		propertyChangeSupport.firePropertyChange("condition", this.condition,
				this.condition = condition);
		this.condition = condition;
	}


	public String getIncrement() {
		return increment;
	}


	public void setIncrement(String increment) {
		propertyChangeSupport.firePropertyChange("increment", this.increment,
				this.increment = increment);
		
	}
	
	
	
	
	
	
	
	
	

}
