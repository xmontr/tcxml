package tcxmlplugin;

import java.util.Set;

import javax.script.ScriptContext;

import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxmlplugin.job.MultipleStepViewerRunner;


public class JsMultipleStepRunner   extends AbstractJSObject {
	
	private MultipleStepViewerRunner mc ;
	private PlayingContext context ;
	public JsMultipleStepRunner(MultipleStepViewerRunner mc, PlayingContext ctx) {
		super();
		this.mc = mc;
		this.context = ctx;
	}
	
	
	@Override
	public boolean isFunction() {
		// TODO Auto-generated method stub
		return true; 
	}
	
	
	
	
	@Override
	public Object call(Object thiz, Object... args) {
		// Tupdate the context with variable of the loop
		
		try {
			
			//copy all global variable into the context
			ScriptObjectMirror glo = (ScriptObjectMirror)	thiz;
			Set<String> allvar = glo.keySet();
			for (String key : allvar) {				
				context.getJsContext().setAttribute(key,glo.get(key), ScriptContext.GLOBAL_SCOPE);
				
			}
			
			this.context = mc.runSteps(this.context) ;
		} catch (TcXmlException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e) ;
		}
		
		return this.context ;
	}
	
	
	

}
