package tcxml.core;

import tcxml.core.parameter.StepParameter;

public class LrAPI {
	
	
	TcXmlController controller;

	
	public LrAPI(TcXmlController tcXmlController) {
		
		
		controller = tcXmlController ;
		
	}
	
	public String getParam( String param) throws TcXmlException {
		
		StepParameter stepParam = controller.getParameterByName(param);
		String ret = stepParam.evalParameter() ;
		return ret;
	}

}
