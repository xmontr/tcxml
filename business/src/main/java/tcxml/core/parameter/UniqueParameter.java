package tcxml.core.parameter;

import org.apache.commons.configuration.HierarchicalINIConfiguration;

import tcxml.core.TcXmlException;

public class UniqueParameter extends StepParameter  {
	
	private String StartValue ;
	
	private String OutOfRangePolicy;
	
	private String OriginalValue ;
	
	private String GenerateNewVal ;
	
	
	private String Format ;
	
	private String BlockSize ;
	
	
	private Long currentValue;
	
	

	protected UniqueParameter(HierarchicalINIConfiguration conf, String secname) {
		super(conf, secname, StepParameterType.UNIQUE);
		this.Format = config.getString("Format");
		this.GenerateNewVal = config.getString("GenerateNewVal");
		this.BlockSize = config.getString("BlockSize");
		this.OriginalValue = config.getString("OriginalValue");
		this.OutOfRangePolicy = config.getString("OutOfRangePolicy");
		this.BlockSize = config.getString("BlockSize");
		this.StartValue = config.getString("StartValue");
		
		currentValue = Long.parseLong(StartValue);
		
		
		
		
		
		

	}

	@Override
	public String evalParameter() throws TcXmlException {
String ret =	String.format(Format, currentValue);
		currentValue++;
		return ret;
	}

}
