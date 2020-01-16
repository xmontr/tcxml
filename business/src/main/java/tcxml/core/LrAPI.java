package tcxml.core;

import javax.json.JsonObject;

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
	

	public void setLogLevel(String format, Object param) {
		
	controller.getLog().warning(" setloglevel is not implemented yet  ");	
	}
	
	
	public void restoreLogLevel() {
		
		controller.getLog().warning(" restoreLogLevel is not implemented yet  ");	
		
	}
	
	
	public JsonObjectWrapper vtcPopCells( String vtsname, String variable) throws TcXmlException {
		
		
		JsonObject ret = controller.vtsPopCells(vtsname, variable);
		PlayingContext ctx = controller.getCurrentPlayingContext();

		
		//get the data value
		JsonObject value = ret.getJsonObject("data");
		JsonObjectWrapper jsob = new JsonObjectWrapper(value);
		//controller.addToCurrentJScontext(ctx,variable,jsob);
		
		return jsob;
	}
	
	
	public JsonObjectWrapper vtcPopCells( String vtsname) throws TcXmlException {
		
		return vtcPopCells(vtsname, "data");
		
	}
	
	
	
	

}
