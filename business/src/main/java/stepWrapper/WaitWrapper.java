package stepWrapper;

import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class WaitWrapper extends AbstractStepWrapper {

	public WaitWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), "Wait "+ argumentMap.get("Interval").getValue() + " seconds" ) ;
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>() ;
		ArgModel mo;
		mo = new ArgModel("Interval");
mo.setValue("3");
ret.add(mo);


mo = new ArgModel("Unit");
mo.setValue("Seconds");
ret.add(mo);

return ret;
	}
	

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
	ArgModel interval = argumentMap.get("Interval");
    ArgModel unit = argumentMap.get("Unit");
	
	long l =0L;
	String val = controller.evaluateJsArgument(interval, ctx.getCurrentExecutionContext());
	
	try {
	 l = Long.parseLong(val);
	
	}catch (NumberFormatException e) {
		throw new TcXmlException("unable to read argument as a long", new IllegalArgumentException(val));	
		
	}
	
	if(unit.getValue().equals("Seconds")) {
		l = l*1000 ;
		
	}
	
	controller.getLog().info("waiting " + l + unit.getValue());
	try {
		Thread.sleep(l );
	} catch (InterruptedException e) {
		throw new TcXmlException("interrupted", e);
	}	
		return ctx;
		
	}
	
	

}
