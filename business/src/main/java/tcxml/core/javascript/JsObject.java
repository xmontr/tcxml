package tcxml.core.javascript;

import org.openqa.selenium.JavascriptExecutor;

import tcxml.core.TcXmlController;

public class JsObject extends AJsElement {

	public JsObject(String name, AJsElement parent, TcXmlController controller) {
		super(name, parent,controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSegment() {
		StringBuffer ret = new StringBuffer();
		ret.append(name);
		return ret.toString();
	}
	
	
	
	@Override
	public Object getMember(String name) {
		Object ret = null;
		JavascriptExecutor jsexecutor = (JavascriptExecutor) controller.getDriver();
		StringBuffer script = new StringBuffer();
		String  pathToDomWindow= buildDomWindowPath();
		if( pathToDomWindow.isEmpty()) {
			pathToDomWindow = name;
		}else {
			pathToDomWindow +="." +  name;
		}
		StringBuffer scriptTypeOf = new StringBuffer();
		scriptTypeOf.append(" return typeof(").append(pathToDomWindow).append( ")");		
		script.append( " function getwinprop(){ return( ").append(pathToDomWindow ).append( ");} return getwinprop();")  ;
		
		// start checking the type of return
		
		String typejs = (String)jsexecutor.executeScript(scriptTypeOf.toString()  ) ; 	
		
		if (typejs.equals("function")) { //member is function
			
			ret = new JsFunction(name, this, controller);
			
			
		}else if (typejs.equals("object")) { // member is object
			
			ret = new JsObject(name, this,controller);
			
		}else {//  member is primitive type we can return it
				
			ret =	jsexecutor.executeScript(script.toString()  ) ;
			
			
		}
		
		
return ret;
	}
	
	
	
	
	
	
	

}
