package stepWrapper;

import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.GenericApiStepRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class GenericApiWrapper extends AbstractStepWrapper {

	public GenericApiWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
	StringBuffer sb  = new StringBuffer("Execute.").append(step.getCategoryName()).append(".").append(step.getMethodName());
		
		return( formatTitle(step.getIndex(), sb.toString()) );
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException{
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();	
		
		switch (step.getCategoryName()) {
		case "VTS":
			addVtsArgument(ret);
			break;
		case "TC":addTCArgument(ret);break;
			default : 

		
			
		}
		
		return ret;
	}

	private void addTCArgument(ArrayList<ArgModel> ret) {
		switch (step.getMethodName()) {
		 
		case "log":
		addTClogArgument(ret);

			break;
			
			default : break;


		}
		
	}
	
	private void addTClogArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = new ArgModel("text");
		mo.setValue("");
		ret.add(mo);
		
		
	}

	private void addVtsArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = null;
		switch (step.getMethodName()) {
		 
		case "vtcConnect":
			
			mo = new ArgModel("serverName");
			mo.setValue("");
			ret.add(mo);
			mo = new ArgModel("port");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("Variable");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("vtsName");
			mo.setValue("");
			ret.add(mo);
		break;
		
	
		
		
		
		
		
			
			default : 



		}
		
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		GenericApiStepRunner runner = new GenericApiStepRunner(step,library, controller);
		
	PlayingContext ret = runner.runStep(ctx);
	return ret;
	}

}
