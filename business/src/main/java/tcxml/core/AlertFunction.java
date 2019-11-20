package tcxml.core;

import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class AlertFunction extends AbstractJSObject{
	
	private TcXmlController controller;
	
	
	public AlertFunction( TcXmlController tc) {
		this.controller = tc;
		
		
	}
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true; 
	}
	
	
	@Override
	public Object call(Object thiz, Object... args) {
	String message = 	(String) args[0];

	
		 controller.getLog().info(message);	
		 return message ;

}

	
}