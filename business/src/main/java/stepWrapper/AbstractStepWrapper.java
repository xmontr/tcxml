package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import tcxml.core.Playable;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public abstract class AbstractStepWrapper implements Playable{
	
	
	public Step getStep() {
		return step;
	}

	public TruLibrary getLibrary() {
		return library;
	}

	public TcXmlController getController() {
		return controller;
	}


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
	
	public HashMap<String, ArgModel> getArgumentMap() {
		return argumentMap;
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		if(isDisabled()) { // don't play a dsabled step
			
	controller.getLog().info("step " + getTitle() + " is disabled. don't play it.");		
			return ctx ;
		}
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
	
	
	public String getLevel() {
		
		String level = step.getLevel();
		if(level == null || level.isEmpty()) {
			level ="1";
			
		}	
		return level;
		
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
	 
	 
	 public abstract void export(PrintWriter pw) throws TcXmlException ;
	 
	 
		protected String genericExport(String targetFuncName) {
			
			
			 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
				String argjs = controller.generateJSobject(li);	
				
				
			
				String ret = TcxmlUtils.formatJavascriptFunction(
						targetFuncName,
							argjs  
						
							);
				return ret;
			}

		
		protected String genericExport(String targetFuncName , TestObject to) throws TcXmlException {
			 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
				String argjs = controller.generateJSobject(li);	
				
				
			
				String ret = TcxmlUtils.formatJavascriptFunction(
						targetFuncName,
							argjs,
							controller.generateJsTestObject(to)
						
							);
				
				
				return ret;
		}
		
	

}




