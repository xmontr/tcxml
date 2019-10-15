package stepWrapper;

import java.util.ArrayList;
import java.util.Set;

import javax.script.ScriptContext;

import tcxml.core.ExecutionContext;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.CallFunctionAttribut;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class CallFunctionWrapper extends AbstractStepWrapper {

	public CallFunctionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret = formatTitle(step.getIndex(), " Call Function " + step.getLibName() + "." + step.getFuncName()) ;
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
		ArrayList<CallFunctionAttribut> callArguments = new ArrayList<CallFunctionAttribut>() ;
		Set<String> keys = argumentMap.keySet();
		for (String key : keys) {			
		 ArgModel att = argumentMap.get(key);			
			String val = att.getValue();		
			Boolean evalJavaScript = att.getIsJavascript();			
			CallFunctionAttribut callatt = new CallFunctionAttribut(key,val,evalJavaScript);			
			callArguments.add(callatt);
		}	
			String name = "Call function " +step.getLibName() + "." +  step.getFuncName();	
		ExecutionContext ec = new ExecutionContext(name  ,callArguments,ctx.getCurrentExecutionContext().getJsContext()); 
		//create new execution context for call
		ec.setParent(ctx);
		ScriptContext  jsctx = controller.buildCallFunctionContext(ec);		
		ret.pushContext(ec);
		FunctionWrapper calledfunction = controller.getCalledFunction(step.getLibName(),step.getFuncName());
		ctx= calledfunction.runStep(ctx);
		
		return ctx;
	}

}

