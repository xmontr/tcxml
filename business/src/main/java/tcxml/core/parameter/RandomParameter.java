package tcxml.core.parameter;

import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

public class RandomParameter extends StepParameter {
	
	
	private String format;
	
	private String generateNewVal;
	
	private String maxValue;
	
	private String minValue;
	
	private String originalValue;

	private IntStream intstream;
	

	

	protected RandomParameter(HierarchicalINIConfiguration conf ,String  secname) {
		super(conf,secname,StepParameterType.RANDOM);
	format = config.getString("Format");
	generateNewVal = config.getString("GenerateNewVal");
	maxValue = config.getString("MaxValue");
	minValue = config.getString("MinValue");
	originalValue = config.getString("OriginalValue");
	
	Random r = new Random();
	int randomNumberOrigin = new Integer(minValue);
	int randomNumberBound= new Integer(maxValue);
	intstream = r.ints(randomNumberOrigin, randomNumberBound);
	
	}




	@Override
	public String evalParameter() {

		int res = intstream.findAny().getAsInt();
		
		
		
		
		return String.format(format, new Integer(res));
	}

}
