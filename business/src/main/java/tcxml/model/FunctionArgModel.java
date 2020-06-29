package tcxml.model;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class FunctionArgModel extends AbstractModel{
	
	
	private String name ;
	private String type ;	
	private boolean optional ;
	
	
	
	public FunctionArgModel(String name) {
		super();
		this.name = name;

	}


	
	
	
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

	this.type =src.getJsonString("type").getString()	 ;
	JsonString opt = src.getJsonString("optional");
	if(opt != null) {
		if(opt.getString().equals("true")) {
			this.optional = true;
			
		}else {
			this.optional = false ;
		}
		
	}else {
		
		this.optional = false ;	
	}
	

	}
	
	
	

}
