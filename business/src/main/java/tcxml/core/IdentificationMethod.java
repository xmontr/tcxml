package tcxml.core;

import java.util.HashMap;
import java.util.Map;

public enum IdentificationMethod {
	XPATH("XPath"),
	JAVASCRIPT("Javascript");
	
	
	private String name;
	
	IdentificationMethod( String n){
		
		this.name = n;
	}

	public String getName() {
		return name;
	}
	

	
    //****** Reverse Lookup Implementation************//
	 
    //Lookup table
    private static final Map<String, IdentificationMethod> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(IdentificationMethod env : IdentificationMethod.values())
        {
            lookup.put(env.getName(), env);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static IdentificationMethod get(String name)
    {
        return lookup.get(name);
    }
	
	
	

}
