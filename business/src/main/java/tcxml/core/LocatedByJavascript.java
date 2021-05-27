package tcxml.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import tcxml.model.TestObject;


public class LocatedByJavascript implements ExpectedCondition<Boolean>{
	
	private String jsLocation;
	private TcXmlController controller;
	private ExecutionContext ctx;
	private TestObject to;
	
	
	public LocatedByJavascript(String jslocation, TcXmlController thecontroller,ExecutionContext thectx , TestObject to) {
		super();
		this.jsLocation = jslocation;
		this.controller = thecontroller;
		this.ctx=thectx;
		this.to = to;
	}
	
	
	








	@Override
	public Boolean apply(WebDriver input) {
		
		boolean ret = false ;
		
		try {
			FoundedElement el  = controller.identifyElement(to, ctx);
			ret=true;
		} catch (TcXmlException e) {
			// TODO Auto-generated catch block
			controller.getLog().info(e.getMessage());
		}		
		
		return ret;
	}

}
