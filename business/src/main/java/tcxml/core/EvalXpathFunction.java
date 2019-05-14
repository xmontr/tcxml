package tcxml.core;

import javax.script.ScriptException;

import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class EvalXpathFunction extends AbstractJSObject  {
	
	
	private TcXmlController controller;
	
	

	public EvalXpathFunction( TcXmlController tc) {
		this.controller = tc;
		
		
	}
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true; 
	}
	
	
	@Override
	public Object call(Object thiz, Object... args) {
	String xpath = 	(String) args[0];
	WebElement ret;
	try {
		ret = controller.evalXPath(xpath);
	} catch (TcXmlException e) {
		throw new RuntimeException(e);
	}
	return ret;
	}
	
	

}


