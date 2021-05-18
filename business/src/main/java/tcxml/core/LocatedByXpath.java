package tcxml.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import tcxml.model.TestObject;

public class LocatedByXpath implements ExpectedCondition<Boolean>{
	
	private TcXmlController controller;
	private ExecutionContext ctx;
	private TestObject to;
	
	

	public LocatedByXpath(TcXmlController controller, ExecutionContext ctx, TestObject to) {
		super();
		this.controller = controller;
		this.ctx = ctx;
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
