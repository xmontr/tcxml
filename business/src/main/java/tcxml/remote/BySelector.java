package tcxml.remote;

import javax.json.JsonObject;

import org.openqa.selenium.By;

public class BySelector {
	
	
	 public By pickFromJsonParameters(JsonObject command) {
		    String method =  command.getString("using");
		    String selector =  command.getString("value");

		    return pickFrom(method, selector);
		  }

		  public By pickFrom(String method, String selector) {
		    if ("class name".equals(method)) {
		      return By.className(selector);
		    } else if ("css selector".equals(method)) {
		      return By.cssSelector(selector);
		    } else if ("id".equals(method)) {
		      return By.id(selector);
		    } else if ("link text".equals(method)) {
		      return By.linkText(selector);
		    } else if ("partial link text".equals(method)) {
		      return By.partialLinkText(selector);
		    } else if ("name".equals(method)) {
		      return By.name(selector);
		    } else if ("tag name".equals(method)) {
		      return By.tagName(selector);
		    } else if ("xpath".equals(method)) {
		      return By.xpath(selector);
		    } else {
		      throw new IllegalArgumentException("Cannot find matching element locator to: " + method);
		    }
		  }

}
