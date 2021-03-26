package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.ListArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;


public class CallActionWrapper extends AbstractStepWrapper {

	public CallActionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), " Call Action" );
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();
		
		Map<String, Step> actionmap = controller.getActionMap();
		List<String> allActions = new ArrayList<String>(actionmap.keySet()) ;
	

		ArgModel arg = new ListArgModel("Action Name", allActions);
		arg.setValue("");		

		ret.add(arg);
		
		return ret ;
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		String actionName = argumentMap.get("Action Name").getValue();
		ActionWrapper calledaction = controller.getCalledAction(actionName);
		ctx= calledaction.runStep(ctx);
		return ctx;
	}
	
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		StringBuffer sb = new StringBuffer("// ").append( getTitle());
		pw.println(sb.toString());
	
		String actionName = argumentMap.get("Action Name").getValue();
		//String actionName = step.getActionName();
		
		sb = new StringBuffer();
		sb.append("await ");
		sb.append(actionName).append("();");
		pw.println(sb.toString());

		
	}
	
	/****
	 * 
	 * 
	 * @return the name of the action called
	 */
	
	public ListArgModel getCalledAction() {
		
		return (ListArgModel) getArgumentMap().get("Action Name");
		
		
	}
	
	/***
	 * 
	 *  save the name of the action to be called
	 * 
	 * 
	 * @param calledaction
	 */
	
	
	

	

}
