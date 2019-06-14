package tcxml.core.runner;

import javax.json.JsonObject;

import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxml.model.WaitModel;

public class WaitRunner extends StepRunner{

	public WaitRunner(Step step,  TcXmlController tcXmlController) throws TcXmlException {
		super(step,null, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
	ArgModel interval = argumentMap.get("Interval");
    ArgModel unit = argumentMap.get("Unit");
	
	long l =0L;
	String val = tcXmlController.evaluateJsArgument(interval, ctx.getCurrentExecutionContext());
	
	try {
	 l = Long.parseLong(val);
	
	}catch (NumberFormatException e) {
		throw new TcXmlException("unable to read argument as a long", new IllegalArgumentException(val));	
		
	}
	
	if(unit.getValue().equals("Seconds")) {
		l = l*1000 ;
		
	}
	
	log.info("waiting " + l + unit.getValue());
	try {
		Thread.sleep(l );
	} catch (InterruptedException e) {
		throw new TcXmlException("interrupted", e);
	}	
		return ctx;
		
	}

}
