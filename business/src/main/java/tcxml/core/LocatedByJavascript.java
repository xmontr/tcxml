package tcxml.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class LocatedByJavascript implements ExpectedCondition<Boolean>{
	
	private String jsLocation;
	private TcXmlController controller;
	private ExecutionContext ctx;
	
	
	public LocatedByJavascript(String jslocation, TcXmlController thecontroller,ExecutionContext thectx) {
		super();
		this.jsLocation = jslocation;
		this.controller = thecontroller;
		this.ctx=thectx;
	}
	
	
	








	@Override
	public Boolean apply(WebDriver input) {
		boolean ret = false ;
		try {
			 WebElement el = controller.evalJavascriptForIdentification(this.jsLocation,ctx);
			 ret= el.isEnabled();
		} catch (TcXmlException e) {
			controller.getLog().severe(e.getMessage());
			ret = false;
			
		}
		return ret;
	}

}
