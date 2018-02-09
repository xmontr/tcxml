package tcxml.core.parameter;

import org.apache.commons.configuration.SubnodeConfiguration;

public class RandomParameter extends StepParameter {
	
	
	private String format;
	
	private String generateNewVal;
	
	private String maxValue;
	
	private String minValue;
	
	private String originalValue;
	

	

	protected RandomParameter(SubnodeConfiguration c) {
		super(c);
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
