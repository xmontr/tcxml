package tcxmlplugin.composite.view.arguments;

import java.util.ArrayList;
import java.util.HashMap;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.core.DefaultArgumentStepFactory;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.GenericAPIStepView;




public class ArgumentViewFactory {
	
	
	
	public static DynamicArgumentView getArgumentViewForStep(Step step,StepView view)  throws TcXmlException {
	
	AbstractStepWrapper wrapper = StepWrapperFactory.getWrapper(step, view.getController(), view.getLibrary())	;
	HashMap<String, ArgModel> liar = view.getController().getArguments(step,wrapper.getDefaultArguments());
	DynamicArgumentView di = new DynamicArgumentView(view, view.getStyle(), liar);
	return di ;	
		
	}
	
	
	












	public static StepArgument getArgumentForFUnction(String functName, CallFunctionView callFunctionView)  throws TcXmlException {
		AbstractStepWrapper wrapper = StepWrapperFactory.getWrapper(callFunctionView.getModel(), callFunctionView.getController(), callFunctionView.getLibrary())	;
		HashMap<String, ArgModel> ar = callFunctionView.getController().getArguments(callFunctionView.getModel(),wrapper.getDefaultArguments());
		CallFunctionArg ret = new CallFunctionArg(callFunctionView, callFunctionView.getStyle(), ar );

		return ret;
	}







}
