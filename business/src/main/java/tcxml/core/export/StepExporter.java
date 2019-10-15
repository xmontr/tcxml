package tcxml.core.export;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.core.StepRunner;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public abstract class StepExporter {
	
	
	protected Step step;
	
	protected TruLibrary library;
	
	protected TcXmlController tcXmlController ;
	protected JsonObject arg;
	
	protected HashMap<String, ArgModel> argumentMap ;
	
	
	

	protected Logger log;

	protected AbstractStepWrapper stepWrapper;
	
	
	
	public StepExporter(Step step,TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super();
		log = Logger.getLogger(StepExporter.class.getName());
		log.setLevel(Level.ALL);
		this.step = step;
		this.stepWrapper = StepWrapperFactory.getWrapper(step, tcXmlController, lib);
		this.tcXmlController = tcXmlController;
		this.library =lib;
		arg = tcXmlController.readJsonObject(step.getArguments());
		argumentMap = tcXmlController.getArguments(step, stepWrapper.getDefaultArguments());
	
	}
	
	
	public abstract String export()  throws TcXmlException ;
	
	
	
	
	protected String genericExport(String targetFuncName) {
	
		
	 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
		String argjs = tcXmlController.generateJSobject(li);	
		
		
	
		String ret = TcxmlUtils.formatJavascriptFunction(
				targetFuncName,
					argjs  
				
					);
		return ret;
	}
	
	
	protected String genericExport(String targetFuncName , TestObject to) throws TcXmlException {
		 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = tcXmlController.generateJSobject(li);	
			
			
		
			String ret = TcxmlUtils.formatJavascriptFunction(
					targetFuncName,
						argjs,
						tcXmlController.generateJsTestObject(to)
					
						);
			
			
			return ret;
	}
	
	

}
