package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class WaitModel extends AbstractModel {
	
	
	private ArgModel interval ;
	
	
	private String selectedunit;
	
	private List<String> allUnits;
	
	
	
	public WaitModel() {
		
		
		interval = new ArgModel("Interval");
		

		
				
		
	}


	public String getSelectedunit() {
		return selectedunit;
	}


	public void setSelectedunit(String selectedunit) {
		propertyChangeSupport.firePropertyChange("selectedunit", this.selectedunit,
				this.selectedunit = selectedunit);
		
	}


	public List<String> getAllUnits() {
		return allUnits;
	}


	public void setAllUnits(List<String> allUnits) {
		propertyChangeSupport.firePropertyChange("allUnits", this.allUnits,
				this.allUnits = allUnits);
		this.allUnits = allUnits;
	}


	public ArgModel getInterval() {
		return interval;
	}


	public void setInterval(ArgModel interval) {
		propertyChangeSupport.firePropertyChange("interval", this.interval,
				this.interval = interval);
	
	}
	
	
	public static ArgModel getDefaultArgInterval() {
		ArgModel ret = new ArgModel("Interval");
		ret.setValue("3");
		return ret;
	}



	
	

}
