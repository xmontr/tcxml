package tcxml.core.parameter;

import java.util.Collection;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import tcxml.core.TcXmlException;

public class DynamicParameter  extends StepParameter {
	
	
	private String value;
	
	
	public DynamicParameter(String thename, String thevalue) {
		super(null, thename, StepParameterType.DYNAMIC);		


		this.value = thevalue ;
		
	}

	

	@Override
	public String evalParameter() throws TcXmlException {
		// TODO Auto-generated method stub
		return value;
	}

}
