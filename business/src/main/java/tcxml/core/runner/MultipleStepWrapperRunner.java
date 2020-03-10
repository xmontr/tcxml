package tcxml.core.runner;

import java.util.List;

import stepWrapper.AbstractStepWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;


public class MultipleStepWrapperRunner {
	
	
	private TcXmlController controller ;
	private List<AbstractStepWrapper> stepswrappers ;

	public MultipleStepWrapperRunner(List<AbstractStepWrapper> stepswrappers, TcXmlController controller) {
		super();
		this.stepswrappers = stepswrappers;
		this.controller = controller ;
	}
	
	
	
public PlayingContext runSteps( PlayingContext ctx) throws TcXmlException {
		
		PlayingContext temp = ctx;
		long interva = Long.parseLong(controller.getProperties().getProperty("RS.interstepInterval"));
		
		for (AbstractStepWrapper thestep : stepswrappers) {
			if(thestep.isDisabled()) {
				break;
			}
			
			temp = thestep.play(ctx);
			try {
				
				controller.getLog().fine("sleeping between step (ms) = " + interva);
				Thread.currentThread().sleep(interva);
			} catch (InterruptedException e) {
throw new TcXmlException("runtime error when waiting interval step time", e) ;
			}
			
		}
		

		return temp;
		
}

}
