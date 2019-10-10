package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class TestObjectWrapperWrapper extends AbstractStepWrapper {

	public TestObjectWrapperWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret;
		TestObject to = null;
		if (!controller.isBrowserStep(step)) { // testobject is not browser

				to = controller.getTestObjectById(step.getTestObject(), library);

			String name = to.getAutoName() == null ? to.getManualName() : to.getAutoName() ;
			
			
			 ret = formatTitle(step.getIndex(), step.getAction() + " on " + name );
			
			
		}else {// testobject is browser
			
			ret = formatTitle(step.getIndex(), step.getAction() + " on Browser ");	
			
		}
		
		

		return ret;
	}

}
