package tcxml.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;

import tcxml.model.CallFunctionAttribut;

public class PlayingContext {
	
	
	
	private TcXmlController controller ;
	
	public TcXmlController getController() {
		return controller;
	}




	private Stack<ExecutionContext>  stack;
	
	

	
	
	public ScriptContext getJsContext() {
		return getCurrentExecutionContext().getJsContext();
	}
	
	
	
	
	
	
	
	public PlayingContext(TcXmlController controller ) throws TcXmlException {
		stack = new Stack<ExecutionContext>();
		this.controller = controller;
		ScriptContext jsContext = controller.buildInitialJavascriptContext();
		List<CallFunctionAttribut> arrgumentsList = new ArrayList<CallFunctionAttribut>();
		ExecutionContext initexec = new ExecutionContext("initial execution context", arrgumentsList , jsContext);
		pushContext(initexec);
		
		
	}
	
	

public void  pushContext(ExecutionContext ec) throws TcXmlException {
	controller.getLog().info(" adding executioncontext " + ec.getName());
	ec.setParent(this);	
	stack.push(ec);
	
}
	
	
public void  popContext() throws TcXmlException {
	
	ExecutionContext toremove = stack.pop();
	controller.getLog().info(" removing executioncontext " + toremove.getName());
	
}




public ExecutionContext getCurrentExecutionContext() {
	if(stack.isEmpty()) {
		
		return null;
	}else {
		
		return stack.lastElement();
	}
	
	
	
}









}
