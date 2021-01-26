package tcxml.core;

import java.util.HashMap;
import java.util.Map;

public enum StepType {
	
	CALLFUNCTION("callFunction"),
	UTIL("util"),
	BLOCK("block"),
	FUNCTION("function"),
	TESTOBJECT("testObject"),
	ALTERNATIVE("alternative"),
	CONTROL("control"),
	GENERICAPISTEP("genericAPIStep"),
	RUNLOGIC("runLogic");
	
	
	StepType(String string) {
		this.name= string ;
	}

	private String name;
	
	
	public String getName() {
		return name;
	}
	
	
    //****** Reverse Lookup Implementation************//
	 
    //Lookup table
    private static final Map<String, StepType> lookup = new HashMap<>();
    
    
    static
    {
        for(StepType env : StepType.values())
        {
            lookup.put(env.getName().toLowerCase(), env);
        }
    }
	
	
    //This method can be used for reverse lookup purpose
    public static StepType get(String name) throws TcXmlException
    {
    	
    	StepType ret = lookup.get(name.toLowerCase());
    	if(ret == null) {
    		throw new TcXmlException("Unknown sttep type:" + name, new IllegalArgumentException());
    		
    	}
        return ret;
    }
	
	
	

}
