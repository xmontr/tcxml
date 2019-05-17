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
import tcxmlplugin.composite.view.CallActionView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.CommentView;
import tcxmlplugin.composite.view.EvaluateJavascriptView;
import tcxmlplugin.composite.view.ForView;
import tcxmlplugin.composite.view.FunctionView;
import tcxmlplugin.composite.view.GenericAPIStepView;
import tcxmlplugin.composite.view.TestObjectView;
import tcxmlplugin.composite.view.WaitView;

public class StepViewerFactory {

	public static StepViewer getViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		
		StepViewer tv = null;
		
		String typeOfStep = step.getType();
		String action = step.getAction();
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
				
		case "alternative":
			tv=getAlernativeStep(step,stepContainer,controller);
			break;
				
		case "control":
			
			switch(action) {
			case "Call Action" :tv=getCallActionViewer(step,stepContainer,controller); break;
			case "For" : tv=getForViewer(step,stepContainer,controller); break;
			default: throw new TcXmlException("type=" + typeOfStep + " action="+action + " not implemented", new IllegalStateException());
			
			
			}		
		break;
		
		case "genericAPIStep" : 
			tv=getGenericAPIStepViewer(step,stepContainer,controller); break;
			
		}
		
		
		
		
		
		
		

		return tv;
	}



	private static StepViewer getGenericAPIStepViewer(Step step, StepContainer stepContainer,
			TcXmlController controller) throws TcXmlException {
		GenericAPIStepView view = new GenericAPIStepView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE, view, stepContainer);
		stepviewer.populate(step);
		return stepviewer;
		
	}



	private static StepViewer getAlernativeStep(Step step, StepContainer stepContainer, TcXmlController controller)   throws TcXmlException {
		// step type alternative. the real step is the child at index activestep
		int index = Integer.parseInt(step.getActiveStep()) ;
		Step altstep = step.getStep().get(index);	
		altstep.setIndex(step.getIndex());		
		return getViewer(altstep, stepContainer, controller);
	}



	private static StepViewer getForViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		ForView view = new ForView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
		
		
	}



	private static StepViewer getCallActionViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		CallActionView view = new CallActionView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
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
		StepViewer stepviewer = new StepViewer( SWT.NONE, view, stepContainer);
		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getUtilViewer(Step step, StepContainer stepContainer, TcXmlController controller,
			String action) throws TcXmlException {
		StepViewer tv = null;
		switch(action){
		case"Evaluate JavaScript":tv = getEvaluateJavascriptViewer(step, stepContainer, controller); break;
		case "Wait": tv = getWaitViewer(step, stepContainer, controller) ;  break;
		case "Comment":tv = getCommentViewer(step, stepContainer, controller) ;  break;
		default:tv = getDefaultViewer(step, stepContainer, controller);
			
		}
		
		return tv;
	}
	
	

	private static StepViewer getCommentViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		CommentView view = new CommentView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}



	private static StepViewer getWaitViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException {
		WaitView view = new WaitView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getEvaluateJavascriptViewer(Step step, StepContainer stepContainer,
			TcXmlController controller) throws TcXmlException {
		EvaluateJavascriptView view = new EvaluateJavascriptView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getFunctionViewer(Step step, LibraryView stepContainer,
			TcXmlController controller)  throws TcXmlException {
		
		FunctionView view = new FunctionView(stepContainer.getBar(), SWT.NONE,controller);
		view.setLibrary(stepContainer.getLibrary());
		view.setLibName(stepContainer.getLibraryName());
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		stepviewer.setPlayable(false);
		return stepviewer;
	}

	private static StepViewer getBlockViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException  {
		BlockView view = new BlockView(stepContainer.getBar(), SWT.NONE,controller);
		
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer); 

		stepviewer.populate(step);
		return stepviewer;
	}

	private static StepViewer getDefaultViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException  {
		
		BasicView view = new BasicView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer); 

	stepviewer.populate(step);
		return stepviewer;
	}
	
	

	private static StepViewer getCallFunctionViewer(Step step, StepContainer stepContainer, TcXmlController controller) throws TcXmlException   {
		
		CallFunctionView view = new CallFunctionView(stepContainer.getBar(), SWT.NONE,controller);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);
		stepviewer.populate(step);
		return stepviewer;

	}

}
