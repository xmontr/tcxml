package tcxml.core;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;

import model.CallFunctionAttribut;

public class PlayingContext {
	
	
	
	private TcXmlController controller ;
	
	private Stack<ExecutionContext>  stack;
	
	
	private ScriptContext jsContext;
	
	
	public ScriptContext getJsContext() {
		return jsContext;
	}
	
	
	
	
	
	
	
	public PlayingContext(TcXmlController controller ) throws TcXmlException {
		stack = new Stack<ExecutionContext>();
		this.controller = controller;
		jsContext = controller.buildInitialJavascriptContext(this);
		
		
	}
	
	

public void  pushContext(ExecutionContext ec) throws TcXmlException {
	ec.setParent(this);
	controller.addFuncArg2context(this,ec);
	stack.push(ec);
}
	
	
public void  popContext() throws TcXmlException {
	
	ExecutionContext toremove = stack.pop();
	controller.removeArgsFromJsContext(this,toremove);
}




public ExecutionContext getCurrentExecutionContext() {
	if(stack.isEmpty()) {
		
		return null;
	}else {
		
		return stack.lastElement();
	}
	
	
	
}


public void dumpJsContext() {
	Logger log = controller.getLog();
	log.info(" browsing global variable for JS context");
	Bindings nashorn_global = (Bindings) jsContext.getAttribute("nashorn.global");

	
	Set<Entry<String, Object>> globalval = nashorn_global.entrySet();
	for (Entry<String, Object> entry : globalval) {
		log.info("   found global var " + entry.getKey());
	}
	log.info(" browsing local variable for JS context");
	Set<Entry<String, Object>> localval = jsContext.getBindings(ScriptContext.ENGINE_SCOPE).entrySet();
	for (Entry<String, Object> entry : localval) {
		
		
		log.info(" found local var " + entry.getKey());
		
		
		
	}
	
	
	
	
	
	
}


}
