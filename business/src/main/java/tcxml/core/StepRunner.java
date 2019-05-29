package tcxml.core;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;

import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public abstract  class StepRunner {
	
	
	protected Step step;
	
	protected TruLibrary library;
	
	protected TcXmlController tcXmlController ;
	protected JsonObject arg;
	
	protected HashMap<String, ArgModel> argumentMap ;
	
	
	

	protected Logger log;
	
protected StepStat stat;
	
	
	
	
	
	

	
	public StepRunner(Step step,TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super();
		log = Logger.getLogger(StepRunner.class.getName());
		log.setLevel(Level.ALL);
		this.step = step;
		this.tcXmlController = tcXmlController;
		this.library =lib;
		arg = tcXmlController.readJsonObject(step.getArguments());
		argumentMap = tcXmlController.getArguments(step);
		stat = new StepStat();
	}




	public abstract PlayingContext runStep (PlayingContext ctx) throws TcXmlException ;

}
