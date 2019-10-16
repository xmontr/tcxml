package stepWrapper;

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

public class FunctionWrapper extends AbstractStepWrapper {

	public FunctionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = "Function " + step.getAction();
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		BoundList<Step> children = step.getStep();
		
		List<AbstractStepWrapper> li = new ArrayList<AbstractStepWrapper>() ;
		for (Step child : children) {
			
		AbstractStepWrapper childwrapper = StepWrapperFactory.getWrapper(child, controller, library);	
	li.add(childwrapper);
		
		
			
		}
		
		controller.getLog().info("running function " + getTitle());
		
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(li, controller);
		ctx = mc .runSteps(ctx);
		
		return ctx;
	}

}