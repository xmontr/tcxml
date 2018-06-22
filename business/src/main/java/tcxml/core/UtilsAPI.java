package tcxml.core;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class UtilsAPI {
	
	TcXmlController controller;
	
	
	
	public UtilsAPI(TcXmlController tcXmlController) {
		
		
		controller = tcXmlController ;		
	
		
	}
	
	
	public void clearCookies( ) {
		
		WebDriver dr = controller.getDriver();
		
		Set<Cookie> cookies = dr.manage().getCookies();
		for (Cookie cookie : cookies) {
			String cn = cookie.getName();
			controller.getLog().info(" found cookie " +cn);
			
		}
		dr.manage().deleteAllCookies();
		controller.getLog().info(" all cookies deleted");
		
		
	}
	
	
	
	public void clearCache() {
		
		controller.getLog().info(" clear cache is not implemented now !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		
		
	}
	
	

}
