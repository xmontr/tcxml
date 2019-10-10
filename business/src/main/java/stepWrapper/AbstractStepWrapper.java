package stepWrapper;

import java.util.HashMap;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public abstract class AbstractStepWrapper {
	
	
	protected Step step ;
	protected TcXmlController controller;
	protected TruLibrary library ;
	protected HashMap<String, ArgModel> argumentMap;
	public AbstractStepWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super();
		this.step = step;
		this.controller = controller;
		this.library = library;
		argumentMap = controller.getArguments(step);
	}
	
	
	
	public abstract  String getTitle() throws TcXmlException ;
	
	
	
	 protected String formatTitle (String index , String txt) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" ").append(index).append(" ").append(txt);
		
		//add tag ifstep disabled
		
		if(step.isDisabled() != null && step.isDisabled()) {
			
			sb.append(" - disabled");
		}
		
		return sb.toString();
		
	}
	

}
