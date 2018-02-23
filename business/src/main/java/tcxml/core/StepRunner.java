package tcxml.core;

import javax.json.JsonObject;

import tcxml.model.Step;

public abstract  class StepRunner {
	
	
	protected Step step;
	
	protected TcXmlController tcXmlController ;
	protected JsonObject arg;
	
	

	
	public StepRunner(Step step, TcXmlController tcXmlController) throws TcXmlException {
		super();
		this.step = step;
		this.tcXmlController = tcXmlController;
		arg = tcXmlController.readJsonObject(step.getArguments());
	}




	public abstract void runStep () throws TcXmlException ;

}
