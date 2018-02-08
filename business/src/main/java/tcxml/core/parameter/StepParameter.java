package tcxml.core.parameter;

import java.util.Iterator;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import tcxml.core.TcXmlException;

public abstract  class StepParameter {
	
	
	
	public static String getNameFromSection( String secname) throws TcXmlException {
		
	String[] part = secname.split(":");
	if(!part[0].equals("parameter")) {
		throw new TcXmlException("invalide name for section:"+ secname, new IllegalArgumentException()
				);
		
	}
		String ret = part[1];
		
		
		
		return ret;
		
	}
	
	
	
	
	public static StepParameter buildParameter(HierarchicalINIConfiguration conf, String secname) {

		StepParameter ret = null ;
		 SubnodeConfiguration se = conf.getSection(secname);	
		String type = se.getString("Type");	
		
		switch(type) {
		case "CurrentIteration":ret = getCurrentIterationParameter( conf, secname); break;
		case "Random": ret = getRandomParameter( conf, secname); break;
		case "Table": ret = getTableParameter( conf, secname); break;
		case "Userid": ret = getUserIdParameter( conf, secname) ; break;
		default: ret = getDefaultParameter( conf, secname); break;
		
		}

		return ret;
		
		
		
		
		
		
	}
	
	private static StepParameter getDefaultParameter(HierarchicalINIConfiguration conf, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepParameter getUserIdParameter(HierarchicalINIConfiguration conf, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepParameter getTableParameter(HierarchicalINIConfiguration conf, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepParameter getRandomParameter(HierarchicalINIConfiguration conf, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepParameter getCurrentIterationParameter(HierarchicalINIConfiguration conf, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
