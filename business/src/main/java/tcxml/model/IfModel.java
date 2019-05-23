package tcxml.model;

public class IfModel extends AbstractModel{
	
	private ArgModel condition;
	
	
	
	public IfModel() {
		condition = new ArgModel("condition");
	}
	
	
	
	public ArgModel getCondition() {
		return condition;
	}


	public void setCondition(ArgModel condition) {
		propertyChangeSupport.firePropertyChange("condition", this.condition,
				this.condition = condition);
		this.condition = condition;
	}
	
	

}
