package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class RunBlockWrapper extends BlockWrapper {

	public RunBlockWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		
		
		
	
	}

@Override
public void export(PrintWriter pw) throws TcXmlException {
	// TODO Auto-generated method stub
	super.export(pw);
}
@Override
protected List<AbstractStepWrapper> getChildren() throws TcXmlException {
	
	ArrayList<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
	BoundList<Step> li = step.getStep();	
	for (Step thestep : li) {
		
	AbstractStepWrapper newwrapper = StepWrapperFactory.getWrapper(thestep, controller, library) ;
		ret.add(newwrapper);
	}
	return ret;
}





}
