package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
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
	
	
	
	private String buildLoopString() {
	return "for(" + argumentMap.get("Init").getValue() +";" + argumentMap.get("Condition").getValue() + ";" + argumentMap.get("Increment").getValue() + ")" ;	
		
	}

}
