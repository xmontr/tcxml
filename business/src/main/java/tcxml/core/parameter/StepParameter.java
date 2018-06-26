package tcxml.core.parameter;

import java.util.Iterator;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import tcxml.core.TcXmlException;

public abstract  class StepParameter {
	
	
	public static  enum StepParameterType {
		CURRENTITERATION,
		TABLE,
		UNIQUE,
		USERID,
		RANDOM
		
		
		
	}
	
	
	protected StepParameterType parameterType;
	
	
	public String getType() {
		return type;
	}

	public StepParameterType getParameterType() {
		return parameterType;
	}

	protected SubnodeConfiguration config;
	
	protected String type ; 
	
	protected String paramName;
	
	protected HierarchicalINIConfiguration conf;
	
	protected String secname ;
	
	protected StepParameter (HierarchicalINIConfiguration conf ,String  secname , StepParameterType typeparam ) {
		parameterType = typeparam ;
		 config = conf.getSection(secname);
		 this.secname = secname;
		 this.conf = conf;
		 
		 
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
	
	
	
	
	public static StepParameter buildParameter(HierarchicalINIConfiguration conf, String secname) throws TcXmlException {

		StepParameter ret = null ;
			
		 SubnodeConfiguration se = conf.getSection(secname);
		String type = se.getString("Type");	
		
		switch(type) {
		case "CurrentIteration":ret = getCurrentIterationParameter( conf, secname); break;
		case "Random": ret = getRandomParameter( conf, secname); break;
		case "Table": ret = getTableParameter( conf, secname); break;
		case "Userid": ret = getUserIdParameter( conf, secname) ; break;
		case "Unique":ret = getUniqueParameter(conf, secname);break;
		default: ret = getDefaultParameter( conf, secname,type); break;
		
		}

		return ret;
		
		
		
		
		
		
	}
	
	private static StepParameter getUniqueParameter(HierarchicalINIConfiguration conf, String secname) {
		UniqueParameter p = new UniqueParameter(conf,secname);
		
		return p;
	}







	private static StepParameter getDefaultParameter(HierarchicalINIConfiguration conf, String secname, String type) throws TcXmlException {
		throw new TcXmlException("unsupported parameter type" + type +  "for parameter " + secname, new IllegalAccessException());
	
	}

	private static StepParameter getUserIdParameter(HierarchicalINIConfiguration conf, String secname) {
		UseridParameter up = new UseridParameter(conf,secname);
		return up;
	}

	private static StepParameter getTableParameter(HierarchicalINIConfiguration conf, String secname) {
		TableParameter tp = new TableParameter(conf,secname);
		
		return tp;
	}

	private static StepParameter getRandomParameter(HierarchicalINIConfiguration conf, String secname) {
		RandomParameter rp = new RandomParameter(conf,secname);
		return rp;
	}

	private static StepParameter getCurrentIterationParameter(HierarchicalINIConfiguration conf, String secname) {
		   CurrentIterationParameter cp = new CurrentIterationParameter(conf,secname);
		return cp;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
		public abstract String evalParameter  ()   throws TcXmlException ;
	

}
