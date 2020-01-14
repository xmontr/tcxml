package tcxml.core;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;

import jdk.nashorn.api.scripting.AbstractJSObject ;





public class WebElementWrapper extends AbstractJSObject {
	
	
	private WebElement theElement;
	private String functioname;

	public WebElementWrapper(WebElement theElement) {
		super();
		this.theElement = theElement;
	}
	
	
	
	
	@Override
	public Object getMember(String name) {
	
		Object ret = theElement.getAttribute(name);
		
		if( ret == null) {// no attribut maybe a function is called
			ret = new MirrorFunction();
			saveFunctionName(name);
			
			
		}
		
		
		return ret;
		
		
	}
	
	
	private void saveFunctionName(String name) {
		functioname = name;
		
	}




	@Override
	public Object call(Object thiz, Object... args) {
		// TODO Auto-generated method stub
		return super.call(thiz, args);
	}
	
	@Override
	public boolean isArray() {
		// TODO Auto-generated method stub
		return false;
	}




	public Object callFunctionWithArgument(Object[] args) throws TcXmlException  {
		// call functionname on theelement with argument args
		
		Class cls = theElement.getClass();	
		
		ArrayList<Class> javatypes = new ArrayList<Class>() ;
		for (Object ob : args) {			
			javatypes.add(ob.getClass());			
		}
		

	Method method;Object ret;
	try {
		method = cls.getDeclaredMethod(functioname, javatypes.toArray(new Class[javatypes.size()]));
		 ret = method.invoke(theElement, args) ;
	} catch ( Exception e) {
throw new TcXmlException("fail to execute method " + functioname + " on this.object ", e);
	}

	
		return ret;
	}
	
	
	
	
	
	


}
