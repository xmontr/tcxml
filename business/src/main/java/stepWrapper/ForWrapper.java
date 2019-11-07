package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.MultipleStepWrapperRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;



public class ForWrapper extends AbstractStepWrapper {

	public ForWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret = formatTitle(step.getIndex(), " " +  buildLoopString() );
		return ret;
	}
	
	
	
	public String buildLoopString() {
	return "for(" + argumentMap.get("Init").getValue() +";" + argumentMap.get("Condition").getValue() + ";" + argumentMap.get("Increment").getValue() + ")" ;	
		
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>() ;
		ArgModel init = new ArgModel("Init"); init.setValue("");init.setIsJavascript(true);
		ArgModel condition = new ArgModel("Condition"); condition.setValue("");condition.setIsJavascript(true);
		ArgModel increment = new ArgModel("Increment"); increment.setValue("");increment.setIsJavascript(true);
		
		ret.add(increment); ret.add(condition); ret.add(init);
		
		return ret;
	}

	
	
	public BoundList<Step> getSteps() throws TcXmlException {
		
		BoundList<Step> li = step.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
				sanitycheck(firstchild);
		li=firstchild.getStep();
		return li ;
		
	}
	

	private List<AbstractStepWrapper> getChildren() throws TcXmlException {
		
		ArrayList<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
				sanitycheck(firstchild);
		li=firstchild.getStep();
		
		
		
		
		for (Step thestep : li) {
			
		AbstractStepWrapper newwrapper = StepWrapperFactory.getWrapper(thestep, controller, library) ;
			ret.add(newwrapper);
		}
		return ret;
	}
	
	
	private String buildPlayingCommand() {
		StringBuffer sb = new StringBuffer();
		sb.append(buildLoopString()).append("{ctx = mc.runSteps(ctx);};");
		
		return sb.toString();
		
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		 MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(getChildren(), controller);
			ScriptEngine engine = controller.getJSengine();
			   ScriptContext context = ctx.getJsContext();
				// store the global variables 
			   context.setAttribute("mc", mc, ScriptContext.GLOBAL_SCOPE);
			   context.setAttribute("ctx", ctx, ScriptContext.GLOBAL_SCOPE);
			  try {
				engine.eval(buildPlayingCommand(),context);
			} catch (ScriptException e) {
			
				throw new TcXmlException(" failure in For loop", e);
			} 
			   
			   
			   
			
				return ctx;
	}
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}
	
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer();
		pw.println(" // " + getTitle());
		
	pw.println(buildLoopString()  + " { ");
	
	 List<AbstractStepWrapper> list = getChildren();
	for (AbstractStepWrapper abstractStepWrapper : list) {
		abstractStepWrapper.export(pw);
	}		
	 sb = new StringBuffer("}//fin for ");	
	pw.println(sb);	
	
	
		
	}

}
