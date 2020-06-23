package tcxml.model;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class FunctionArgModel extends AbstractModel{
	
	
	private String name ;
	private String type ;	
	
	public FunctionArgModel(String name) {
		super();
		this.name = name;

	}


	private boolean optional ;
	
	
	public boolean isOptional() {
		return optional;
	}


	public void setOptional(boolean optional) {
		propertyChangeSupport.firePropertyChange("optional", this.optional,
				this.optional = optional);		
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.name,
				this.name = name);
		
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		propertyChangeSupport.firePropertyChange("type", this.type,
				this.type = type);
		
	}
	
	
	public void populateFromJson( JsonObject src) {		
	JsonValue val = src.getJsonString("type")	;

	this.type = val.toString() ;		

	}
	
	
	

}
