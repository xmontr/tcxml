package tcxml.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GenericAPIModel extends AbstractModel implements PropertyChangeListener {
	
	
	private String methodName;
	
	private String selectedCategory;
	
	private List<String> allCategory;
	
	
	private List<String> allMethod;
	
	

	
	
	
	public List<String> getAllMethod() {
		return allMethod;
	}






	public void setAllMethod(List<String> allMethod) {
		propertyChangeSupport.firePropertyChange("allMethod", this.allMethod,this.allMethod = allMethod);
		
	}






	public GenericAPIModel() {
		
		ArrayList<String> li = new ArrayList<String>();
		li.add("IO");
		li.add("TC");
		li.add("UTILS");
		li.add("VTS");
		allCategory = li;
	
		// adapt the method to the category selected
		this.addPropertyChangeListener("selectedCategory", this);
		
	}






	public String getMethodName() {
		return methodName;
	}






	public void setMethodName(String methodName) {
		propertyChangeSupport.firePropertyChange("methodName", this.methodName,this.methodName = methodName);
		
	}






	public String getSelectedCategory() {
		return selectedCategory;
	}






	public void setSelectedCategory(String selectedCategory) {	
		propertyChangeSupport.firePropertyChange("selectedCategory", this.selectedCategory,this.selectedCategory = selectedCategory);
	}






	public List<String> getAllCategory() {
		return allCategory;
	}






	public void setAllCategory(List<String> allCategory) {
		this.allCategory = allCategory;
	}






	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	if(	evt.getPropertyName().equals("selectedCategory")) {
		
		setAllMethod(getMethodsForCategory((String)evt.getNewValue()));
		
	}
		
	}






	private List<String> getMethodsForCategory(String cat) {
		ArrayList<String> ret = new ArrayList<String>();
		switch(cat) {
		
		case "VTS": ret=getVTSmethods();break;
		case "IO": ret=getIOmethods();break;
		case "TC": ret=getTCmethods();break;
		case "UTILS": ret=getUTILSmethods();break;
		
		
		}
		
		
		return ret;
	}






	private ArrayList<String> getUTILSmethods() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		
		
		return ret;
	}






	private ArrayList<String> getTCmethods() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		
		
		return ret;
	}






	private ArrayList<String> getIOmethods() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		ret.add("notdone");
		
		
		return ret;
	}






	private ArrayList<String> getVTSmethods() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("vtcDisconnect");
		ret.add("vtcConnect");
		ret.add("vtcPopCells");
		ret.add("vtcPopMultipleCells");
		
		
		return ret;
	}







}
