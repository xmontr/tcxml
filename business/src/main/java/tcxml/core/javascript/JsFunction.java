package tcxml.core.javascript;

import org.openqa.selenium.JavascriptExecutor;

import com.google.gson.JsonElement;

import tcxml.core.TcXmlController;

public class JsFunction extends AJsElement {
	
	
	private Object[] lastArgs ;

	public JsFunction(String name, AJsElement parent, TcXmlController controller) {
		super(name, parent,controller);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public JsFunction(String name,Object[] lastrgs, AJsElement parent, TcXmlController controller) {
		super(name, parent,controller);
		this.lastArgs = lastrgs;
	}
	
	
	
	

	@Override
	protected String getSegment() {
		if( this.lastArgs == null) {
			return name;
			
		}else {
			
		return name + convertArgs(this.lastArgs);	
		}
		
	}
	
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	@Override
	public Object call(Object thiz, Object... args) {

		Object ret = null;
		JavascriptExecutor jsexecutor = (JavascriptExecutor) controller.getDriver();
		StringBuffer script = new StringBuffer();
		String  pathToDomWindow= buildDomWindowPath() + convertArgs(args);
		StringBuffer scriptTypeOf = new StringBuffer();
		scriptTypeOf.append(" return typeof(").append(pathToDomWindow).append( ")");		
		script.append( " function getwinprop(){ return( ").append(pathToDomWindow ).append( ");} return getwinprop();")  ;
		
		// start checking the type of return
		
		String typejs = (String)jsexecutor.executeScript(scriptTypeOf.toString()  ) ; 	
		
		if (typejs.equals("function")) { //member is function
			this.setLastArgs(args);
			ret=this;
			//ret = new JsFunction(name,args, this, controller);
			
			
		}else if (typejs.equals("object")) { // member is object
			
			//ret = new JsObject(name, this,controller);
			this.setLastArgs(args);
			ret=this;
			
		}else {//  member is primitive type we can return it
				
			ret =	jsexecutor.executeScript(script.toString()  ) ;
			
			
		}
		
		
return ret;

	}

	public void setLastArgs(Object[] lastArgs) {
		this.lastArgs = lastArgs;
	}



	private String convertArgs(Object[] args) {
		String[] ret = new String[args.length] ;
		for (int i = 0; i < args.length; i++) {
			Object object = args[i];
			if( object instanceof  AJsElement) {
				
				AJsElement jel = 	(AJsElement) object ;
			ret[i] =  jel.buildDomWindowPath() ;
				
			}else if(  object instanceof  String) {
				ret[i]= "\"" + object.toString() + "\"" ;
				
			} else if(  object != null) {
				
				ret[i] = object.toString();	
				
			}			else {
				ret[i]="null";	
			}
			
			
			
			
		}
		StringBuffer temp = new StringBuffer();
		temp.append("(").append( String.join(",", ret)).append(")");
		return temp.toString();
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



