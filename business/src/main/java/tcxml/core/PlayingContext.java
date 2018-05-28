package tcxml.core;

import java.util.List;
import java.util.Stack;

import model.CallFunctionAttribut;

public class PlayingContext {
	
	
	
	
	
	private Stack<ExecutionContext>  stack;
	
	
	
	public PlayingContext() {
		stack = new Stack<ExecutionContext>();
		
	}
	
	

public void  pushContext(ExecutionContext ec) {
	
	stack.push(ec);
}
	
	
public void  popContext() {
	
	stack.pop();
}




public ExecutionContext getCurrentExecutionContext() {
	if(stack.isEmpty()) {
		
		return null;
	}else {
		
		return stack.lastElement();
	}
	
	
	
}





}
