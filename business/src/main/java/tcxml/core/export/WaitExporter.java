package tcxml.core.export;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class WaitExporter extends StepExporter {

	public WaitExporter(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step, lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String export() throws TcXmlException {
		ArgModel interval = argumentMap.get("Interval");
		ArgModel unit = argumentMap.get("Unit");
		String func = "TC.wait";	
		
		StringBuilder objarg  = new StringBuilder();
		objarg.append("{\n");
		String theinterval = interval.getValue();
		objarg.append("Interval:").append(theinterval).append("\n");
		String theunit = unit.getValue();
		objarg.append("Unit:").append(theunit).append("\n");		
		objarg.append("}\n");
		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,objarg.toString()					
					);
		return ret;	
	}

}
