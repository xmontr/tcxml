package tcxml.core.runner;

import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class WaitRunner extends StepRunner{

	public WaitRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step,lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runStep() throws TcXmlException {
		// TODO Auto-generated method stub
		
	}

}
