package tcxml.core;

import java.util.List;

import model.CallFunctionAttribut;

public class PlayingContext {
	
	
	private List<CallFunctionAttribut> arrgumentsList;
	
	
	private PlayingContext parentContext;
	
	
	
	public PlayingContext(PlayingContext parent) {
		this.parentContext = parent;
		
	}
	
	
	public List<CallFunctionAttribut> getArrgumentsList() {
		return arrgumentsList;
	}

	public void setArrgumentsList(List<CallFunctionAttribut> arrgumentsList) {
		this.arrgumentsList = arrgumentsList;
	}

	
	
	

}
