package tcxml.core;

import jdk.nashorn.api.scripting.AbstractJSObject;
import net.bytebuddy.implementation.bind.annotation.Super;
import tcxml.core.parameter.StepParameter;

public class LrAPI   {
	
	
	TcXmlController controller;
	
	
	public  String scriptDir;

	
	public LrAPI(TcXmlController tcXmlController) {
		
		
		controller = tcXmlController ;
		scriptDir=controller.getScriptDir();
		
	}
	
	public String getParam( String param) throws TcXmlException {
		
		StepParameter stepParam = controller.getParameterByName(param);
		String ret = stepParam.evalParameter() ;
		return ret;
	}
	
	
	
	public void log( String message, String level) {
		controller.getLog().info("JS message:level="+level +"\r\n"+ message);	
		
		
	}
	
	
	public void userDataPoint(String name, String value) {
		
		
		controller.getLog().info("userDataPoint=" + value);	
		
	}
	

	

}
