package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.MultipleStepWrapperRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

import util.TcxmlUtils;


public class IfWrapper extends AbstractStepWrapper {

	public IfWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), " " +  buildIfString() );
		return ret;
	}
	
	
	private String buildIfString() {
		// TODO Auto-generated method stub
		return "if( ("  + argumentMap.get("Condition").getValue()  + ")  ) {" ;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();
		ArgModel mo = new ArgModel("Condition");
mo.setValue("");
mo.setIsJavascript(true);
ret.add(mo);
return ret;
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		ArgModel cond = argumentMap.get("Condition");
		
	String val = controller.evaluateJsArgument(cond, ctx.getCurrentExecutionContext());
	
	boolean ok = Boolean.parseBoolean(val);
	
	controller.getLog().info("if clause condition :" + cond.getValue());
	controller.getLog().info("if value:" + ok);
	if(ok) {
		
	runChildSteps(ctx);	
		
	} else {
		controller.getLog().info("condition not matched, skip inside step and run else step if exists" );
		
		runelseSteps(ctx);
		
	}
		
		
		return ctx;
	}
	

	private void runChildSteps(PlayingContext ctx) throws TcXmlException {
		
		
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(getIfChildren(),controller);
		mc.runSteps(ctx);
		
	}
	

	private List<AbstractStepWrapper> getIfChildren() throws TcXmlException {
		List<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		Step firstchild = li.get(0);				
		sanitycheck(firstchild);


for (Step thestep : firstchild.getStep()) {
	AbstractStepWrapper newWrapper = StepWrapperFactory.getWrapper(thestep, controller, library);
	ret.add(newWrapper);
}
		
		
		
		return ret;
	}
	
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}
	
	private void runelseSteps(PlayingContext ctx) throws TcXmlException {
		
	List<AbstractStepWrapper> elsesteps = getElseChildren();
	
	if(elsesteps.size() >0 ) {
		
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(elsesteps, controller);
		mc.runSteps(ctx);
		
		
	}
	

		
	}
	
	
	
	private List<AbstractStepWrapper> getElseChildren() throws TcXmlException {
		List<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		if(li.size() > 1) { 	//add else children
			
			Step secondchild = li.get(1);
			for (Step thestep : secondchild.getStep()) {
				AbstractStepWrapper newWrapper = StepWrapperFactory.getWrapper(thestep, controller, library);
				ret.add(newWrapper);
			}

			



}
		
		
		
		return ret;
	}
	
	public BoundList<Step> getIfSteps() throws TcXmlException {
		
		BoundList<Step> li = step.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
				sanitycheck(firstchild);
		li=firstchild.getStep();
		return li ;
		
	}
	
	
	public BoundList<Step> getElsefSteps() throws TcXmlException {
		
	BoundList<Step> li = step.getStep() ;
	if(li.size() > 1) {
		
	return li.get(1).getStep();	
	}else {
		
		return null;
		
	}
	
		
		
		
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
	
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer();
		pw.println(" // " + getTitle());
		sb.append(exportIfString()) ;
		pw.println(sb);
		List<AbstractStepWrapper> list =getIfChildren() ;
		for (AbstractStepWrapper child : list) {
			child.export(pw);
		}		
		 
		 pw.println("}//fin if " ) ;
		 pw.println(" else { " ) ;
		 
			List<AbstractStepWrapper> listelse =getElseChildren() ;
			for (AbstractStepWrapper child : listelse) {
				child.export(pw);
			}
		
		pw.println("}//fin else " ) ;
		
		
	}
	
	
	private String exportIfString() {
		
	StringBuffer ret = new StringBuffer();
	
	String input=argumentMap.get("Condition").getValue();
	String escapeChar ="\"";
	ret.append("if( TC.eval(").append( TcxmlUtils.formatAsJsString(input, escapeChar )  ).append(" )){");
	
	return ret.toString();
		
		
	}
	
	
	public ArgModel getCondition() {
		ArgModel cond = argumentMap.get("Condition");
		//javascript flag is not set so put it manually
		cond.setIsJavascript(true);
		return cond;
		
	}
	
	
	
	
}
