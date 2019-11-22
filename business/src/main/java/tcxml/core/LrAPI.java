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
	
	
	
	public void setParam ( String name, String val) {
		controller.setParameter(name, val);
		
	}
	
	
	
	public void log( String message, String level) {
		controller.getLog().info("JS message:level="+level +"\r\n"+ message);	
		
		
	}
	
	public void log( String message) {
		controller.getLog().info("JS message:level=Standard \r\n"+ message);	
		
		
	}
	
	
	public void userDataPoint(String name, String value) {
		
		
		controller.getLog().info("userDataPoint=" + value);	
		
	}
	
	
	public void vtcConnect(String server, int port , String vtsname) throws TcXmlException {
		
		String sport = new Integer(port).toString();
		
	controller.vtsConnect(vtsname, server, sport);	
		
	}
	
	
	public void vtcDisconnect(String vtsname) throws TcXmlException {
		
		
		controller.vtsDisconnect(vtsname);
		
	}
	
	
	public void vtcAddCells( String colnames , String values, int option , String vtsname) throws TcXmlException {
		
		String soption = new Integer(option).toString();
		
		controller.vtsAddCells(vtsname, colnames, values, soption);
	}
	

	

}
