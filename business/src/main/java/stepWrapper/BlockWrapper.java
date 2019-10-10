package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class BlockWrapper extends AbstractStepWrapper {

	public BlockWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		
		String title = null;
		String ret = null;
		
		if( step.getAction().equals("action")) { // the block represent an action
			
			title = step.getActionName() ;
			 ret = formatTitle("", "action " + title);
		}else { // block is a group of step
			
			 ret = formatTitle(step.getIndex(), "Group " + step.getAction());	
		}
		
		
		return ret;
	}

}
