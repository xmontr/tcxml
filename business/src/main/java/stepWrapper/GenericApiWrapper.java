package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class GenericApiWrapper extends AbstractStepWrapper {

	public GenericApiWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
	StringBuffer sb  = new StringBuffer("Execute.").append(step.getCategoryName()).append(".").append(step.getMethodName());
		
		return( formatTitle(step.getIndex(), sb.toString()) );
	}

}
