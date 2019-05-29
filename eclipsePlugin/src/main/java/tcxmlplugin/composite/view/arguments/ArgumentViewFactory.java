package tcxmlplugin.composite.view.arguments;

import java.util.ArrayList;
import java.util.HashMap;

import tcxml.core.DefaultArgumentStepFactory;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.GenericAPIStepView;




public class ArgumentViewFactory {
	
	
	
	public static DynamicArgumentView getArgumentViewForStep(Step step,StepView view)  throws TcXmlException {
	
		
	HashMap<String, ArgModel> liar = view.getController().getArguments(step);
	DynamicArgumentView di = new DynamicArgumentView(view, view.getStyle(), liar);
	return di ;	
		
	}
	
	
	












	public static StepArgument getArgumentForFUnction(String functName, CallFunctionView callFunctionView)  throws TcXmlException {
		HashMap<String, ArgModel> ar = callFunctionView.getController().getArguments(callFunctionView.getModel());
		CallFunctionArg ret = new CallFunctionArg(callFunctionView, callFunctionView.getStyle(), ar );

		return ret;
	}







}
