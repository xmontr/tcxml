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


public class BlockWrapper extends AbstractStepWrapper {

	public BlockWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		
			 String ret = formatTitle(step.getIndex(), "Group " + step.getAction());	
				
		
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>() ;
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		BoundList<Step> children = step.getStep();
		
		List<AbstractStepWrapper> li = new ArrayList<AbstractStepWrapper>() ;
		for (Step child : children) {
			
		AbstractStepWrapper childwrapper = StepWrapperFactory.getWrapper(child, controller, library);	
	li.add(childwrapper);
		
		
			
		}
		
		controller.getLog().info("running block " + getTitle());
		
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(li, controller);
		ctx = mc .runSteps(ctx);
		
		return ctx;
	}
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer(" // ").append(getTitle());
		pw.println(sb.toString());
		for (AbstractStepWrapper child : getChildren()) {
			child.export(pw);
			
		}
		
	}
	
	
	private List<AbstractStepWrapper> getChildren() throws TcXmlException {
		
		ArrayList<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
		//		sanitycheck(firstchild);
		li=firstchild.getStep();
		
		
		
		
		for (Step thestep : li) {
			
		AbstractStepWrapper newwrapper = StepWrapperFactory.getWrapper(thestep, controller, library) ;
			ret.add(newwrapper);
		}
		return ret;
	}

}
