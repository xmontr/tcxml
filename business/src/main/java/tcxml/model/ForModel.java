package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class ForModel extends AbstractModel {
	
	
	
	
	private ArgModel init;
	
	private ArgModel condition;
	
	
	private ArgModel increment ;
	
	
	
	public ForModel() {
		
		
		init = new ArgModel("init");
		init.setValue("");
		condition = new ArgModel("condition");
		condition.setValue("");
		
		increment = new ArgModel("increment");
		increment.setValue("");
	}
	
	


	public ArgModel getInit() {
		return init;
	}


	public void setInit(ArgModel init) {
		propertyChangeSupport.firePropertyChange("init", this.init,
				this.init = init);

	}


	public ArgModel getCondition() {
		return condition;
	}


	public void setCondition(ArgModel condition) {
		propertyChangeSupport.firePropertyChange("condition", this.condition,
				this.condition = condition);
		this.condition = condition;
	}


	public ArgModel getIncrement() {
		return increment;
	}


	public void setIncrement(ArgModel increment) {
		propertyChangeSupport.firePropertyChange("increment", this.increment,
				this.increment = increment);
		
	}





	
	
	
	
	
	
	
	
	

}
