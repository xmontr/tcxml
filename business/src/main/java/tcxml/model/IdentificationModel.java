package tcxml.model;

import java.util.List;

public class IdentificationModel extends AbstractModel
{
	
	
	
	
	private String xpath;
	
	private String javascript;
	
	
	private List<String> allMethods;

	private String selectedMethod;

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		propertyChangeSupport.firePropertyChange("xpath", this.xpath,this.xpath = xpath);
	
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		propertyChangeSupport.firePropertyChange("javascript", this.javascript,this.javascript = javascript);
	}



public List<String> getAllMethods() {
	return allMethods;
}

public void setAllMethods(List<String> allMethods) {
	propertyChangeSupport.firePropertyChange("allMethods", this.allMethods, this.allMethods = allMethods);
	
}

public String getSelectedMethod() {
	return selectedMethod;
}

public void setSelectedMethod(String selectedMethod) {
	propertyChangeSupport.firePropertyChange("selectedMethod", this.selectedMethod,
			this.selectedMethod = selectedMethod);
	
}

}


