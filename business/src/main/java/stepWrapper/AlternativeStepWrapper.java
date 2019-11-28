package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class AlternativeStepWrapper extends AbstractStepWrapper{

	public AlternativeStepWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}
	
	
	public Step getAlternative() {
		// step type alternative. the real step is the child at index activestep
		int index = Integer.parseInt(step.getActiveStep()) ;
		Step altstep = step.getStep().get(index);	
		altstep.setIndex(step.getIndex());
		altstep.setDisabled(step.isDisabled());
		return altstep ;
		
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
	AbstractStepWrapper alstep = StepWrapperFactory.getWrapper(getAlternative(), controller, library);
	alstep.export(pw);
		
	}
	

}
