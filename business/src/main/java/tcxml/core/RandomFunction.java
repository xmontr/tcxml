package tcxml.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class RandomFunction extends AbstractJSObject {
	
	
	private TcXmlController controller;
	
	

	public RandomFunction( TcXmlController tc) {
		this.controller = tc;
		
		
	}
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true; 
	}
	
	
	@Override
	public Object call(Object thiz, Object... args) {
		List<WebElement> thelist = 	(List<WebElement>) args[0];


Random r = new Random();
int choice = r.nextInt(thelist.size());
ArrayList<WebElement> ret = new ArrayList<WebElement>();
ret.add(thelist.get(choice));

	return ret ;
	}
	
	
	

}
