package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

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
	
	

}
