package tcxml.core;

import java.util.HashMap;
import java.util.Map;

public enum StepAction {
	
	
	//runlogic action
	ACTION("action"),
	INITBLOCK("Init Block"),
	ACTIONBLOCK("Action Block"),
	ENDBLOCK("End Block"),
RUNBLOCK("Run Block"),

// testobject action
EVALJAVASCRIPT("Evaluate JavaScript"),
WAIT("Wait"),
COMMENTT("Comment"),
NAVIGATE("Navigate"),
TYPE("Type"),
CLICK("Click"),
MOUSEOVER("Mouse Over"),
MOUSEUP("Mouse Up"),
MOUSEDOWN("Mouse Down"),
SET("Set"),
VERIFY("Verify"),
SELECT("Select"),
WAITFORPROPERTY("Wait for Property"),

//
CALLACTION("Call Action"),
FOR("For"),
IF("If"),
IF2("If2");


	
	
	
	private String name;

	StepAction(String string) {
		this.name=string;
	}
	
	public String getName() {
		return name;
	}
	
	
    //****** Reverse Lookup Implementation************//
	 
    //Lookup table
    private static final Map<String, StepAction> lookup = new HashMap<>();
    
    
    static
    {
        for(StepAction env : StepAction.values())
        {
            lookup.put(env.getName().toLowerCase(), env);
        }
    }
	
	
    //This method can be used for reverse lookup purpose
    public static StepAction get(String name) throws TcXmlException
    {
    	
    	StepAction ret = lookup.get(name.toLowerCase());
    	if(ret == null) {
    		throw new TcXmlException("Unknown sttep action:" + name, new IllegalArgumentException());
    		
    	}
        return ret;
    }
	
	
	
	
	

}
