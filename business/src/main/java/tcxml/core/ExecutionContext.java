package tcxml.core;

import java.util.List;

import javax.script.ScriptContext;

import model.CallFunctionAttribut;

public class ExecutionContext {
	
	
	private List<CallFunctionAttribut> arrgumentsList;
	
	
	private PlayingContext parent;
	

	

	
	
	public PlayingContext getParent() {
		return parent;
	}

	public void setParent(PlayingContext parent) {
		this.parent = parent;
	}

	public List<CallFunctionAttribut> getArrgumentsList() {
		return arrgumentsList;
	}


	private ExecutionContext() {
		
		
	}

	public ExecutionContext(List<CallFunctionAttribut> arrgumentsList) {
		super();
		this.arrgumentsList = arrgumentsList;
	}


	
	
	

	
	


	
	
	
	

}
