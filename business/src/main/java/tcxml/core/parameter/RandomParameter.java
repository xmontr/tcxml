package tcxml.core.parameter;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

public class RandomParameter extends StepParameter {
	
	
	private String format;
	
	private String generateNewVal;
	
	private String maxValue;
	
	private String minValue;
	
	private String originalValue;
	

	

	protected RandomParameter(HierarchicalINIConfiguration conf ,String  secname) {
		super(conf,secname,StepParameterType.RANDOM);
	format = config.getString("Format");
	generateNewVal = config.getString("GenerateNewVal");
	maxValue = config.getString("MaxValue");
	minValue = config.getString("MinValue");
	originalValue = config.getString("OriginalValue");
	
	}




	@Override
	public String evalParameter() {
		// TODO Auto-generated method stub
		return "not implemented";
	}

}
