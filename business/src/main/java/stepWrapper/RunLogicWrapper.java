package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class RunLogicWrapper extends AbstractStepWrapper {

	public RunLogicWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		// TODO Auto-generated method stub
		return "run Logic";
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		RunBlockWrapper initRunBlock = getInitRunblock();
		RunBlockWrapper actionRunBlock = getActionRunblock();
		RunBlockWrapper endRunBlock = getEndRunblock();
		
		ctx = initRunBlock.runStep(ctx);
		
		ctx= actionRunBlock.runStep(ctx);
		
		ctx = endRunBlock.runStep(ctx);
		
		
		return ctx;
	}

	private RunBlockWrapper getEndRunblock() throws TcXmlException {
		Step thestep = step.getStep().get(2);
		RunBlockWrapper thewrapper = (RunBlockWrapper) StepWrapperFactory.getWrapper(thestep, controller, library);
		return thewrapper;
	}

	private RunBlockWrapper getActionRunblock() throws TcXmlException {
		Step thestep = step.getStep().get(1);
		RunBlockWrapper thewrapper = (RunBlockWrapper) StepWrapperFactory.getWrapper(thestep, controller, library);
		return thewrapper;
	}

	private RunBlockWrapper getInitRunblock() throws TcXmlException {
		Step thestep = step.getStep().get(0);
		RunBlockWrapper thewrapper = (RunBlockWrapper) StepWrapperFactory.getWrapper(thestep, controller, library);
		return thewrapper;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		// TODO Auto-generated method stub
		
	}

}
