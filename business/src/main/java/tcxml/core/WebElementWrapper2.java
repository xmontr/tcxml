package tcxml.core;

import java.util.Collection;
import java.util.Set;
import java.util.Stack;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class WebElementWrapper2  extends AbstractJSObject {
	
	private WebElement theElement;
	private String name;
	
	private WebElementWrapper2 parent ; 
	
	private boolean isFunct;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFunct() {
		return isFunct;
	}

	public void setFunct(boolean isFunct) {
		this.isFunct = isFunct;
	}

	private TcXmlController controller;
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param theElement if null then window is wrapped
	 * @param controller
	 */
	
	public WebElementWrapper2(WebElement targetElement, TcXmlController controller) {
		super();
		this.theElement = targetElement;
		this.controller=controller;
		if(targetElement == null) {
			this.name = "window";
			this.parent=null;
		}

	}
	
	private String buildDomWindowPath() {
		WebElementWrapper2 current = this;
		Stack<String> st = new Stack<String>();
		st.push(current.name);
while(current.parent != null){
	current = current.parent;
	st.push(current.name);
	
}
	StringBuffer ret = new StringBuffer();
	String first = st.pop(); // skip window objet
	if( ! st.isEmpty()) { // add the first
		ret.append(st.pop());	
		
	}
		
	while(! st.isEmpty()) { // add the other
				ret.append(".").append(st.pop());
		
	}
	return ret.toString();	
	}
	
	
	@Override
	public Object call(Object thiz, Object... args) {
		controller.getLog().info("call called on  " + thiz);
		for (Object object : args) {
			controller.getLog().info("arg= " + object);
		}
		WebElementWrapper2 el = (WebElementWrapper2) thiz ;
		
		
		Object retjs = null;
		WebElementWrapper2 ret = null;
	JavascriptExecutor jsexecutor = (JavascriptExecutor) controller.getDriver();	
	StringBuffer script= new StringBuffer();
	
	if( parent  == null) {// call made on window member
		script.append("return ").append(name).append(".apply(arguments[1])" );
		
		retjs = jsexecutor.executeScript(script.toString(),args ) ;	
	}else { // call made on a htmlelement
		
	
		
		
		
		script.append("return ").append("arguments[0].").append(name).append(".apply(arguments[0],arguments[1])" );
		Object[] convertedArgs = convertArgs(args);
		// debug
		String debugscript =" console.log(\"arg0:\"); console.log(arguments[0]);console.log(\"arg1:\"); console.log(arguments[1]);";
		jsexecutor.executeScript(debugscript.toString() ,parent.getTheElement(),convertedArgs  ) ;
		
		String thejs = script.toString();
		retjs = jsexecutor.executeScript(thejs ,parent.getTheElement(),convertedArgs  ) ;	
	}
	if(retjs instanceof WebElement) {
		ret = new WebElementWrapper2((WebElement)retjs, controller);
		
	}
	return ret;
		
		
	}
	
	
	private Object[] convertArgs(Object[] args) {
		Object[] ret = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if(args[i] instanceof  WebElementWrapper2 ) {
			ret[i]	= ((WebElementWrapper2) args[i]).getTheElement();
			}else {
				
			ret[i]=args[i];	
			}
			
		}
		
		
		return ret;
	}

	public WebElement getTheElement() {
		return theElement;
	}

	public WebElementWrapper2 getParent() {
		return parent;
	}

	public void setParent(WebElementWrapper2 parent) {
		this.parent = parent;
	}

	@Override
	public Object getMember(String name) {
		Object retjs = null;
		WebElementWrapper2 ret =null;
		controller.getLog().info("getMember called for name " + name + " on webelment " + theElement);
		JavascriptExecutor jsexecutor = (JavascriptExecutor) controller.getDriver();	
		
		StringBuffer script= new StringBuffer();
		
		if( theElement  == null) { // called on widow object 
			String pathToDomWindow = buildDomWindowPath();
			if(pathToDomWindow.isEmpty()) {
				pathToDomWindow = name;	
			}else {
				pathToDomWindow = pathToDomWindow + "." + name; 
			}
			script.append( " function getwinprop(){ return( ").append(pathToDomWindow ).append( ");} return getwinprop();")  ;			
			retjs = jsexecutor.executeScript(script.toString()) ;
			controller.getLog().info("js to evaluate is: " + script.toString() );
			if(! (retjs instanceof WebElement)) { // return a function or object that we need to mirror
				
				String typejs = (String) jsexecutor.executeScript(" return typeof(" + name + ")") ;
				if (typejs.equals("function")) {
				ret	=new WebElementWrapper2(this.getTheElement() , controller);
				ret.setFunct(true);	
				ret.setParent(this);
				ret.setName(name);
				}
				
		if (typejs.equals("object")) { // directly readable by rhino, return it
			
			ret	=new WebElementWrapper2(this.getTheElement() , controller);
						ret.setParent(this);
			ret.setName(name);
					
					
				}else {
				
					return retjs;
					
				}
				
			}else { //the memeber is a bebelement
				
				ret	=new WebElementWrapper2((WebElement) retjs , controller);
				ret.setParent(this);
				ret.setName(name);
			}
			
			
			
			
			
		}else { // called on other webelement
			script.append("return arguments[0]." ).append(name);
			retjs = jsexecutor.executeScript(script.toString(),theElement ) ;	
			controller.getLog().info("js to evaluate is:" + script.toString());
			if(! (retjs instanceof WebElement)) { // return a function or object that we need to mirror
				
				String typejs = (String) jsexecutor.executeScript(" return typeof(arguments[0]." + name + ")"  ,theElement) ;
				if (typejs.equals("function")) {
				ret	=new WebElementWrapper2(this.getTheElement() , controller);
				ret.setFunct(true);	
				ret.setName(name);
				ret.setParent(this);
				
				}
				
		if (typejs.equals("object")) { // directly readable by rhino, return it
			
			return retjs;
					
					
				}
				
			}else{ //the memeber is a bebelement
				ret	=new WebElementWrapper2((WebElement) retjs , controller);
				ret.setParent(this);
				ret.setName(name);
				
			}
			
			
			
			
			
			
			
	
			
		}
	
	
		
		

		controller.getLog().info("wrapped returned element  is  " + ret);
		return ret;
	
	

		
		
	}
	@Override
	public boolean isArray() {
		controller.getLog().info("isarray called for name  on webelment " + theElement);
		return super.isArray();
	}
	
	@Override
	public boolean hasMember(String name) {
		controller.getLog().info("hasMember called for name " +  name + "  on webelment " + theElement);
		return super.hasMember(name);
	}
	
	
	@Override
	public boolean isFunction() {
		controller.getLog().info("isFunction called    on webelment " + theElement);
		return isFunct;
	}
	
	
	@Override
	public Object getSlot(int index) {
		controller.getLog().info("getSlot called for index " + index + "   on webelment " + theElement);
		return super.getSlot(index);
	}
	
	@Override
	public boolean hasSlot(int slot) {
		controller.getLog().info("v called for index " + slot + "   on webelment " + theElement);
		return super.hasSlot(slot);
	}
	
	
	@Override
	public void removeMember(String name) {
		controller.getLog().info("v removeMember for name " + name + "   on webelment " + theElement);
		super.removeMember(name);
	}
	
	
	@Override
	public void setMember(String name, Object value) {
		controller.getLog().info("v setMember for name " + name + "   on webelment " + theElement);
		super.setMember(name, value);
	}
	
	@Override
	public Set<String> keySet() {
		controller.getLog().info("keySet called    on webelment " + theElement);
		return super.keySet();
	}
	
	@Override
	public Collection<Object> values() {
		controller.getLog().info("values called    on webelment " + theElement);
		return super.values();
	}
	

}
