package tcxmlplugin.composite.view.arguments;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

import tcxml.core.TcXmlException;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.GenericAPIStepView;
import tcxmlplugin.composite.view.arguments.tc.TlcLogArgView;
import tcxmlplugin.composite.view.arguments.vts.VtcConnectArgView;



public class ArgumentViewFactory {
	
	
	
	public static StepArgument getArgumentForTestObject(String newAction, StepView view) throws TcXmlException {
		StepArgument ret = null;	
		
		switch(newAction) {
		
		case "Navigate":ret = getNavigateArgument(view); break;
		case "Type":ret = getTypeTextArgument(view);break;
		case "Click":ret=getClickArgument(view);break;
		case "Set":ret=getSetArgument(view);break;
		
		default: ret=getDefaultArgument(view); break;
		
		}
		
		
	
	
	
	
	
	
	return ret;
		
		
		
	}

	private static StepArgument getSetArgument(StepView view) throws TcXmlException {
		SetArgument ret = new SetArgument(view, SWT.NONE);	
		ret.populate(view.getController().getArguments(view.getModel().getArguments()));
		return ret;
	}

	private static StepArgument getClickArgument(StepView view) throws TcXmlException {
		ArrayList<String> li = new ArrayList<String>();
		li.add("Alt Key");
		li.add("Ctrl Key");
		li.add("Shift Key");
		li.add("Button");
		li.add("X Coordinate");
		li.add("Y Coordinate");
		
		
		DynamicArgumentView ret = new DynamicArgumentView(view, view.getStyle(), li);
		

		ret.populate(view.getController().getArguments(view.getModel().getArguments()));
		
		return ret;
		

	}

	private static StepArgument getTypeTextArgument(StepView view) throws TcXmlException {
		TypeTextArgs ret = new TypeTextArgs(view, view.getStyle());
		ret.populate(view.getController().getArguments(view.getModel().getArguments()));
		return ret;
	}

	private static StepArgument getDefaultArgument(StepView view) {
		DefaultArgument ret = new DefaultArgument(view, view.getStyle());
		return ret;
	}

	private static StepArgument getNavigateArgument(StepView view) throws TcXmlException {
		ArrayList<String> li = new ArrayList<String>();
		li.add("Location");
		DynamicArgumentView ret = new DynamicArgumentView(view, view.getStyle(), li);		
	
		ret.populate(view.getController().getArguments(view.getModel().getArguments()));
		
		return ret;
	}

	public static StepArgument getArgumentForFUnction(String functName, CallFunctionView callFunctionView)  throws TcXmlException {
		CallFunctionArg ret = new CallFunctionArg(callFunctionView, callFunctionView.getStyle());
		ret.populate(callFunctionView.getController().getArguments(callFunctionView.getModel().getArguments()));
		return ret;
	}

	public static StepArgument getArgumentForGenericApi(String newmethod, String category, GenericAPIStepView genericAPIStepView) throws TcXmlException {
		StepArgument ret= null;
		switch (category) {
		case "VTS":
			ret = getVtsArgumentForMethod(newmethod,genericAPIStepView);
			break;
		case "TC":
			ret=getTCargumentForMethod(newmethod,genericAPIStepView);
			default : ret= getDefaultArgument(genericAPIStepView);

		
			
		}
		return ret;
	}

	private static StepArgument getTCargumentForMethod(String newmethod, GenericAPIStepView genericAPIStepView) throws TcXmlException {
		StepArgument ret = null;
		switch (newmethod) {
		 
		case "log":
			ret = new TlcLogArgView(genericAPIStepView, SWT.NONE);
			ret.populate(genericAPIStepView.getController().getArguments(genericAPIStepView.getModel().getArguments()));
			break;
			
			default : ret= getDefaultArgument(genericAPIStepView);


		}
		return ret;
	}

	private static StepArgument getVtsArgumentForMethod(String newmethod,  GenericAPIStepView genericAPIStepView) throws TcXmlException {
		StepArgument ret = null;
		switch (newmethod) {
		 
		case "vtcConnect":
			ret = new VtcConnectArgView(genericAPIStepView, SWT.NONE);
			ret.populate(genericAPIStepView.getController().getArguments(genericAPIStepView.getModel().getArguments()));
			break;
			
			default : ret= getDefaultArgument(genericAPIStepView);


		}
		return ret;
	}

}
