package tcxml.core.runner;

import javax.json.JsonObject;

import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class WaitRunner extends StepRunner{

	public WaitRunner(Step step,  TcXmlController tcXmlController) throws TcXmlException {
		super(step,null, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		String json = step.getArguments();
		String rootKey="Interval";
		JsonObject arg = tcXmlController.readJsonObject(json);
	String duration = arg.getString("value","3");
		
	try {
		Thread.currentThread().sleep(3000);
	} catch (InterruptedException e) {
		throw new TcXmlException("interrupted", e);
	}	
		return ctx;
		
	}

}
