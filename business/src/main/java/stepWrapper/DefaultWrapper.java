package stepWrapper;

import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class DefaultWrapper extends AbstractStepWrapper {

	public DefaultWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), "step #" +  step.getStepId());
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
	throw new TcXmlException("step not implemented yet" + step.getStepId(), new IllegalStateException());
	}

}
