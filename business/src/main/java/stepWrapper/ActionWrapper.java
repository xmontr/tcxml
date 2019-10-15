package stepWrapper;

import java.util.ArrayList;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class ActionWrapper extends BlockWrapper {

	public ActionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String title = step.getActionName() ;
		 String ret = formatTitle("", "action " + title);
		 return ret ;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>() ;
	}

}
