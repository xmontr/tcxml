package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class EvalJavascriptWrapper extends AbstractStepWrapper {

	public EvalJavascriptWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), "Evaluate Javascript code " +  getShortCode());
		return ret;
	}
	
	
	
	private String getShortCode() {
		
		String co = argumentMap.get("Code").getValue();
		String ret= co ;
		int size = co.length();
		if(size > 30) {
			StringBuffer sb = new StringBuffer();
			sb.append(co.substring(0, 15));
			sb.append(".......");
			sb.append(co.substring( size -15 , size )) ;
			ret = sb.toString();
		}
		




		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>() ;
		ArgModel mo;
		mo = new ArgModel("Code");
mo.setValue("");
ret.add(mo);

return ret;
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		String code = argumentMap.get("Code").getValue();
		controller.evaluateJS(code , ctx.getCurrentExecutionContext());
		controller.getLog().info("after evaljavascript step context is :");
		ctx.getCurrentExecutionContext().dumpJsContext();
		return ctx;
				
	}
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		pw.println(" // " + getTitle());
		
		
		
		ArgModel argtext = argumentMap.get("Code");
		
		String argjs = controller.generateJSobject(argtext);
		
		
		
	String func = " TC.evalJavascript";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs  
					
					);

		
		
		
		
		
	
		pw.println(ret);
		
	}
	
	
	
	

}
