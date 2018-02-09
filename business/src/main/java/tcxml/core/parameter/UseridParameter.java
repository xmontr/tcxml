package tcxml.core.parameter;

import org.apache.commons.configuration.SubnodeConfiguration;

public class UseridParameter extends StepParameter {
	
	
	
	private String format;
	
	private String originalValue;
	
	
	

	protected UseridParameter(SubnodeConfiguration c) {
		super(c);
	format = config.getString("Format");
	originalValue = config.getString("OriginalValue");
	}




	@Override
	public String evalParameter() {
		// TODO Auto-generated method stub
		return "1";
	}
	
	


}
