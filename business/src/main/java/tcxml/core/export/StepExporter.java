package tcxml.core.export;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;

import tcxml.core.StepRunner;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public abstract class StepExporter {
	
	
	protected Step step;
	
	protected TruLibrary library;
	
	protected TcXmlController tcXmlController ;
	protected JsonObject arg;
	
	protected HashMap<String, ArgModel> argumentMap ;
	
	
	

	protected Logger log;
	
	
	
	public StepExporter(Step step,TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super();
		log = Logger.getLogger(StepRunner.class.getName());
		log.setLevel(Level.ALL);
		this.step = step;
		this.tcXmlController = tcXmlController;
		this.library =lib;
		arg = tcXmlController.readJsonObject(step.getArguments());
		argumentMap = tcXmlController.getArguments(step);
	
	}
	
	
	public abstract String export()  throws TcXmlException ;
	

}
