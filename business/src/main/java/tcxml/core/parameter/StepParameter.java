package tcxml.core.parameter;

import java.util.Iterator;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import tcxml.core.TcXmlException;

public abstract  class StepParameter {
	
	
	
	protected SubnodeConfiguration config;
	
	protected String type ; 
	
	protected String paramName;
	
	
	
	protected StepParameter ( SubnodeConfiguration c ) {
		
		this.config = c;
		type=config.getString("Type");
		this.paramName = config.getString("ParamName");
		this.setName(paramName);
	}
	
	
	
	public static String getNameFromSection( String secname) throws TcXmlException {
		
	String[] part = secname.split(":");
	if(!part[0].equals("parameter")) {
		throw new TcXmlException("invalid name for section:"+ secname, new IllegalArgumentException()
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
		case "CurrentIteration":ret = getCurrentIterationParameter( se, secname); break;
		case "Random": ret = getRandomParameter( se, secname); break;
		case "Table": ret = getTableParameter( se, secname); break;
		case "Userid": ret = getUserIdParameter( se, secname) ; break;
		default: ret = getDefaultParameter( se, secname); break;
		
		}

		return ret;
		
		
		
		
		
		
	}
	
	private static StepParameter getDefaultParameter(SubnodeConfiguration se, String secname) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepParameter getUserIdParameter(SubnodeConfiguration se, String secname) {
		UseridParameter up = new UseridParameter(se);
		return up;
	}

	private static StepParameter getTableParameter(SubnodeConfiguration se, String secname) {
		TableParameter tp = new TableParameter(se);
		
		return tp;
	}

	private static StepParameter getRandomParameter(SubnodeConfiguration se, String secname) {
		RandomParameter rp = new RandomParameter(se);
		return rp;
	}

	private static StepParameter getCurrentIterationParameter(SubnodeConfiguration se, String secname) {
		   CurrentIterationParameter cp = new CurrentIterationParameter(se);
		return cp;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
		public abstract String evalParameter();
	

}
