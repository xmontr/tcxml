package tcxml.model;

import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class ArgModel extends AbstractModel{
	
	
	
	private String name;
	
	
	private Boolean isJavascript ;
	
	private Boolean isParam ;
	
	
	private String value ;
	
	
	
	public ArgModel( String n) {
		
		this.name =n;
		this.isJavascript = false;
		this.isParam=false;
		
	}


	public Boolean getIsParam() {
		return isParam;
	}


	public void setIsParam(Boolean isParam) {
		this.isParam = isParam;
	}


	public String getName() {
		return this.name;
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
	JsonValue val = src.get("value")	;
	ValueType ty = val.getValueType();
	if(ty.equals(ty.STRING))
	{
		setValue(src.getJsonString("value").getString());
		if(src.containsKey("evalJavaScript")) {
			setIsJavascript( src.getBoolean("evalJavaScript",false));
			
		}
		else {
		setIsJavascript(false);	
			
		}
		
			
		
	}
	
	if(ty.equals(ty.NUMBER))
	{
	Long loval = src.getJsonNumber("value").longValueExact();
		setValue(loval.toString());
		setIsJavascript( false);		
		
	}
	
	
	if(src.containsKey("param")) {
		setIsParam( src.getBoolean("param",false));
		
	}
	else {
		setIsParam(false);	
		
	}
			
		
		
	
		

	}



	
	
	

}
