package tcxml.core;

import java.util.HashMap;
import java.util.Map;

public enum ProgressType {
	
	STEPSTARTED("Step started"),
	ACTIONSTARTED("Action started"),
	STEPCOMPLETED("Step completed"),
	AFTERSTEPCOMPLETED("After step completed"),
	ACTIONCOMPLETED("Action completed"),
	AFTERSTEPENDED("After step ended");
	
	
	private String  name;
	
	ProgressType( String n){
		
		this.name = n;
	}

	public String getName() {
		return name;
	}
	

	
    //****** Reverse Lookup Implementation************//
	 
    //Lookup table
    private static final Map<String, ProgressType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(ProgressType env : ProgressType.values())
        {
            lookup.put(env.getName().toLowerCase(), env);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static ProgressType get(String name)
    {
        return lookup.get(name.toLowerCase());
    }
	
	
	
	
	

}
