package tcxml.core;


import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject ;





public class WebElementWrapper extends AbstractJSObject {
	
	
	private WebElement theElement;

	public WebElementWrapper(WebElement theElement) {
		super();
		this.theElement = theElement;
	}
	
	
	
	
	@Override
	public Object getMember(String name) {
	
		
		return theElement.getAttribute(name);
		
		
	}
	
	@Override
	public boolean isArray() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	


}
