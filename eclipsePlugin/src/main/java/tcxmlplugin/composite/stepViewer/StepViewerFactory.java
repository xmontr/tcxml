package tcxmlplugin.composite.stepViewer;

import java.awt.Container;



import org.eclipse.swt.SWT;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.AStepContainer;
import tcxmlplugin.composite.LibraryView;
import tcxmlplugin.composite.view.BasicView;
import tcxmlplugin.composite.view.BlockView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.EvaluateJavascriptView;
import tcxmlplugin.composite.view.FunctionView;
import tcxmlplugin.composite.view.TestObjectView;
import tcxmlplugin.composite.view.WaitView;

public class StepViewerFactory {

	public static StepViewer getViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		
		StepViewer tv = null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			tv = getCallFunctionViewer(step,stepContainer,controller)	;
			break;
		case "util":tv=getUtilViewer(step,stepContainer,controller,step.getAction());
		break;
		default:
			tv = getDefaultViewer(step,stepContainer,controller);
			break;
			
		case "block":
			tv = getBlockViewer(step,stepContainer,controller);
			break;
			
		
		case "function":
			if(!(stepContainer instanceof LibraryView)) {
				
			throw new TcXmlException("cannot view a function outside of a LibraryView", new IllegalStateException())	;
			}
		tv=getFunctionViewer(step,(LibraryView)stepContainer,controller);
		break;	
		case "testObject":				
				tv=getTestObjectViewer(step,stepContainer,controller);
				break;
				
		case "control":tv=getCallActionViwer(step,stepContainer,controller);
			
		
		break;
			
		}
		
		
		
		
		
		
		

		return tv;
	}



	private static StepViewer getCallActionViwer(Step step, StepContainer stepContainer, TcXmlController controller) {
		// TODO Auto-generated method stub
		return null;
	}



	private static StepViewer getTestObjectViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		StepViewer tv = null;
		TruLibrary lib = null;
		//testobject can be referenced in the script or in a library !!!
		if(stepContainer  instanceof  FunctionView) {
			FunctionView funccont = (FunctionView)stepContainer;
			 lib = funccont.getLibrary();			
		}
	TestObjectView view = new TestObjectView(stepContainer.getBar(), SWT.NONE,controller);
	view.setLibrary(lib);
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE, view, stepContainer);
		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getUtilViewer(Step step, StepContainer stepContainer, TcXmlController controller,
			String action) throws TcXmlException {
		StepViewer tv = null;
		switch(action){
		case"Evaluate JavaScript":tv = getEvaluateJavascriptViewer(step, stepContainer, controller); break;
		case "Wait": tv = getWaitViewer(step, stepContainer, controller) ;  break;
		default:tv = getDefaultViewer(step, stepContainer, controller);
			
		}
		
		return tv;
	}
	
	

	private static StepViewer getWaitViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		WaitView view = new WaitView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getEvaluateJavascriptViewer(Step step, StepContainer stepContainer,
			TcXmlController controller) throws TcXmlException {
		EvaluateJavascriptView view = new EvaluateJavascriptView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getFunctionViewer(Step step, LibraryView stepContainer,
			TcXmlController controller)  throws TcXmlException {
		
		FunctionView view = new FunctionView(stepContainer.getBar(), SWT.NONE,controller);
		view.setLibrary(stepContainer.getLibrary());
		view.setLibName(stepContainer.getLibraryName());
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		stepviewer.setPlayable(false);
		return stepviewer;
	}

	private static StepViewer getBlockViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException  {
		BlockView view = new BlockView(stepContainer.getBar(), SWT.NONE,controller);
		
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer); 

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getDefaultViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException  {
		
		BasicView view = new BasicView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer); 

	stepviewer.populate(step);
		return stepviewer;
	}
	
	

	private static StepViewer getCallFunctionViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException   {
		
		CallFunctionView view = new CallFunctionView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer(stepContainer.getBar(), SWT.NONE,view,stepContainer);

		
		
		

	stepviewer.populate(step);
		return stepviewer;
	}

}
