package tcxml.core.runner;

import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

public class DefaulltRunner extends StepRunner{

	public DefaulltRunner(Step step, TcXmlController tcXmlController) {
		super(step, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runStep() throws TcXmlException {
		throw new TcXmlException(" no runner for this step", new IllegalStateException());
		
	}

}
