package stepWrapper;

import java.util.ArrayList;
import java.util.HashMap;

import tcxml.core.Playable;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public abstract class AbstractStepWrapper implements Playable{
	
	
	protected Step step ;
	protected TcXmlController controller;
	protected TruLibrary library ;
	protected HashMap<String, ArgModel> argumentMap;
	public AbstractStepWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super();
		this.step = step;
		this.controller = controller;
		this.library = library;
		argumentMap = controller.getArguments(step , getDefaultArguments());
	}
	
	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		controller.manageStartStopTransaction(this);
		PlayingContext ret = runStep(ctx);
		controller.manageStartStopTransaction(this);
		return ret;
	}
	
	public abstract  String getTitle() throws TcXmlException ;
	
	
	protected abstract PlayingContext runStep (PlayingContext ctx) throws TcXmlException ;
	
	
	public Step getModel() {
		return step;
		
	}
	
	public boolean isDisabled() {
		if(step.isDisabled() != null && step.isDisabled()) {
			
			return true;
		} else {
			
			return false;
		}
		
		
	}
	
	
	
	 protected String formatTitle (String index , String txt) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" ").append(index).append(" ").append(txt);
		
		//add tag ifstep disabled
		
		if( isDisabled()) {
			
			sb.append(" - disabled");
		}
		
		return sb.toString();
		
	}
	 
	 
	 public abstract  ArrayList<ArgModel>  getDefaultArguments() throws TcXmlException ;
	 
	

}
