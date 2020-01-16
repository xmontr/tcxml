package tcxml.core;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class MirrorFunction  extends AbstractJSObject{
	
	
	private TcXmlController controller;



	public MirrorFunction(TcXmlController controller) {
		this.controller=controller;
	}



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
	controller.getLog().severe(e.getMessage());
}
		
		return ret;
	}
	
	
	

}
