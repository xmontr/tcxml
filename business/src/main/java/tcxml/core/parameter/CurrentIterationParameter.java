package tcxml.core.parameter;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

public class CurrentIterationParameter extends StepParameter{
	
	private String format;
	
	private String originalValue;
	
	
	
	

	protected CurrentIterationParameter(HierarchicalINIConfiguration conf ,String  secname) {
		super(conf,secname, StepParameterType.CURRENTITERATION);
		format = config.getString("Format");
		originalValue = config.getString("OriginalValue");
		paramName = config.getString("ParamName");
		format = config.getString("Format");
		format = config.getString("Format");
	}





	@Override
	public String evalParameter() {
		// TODO Auto-generated method stub
		return "not implemented";
	}

}
