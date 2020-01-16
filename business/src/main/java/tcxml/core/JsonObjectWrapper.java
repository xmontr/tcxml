package tcxml.core;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import jdk.nashorn.api.scripting.AbstractJSObject;


/***
 *    use to avoid to use directly JsonObject in a javascript context and for jsonStringimpl instead of java.lang.String 
 * 
 * 
 * 
 * @author montrxa
 *
 */
public class JsonObjectWrapper extends AbstractJSObject {
	
	
	
	private JsonObject jsonob;

	public JsonObjectWrapper( JsonObject  jsonob) {
		
	this.jsonob = 	jsonob ;
		
	}
	
	
	
	@Override
	public Object getMember(String name) {
		
		Object ret = null;
		
		JsonValue val = jsonob.get(name) ;
		if(val.getValueType().equals(ValueType.STRING)) {
			
			ret = jsonob.getJsonString(name).getString();
			
		}
		
		if(val.getValueType().equals(ValueType.NUMBER)) {
			
			ret = jsonob.getJsonNumber(name).longValueExact();
			
		}
		
		
		return ret ;
		
	}

}
