package tcxml.core;

import java.util.List;

import javax.script.ScriptContext;

import tcxml.model.CallFunctionAttribut;

public class ExecutionContext {
	
	
	private List<CallFunctionAttribut> arrgumentsList;
	
	
	private PlayingContext parent;
	
	private String name ;
	

	

	
	
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

	public ExecutionContext(String name , List<CallFunctionAttribut> arrgumentsList) {
		super();
		this.name = name;
		this.arrgumentsList = arrgumentsList;
	}

	public String getName() {
		return name;
	}


	
	
	

	
	


	
	
	
	

}
