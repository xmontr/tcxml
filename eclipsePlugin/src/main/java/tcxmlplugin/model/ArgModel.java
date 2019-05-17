package tcxmlplugin.model;

import javax.json.JsonObject;

public class ArgModel extends AbstractModel{
	
	
	
	private String name;
	
	
	private Boolean isJavascript ;
	
	
	private String value ;
	
	
	
	public ArgModel( String n) {
		
		this.name =n;
		this.isJavascript = false;
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.isJavascript,
				this.name = name);
		
	}


	public Boolean getIsJavascript() {
		return isJavascript;
	}


	public void setIsJavascript(Boolean isJavascript) {
		propertyChangeSupport.firePropertyChange("isJavascript", this.isJavascript,
				this.isJavascript = isJavascript);
		
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		propertyChangeSupport.firePropertyChange("value", this.value,
				this.value = value);
		
	}
	
	
	public void populateFromJson( JsonObject src) {
		
		setValue(src.getJsonString("value").getString());
		setIsJavascript( src.getBoolean("evalJavaScript",false));
	}
	
	
	

}
