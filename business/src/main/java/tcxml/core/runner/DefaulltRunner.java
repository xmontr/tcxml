package tcxml.core.runner;

import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class DefaulltRunner extends StepRunner{

	public DefaulltRunner(Step step,TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step,lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		throw new TcXmlException(" no runner for this step", new IllegalStateException());
		
	}

}
