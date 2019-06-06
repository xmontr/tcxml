package tcxml.core;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;

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
			dr.manage().deleteCookie(cookie);
			controller.getLog().info(" delete " +cn);
			
		}
		
		controller.getLog().info(" all cookies deleted");
		
		
	}
	
	
	
	public void clearCache() {
		
		WebDriver dr = controller.getDriver();
		if(dr  instanceof WebStorage) {
			WebStorage ws = (WebStorage)dr;
			ws.getLocalStorage().clear();
			controller.getLog().info(" clear cache : clear local storage");
			ws.getSessionStorage().clear();
			controller.getLog().info(" clear cache : clear local storage");
			
			
			
		}
		

		
		
		
	}
	
	

}
