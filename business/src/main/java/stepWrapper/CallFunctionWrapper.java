package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.script.ScriptContext;
import tcxml.core.ExecutionContext;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.CallFunctionAttribut;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class CallFunctionWrapper extends AbstractStepWrapper {

	public CallFunctionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret = formatTitle(step.getIndex(), " Call Function " + step.getLibName() + "." + step.getFuncName());
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		PlayingContext ret = ctx;
		// build the list of the call function parameter
		ArrayList<CallFunctionAttribut> callArguments = new ArrayList<CallFunctionAttribut>();
		Set<String> keys = argumentMap.keySet();
		for (String key : keys) {
			ArgModel att = argumentMap.get(key);
			String val = att.getValue();
			Boolean evalJavaScript = att.getIsJavascript();
			boolean isparam = att.getIsParam();
			CallFunctionAttribut callatt = new CallFunctionAttribut(key, val, evalJavaScript, isparam);
			callArguments.add(callatt);
		}
		String name = "Call function " + step.getLibName() + "." + step.getFuncName();
		ExecutionContext ec = new ExecutionContext(name, callArguments,
				ctx.getCurrentExecutionContext().getJsContext());
		// create new execution context for call
		ec.setParent(ctx);
		ScriptContext jsctx = controller.buildCallFunctionContext(ec);
		ret.pushContext(ec);
		FunctionWrapper calledfunction = controller.getCalledFunction(step.getLibName(), step.getFuncName());
		ctx = calledfunction.runStep(ctx);

		return ctx;
	}

	private ArrayList<CallFunctionAttribut> getCallFunctionAttribut() {
		// build the list of the call function parameter
		ArrayList<CallFunctionAttribut> callArguments = new ArrayList<CallFunctionAttribut>();
		Set<String> keys = argumentMap.keySet();
		for (String key : keys) {
			ArgModel att = argumentMap.get(key);
			String val = att.getValue();
			Boolean evalJavaScript = att.getIsJavascript();
			Boolean isparm = att.getIsParam();
			CallFunctionAttribut callatt = new CallFunctionAttribut(key, val, evalJavaScript, isparm);
			callArguments.add(callatt);
		}
		return callArguments;

	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		Vector<String> liparam = new Vector<String>();
		String[] tab;
		StringBuffer sb = new StringBuffer("// ").append(getTitle());
		pw.println(sb.toString());
		StringBuffer sb2 = new StringBuffer();
		sb2.append("await ");

		List<CallFunctionAttribut> listArguments = getCallFunctionAttribut();

		String func = " await TC.callFunction";

		sb2.append(step.getLibName()).append(".").append(step.getFuncName());

		String objarg = controller.generateFunctArgJSobject(listArguments);

		String ret = TcxmlUtils.formatJavascriptFunction(func, sb2.toString(), objarg.toString());

		pw.println(ret.toString());
	}

	/***
	 * 
	 * 
	 * 
	 * @return the name of the function
	 */

	public String getFunctionName() {

		return step.getFuncName();

	}

	public String getLibName() {

		return step.getLibName();

	}

	public void saveFunction(String libname, String funcName) {

		step.setFuncName(funcName);
		step.setLibName(libname);

	}
	
	
public Set<String> getAllLibraryName(){
	
	return controller.getLibraries().keySet();
	
}


public List<String> getFunctionInLibrary(String libname) throws TcXmlException {
	
	return controller.getFunctionsNameForLib(step.getLibName() );
	
}
	
	

}
