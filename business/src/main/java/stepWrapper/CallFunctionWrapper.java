package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class CallFunctionWrapper extends AbstractStepWrapper {

	public CallFunctionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret = formatTitle(step.getIndex(), " Call Function " + step.getLibName() + "." + step.getFuncName()) ;
		return ret;
	}

}
