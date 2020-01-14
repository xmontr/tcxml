package tcxml.core;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class MirrorFunction  extends AbstractJSObject{
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true; 
	}
	
	
	
	@Override
	public Object call(Object thiz, Object... args) {

WebElementWrapper ww = (WebElementWrapper)thiz ;
Object ret = null;
try {
	ret = ww.callFunctionWithArgument(args);
} catch (TcXmlException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		return ret;
	}
	
	
	

}
