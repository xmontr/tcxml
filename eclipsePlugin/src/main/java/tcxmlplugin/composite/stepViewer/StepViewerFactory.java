package tcxmlplugin.composite.stepViewer;

import static org.hamcrest.Matchers.instanceOf;

import java.awt.Container;



import org.eclipse.swt.SWT;
import org.osgi.framework.AllServiceListener;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.ActionWrapper;
import stepWrapper.AlternativeStepWrapper;
import stepWrapper.BlockWrapper;
import stepWrapper.CallActionWrapper;
import stepWrapper.CallFunctionWrapper;
import stepWrapper.CommentWrapper;
import stepWrapper.DefaultWrapper;
import stepWrapper.EvalJavascriptWrapper;
import stepWrapper.ForWrapper;
import stepWrapper.FunctionWrapper;
import stepWrapper.GenericApiWrapper;
import stepWrapper.If2Wrapper;
import stepWrapper.IfWrapper;
import stepWrapper.RunBlockWrapper;
import stepWrapper.StepWrapperFactory;
import stepWrapper.TestObjectWrapper;
import stepWrapper.WaitWrapper;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.AStepContainer;
import tcxmlplugin.composite.ActionView;
import tcxmlplugin.composite.LibraryView;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.AlternativeView;
import tcxmlplugin.composite.view.BasicView;
import tcxmlplugin.composite.view.BlockView;
import tcxmlplugin.composite.view.CallActionView;
import tcxmlplugin.composite.view.CallFunctionView;
import tcxmlplugin.composite.view.CommentView;
import tcxmlplugin.composite.view.EvaluateJavascriptView;
import tcxmlplugin.composite.view.ForView;
import tcxmlplugin.composite.view.FunctionView;
import tcxmlplugin.composite.view.GenericAPIStepView;
import tcxmlplugin.composite.view.IF2View;
import tcxmlplugin.composite.view.IfView;
import tcxmlplugin.composite.view.RunBlockView;
import tcxmlplugin.composite.view.TestObjectView;
import tcxmlplugin.composite.view.WaitView;

public class StepViewerFactory {

	public static StepViewer getViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		
	boolean founded = false ;
	StepView theview =null;
		
		//other value	
		AbstractStepWrapper thesteppwrapper = StepWrapperFactory.getWrapper(step, controller, truLibrary);
		
		if(thesteppwrapper instanceof BlockWrapper   ) {			
			BlockWrapper blockwrapper  = new BlockWrapper(step, controller, truLibrary);			
			 theview = new BlockView(stepContainer.getBar(), SWT.NONE) ;
			 theview.setStepWrapper( blockwrapper);
	
			founded = true ;
		}
		
		
		
		
		if(thesteppwrapper instanceof RunBlockWrapper   ) {			
			BlockWrapper blockwrapper  = new RunBlockWrapper(step, controller, truLibrary);			
			 theview = new RunBlockView(stepContainer.getBar(), SWT.NONE) ;
			 theview.setStepWrapper( blockwrapper);
	
			founded = true ;
		}
		
		
		
		
		
		
		////
		
		
		
	
	if(thesteppwrapper instanceof CallActionWrapper    ) {		
		CallActionWrapper calactionwrapper  = new CallActionWrapper(step, controller, truLibrary);
		 theview = new CallActionView(stepContainer.getBar(), SWT.NONE) ;
		 theview.setStepWrapper(calactionwrapper);
		 founded = true ;
	}
	

	
	
	if(thesteppwrapper instanceof CallFunctionWrapper  ) {
		
		CallFunctionWrapper thewrapper  = new CallFunctionWrapper(step, controller, truLibrary);
		theview = new CallFunctionView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	if(thesteppwrapper instanceof CommentWrapper  ) {		
		CommentWrapper commentwrap  = new CommentWrapper(step, controller, truLibrary);
		theview = new CommentView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(commentwrap);
		founded = true ;
	}
	
	
	
	if(thesteppwrapper instanceof EvalJavascriptWrapper  ) {
		
		EvalJavascriptWrapper evalwrapper  = new EvalJavascriptWrapper(step, controller, truLibrary);
		theview = new EvaluateJavascriptView(stepContainer.getBar(), SWT.NONE ) ;
		theview.setStepWrapper(evalwrapper);
		founded = true ;
	}
	

	
	
	if(thesteppwrapper instanceof ForWrapper  ) {
		
		ForWrapper thewrapper  = new ForWrapper(step, controller, truLibrary);
		theview = new ForView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	
	if(thesteppwrapper instanceof FunctionWrapper  ) {
		
		if(!(stepContainer instanceof LibraryView)) {
			
		throw new TcXmlException("cannot view a function outside of a LibraryView", new IllegalStateException())	;
		}
		
		FunctionWrapper thewrapper  = new FunctionWrapper(step, controller, truLibrary);
		 theview = new FunctionView(stepContainer.getBar(), SWT.NONE ) ;
		theview.setLibrary(((LibraryView)stepContainer).getLibrary());
	
		
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	
	if(thesteppwrapper instanceof GenericApiWrapper  ) {
		
		GenericApiWrapper thewrapper  = new GenericApiWrapper(step, controller, truLibrary);
		theview = new GenericAPIStepView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	if(thesteppwrapper instanceof If2Wrapper  ) {
		
		If2Wrapper thewrapper  = new If2Wrapper(step, controller, truLibrary);
		theview = new IF2View(stepContainer.getBar(), SWT.NONE ) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	
	if(thesteppwrapper instanceof IfWrapper  ) {
		
		IfWrapper thewrapper  = new IfWrapper(step, controller, truLibrary);
		theview = new IfView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	
	if(thesteppwrapper instanceof TestObjectWrapper  ) {
		
		TestObjectWrapper thewrapper  = new TestObjectWrapper(step, controller, truLibrary);
		theview = new TestObjectView(stepContainer.getBar(), SWT.NONE ) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	if(thesteppwrapper instanceof WaitWrapper  ) {
		
		WaitWrapper thewrapper  = new WaitWrapper(step, controller, truLibrary);
		theview = new WaitView(stepContainer.getBar(), SWT.NONE ) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
	}
	
	
	if(thesteppwrapper instanceof AlternativeStepWrapper  ) {
		
		AlternativeStepWrapper thewrapper = new AlternativeStepWrapper(step,controller, truLibrary);
		theview = new AlternativeView(stepContainer.getBar(), SWT.NONE) ;
		theview.setStepWrapper(thewrapper);
		founded = true ;
		
		
		/*
		 * AlternativeStepWrapper thewrapper = new AlternativeStepWrapper(step,
		 * controller, truLibrary); Step thestep = thewrapper.getAlternative(); return
		 * getViewer(thestep, stepContainer, controller, truLibrary);
		 */
	}
	
	
	
	
	// default value	
	
	if( founded == false ) {
		
		  DefaultWrapper defaultwrapper = new DefaultWrapper(step, controller, truLibrary) ;
			 theview = new BasicView(stepContainer.getBar(), SWT.NONE) ;
			theview.setStepWrapper(defaultwrapper);
		
		
	}
	
	
	
	
	
	
	
	
	
	
		

	 StepViewer ret = new StepViewer( SWT.NONE,theview,stepContainer); 	 
		
		
	return ret ;	
		
		
		
	/*	
		
		
		
		StepViewer tv = null;
		
		String typeOfStep = step.getType();
		String action = step.getAction();
		switch (typeOfStep) {
		case "callFunction":
			tv = getCallFunctionViewer(step,stepContainer,controller,truLibrary)	;
			break;
		case "util":tv=getUtilViewer(step,stepContainer,controller,step.getAction(),truLibrary);
		break;
		default:
			tv = getDefaultViewer(step,stepContainer,controller,truLibrary);
			break;
			
		case "block":
			tv = getBlockViewer(step,stepContainer,controller,truLibrary);
			break;
			
		
		case "function":
			if(!(stepContainer instanceof LibraryView)) {
				
			throw new TcXmlException("cannot view a function outside of a LibraryView", new IllegalStateException())	;
			}
		tv=getFunctionViewer(step,(LibraryView)stepContainer,controller,truLibrary);
		break;	
		case "testObject":				
				tv=getTestObjectViewer(step,stepContainer,controller,truLibrary);
				break;
				
		case "alternative":
			tv=getAlernativeStep(step,stepContainer,controller,truLibrary);
			break;
				
		case "control":
			
			switch(action) {
			case "Call Action" :tv=getCallActionViewer(step,stepContainer,controller,truLibrary); break;
			case "For" : tv=getForViewer(step,stepContainer,controller,truLibrary); break;
			case "If" : tv=getIfViewer(step,stepContainer,controller,truLibrary); break;
			case "If2" : tv=getIf2Viewer(step,stepContainer,controller,truLibrary); break;
			default: throw new TcXmlException("type=" + typeOfStep + " action="+action + " not implemented", new IllegalStateException());
			
			
			}		
		break;
		
		case "genericAPIStep" : 
			tv=getGenericAPIStepViewer(step,stepContainer,controller,truLibrary); break;
			
		}
		
		
		
		
		
		
		

		return tv;
		
		
		*/
	}


/*
	private static StepViewer getIf2Viewer(Step step, StepContainer stepContainer, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		IF2View view = new IF2View(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
	}*/


/*
	private static StepViewer getIfViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		IfView view = new IfView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
	}*/


/*
	private static StepViewer getGenericAPIStepViewer(Step step, StepContainer stepContainer,
			TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		GenericAPIStepView view = new GenericAPIStepView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE, view, stepContainer);
		stepviewer.populate(step);
		return stepviewer;
		
	}*/


/*
	private static StepViewer getAlernativeStep(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary)   throws TcXmlException {
		// step type alternative. the real step is the child at index activestep
		int index = Integer.parseInt(step.getActiveStep()) ;
		Step altstep = step.getStep().get(index);	
		altstep.setIndex(step.getIndex());		
		return getViewer(altstep, stepContainer, controller,truLibrary);
	}*/



	/*
	private static StepViewer getForViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		ForView view = new ForView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
		
		
	}*/


/*
	private static StepViewer getCallActionViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		CallActionView view = new CallActionView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer sv =  new StepViewer( SWT.NONE, view, stepContainer);
		sv.populate(step);
		return sv;
	}*/


/*
	private static StepViewer getTestObjectViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		StepViewer tv = null;

	TestObjectView view = new TestObjectView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
	
		StepViewer stepviewer = new StepViewer( SWT.NONE, view, stepContainer);
		stepviewer.populate(step);
		return stepviewer;
	}
	
	*/
	
	/*

	private static StepViewer getUtilViewer(Step step, StepContainer stepContainer, TcXmlController controller,
			String action, TruLibrary truLibrary) throws TcXmlException {
		StepViewer tv = null;
		switch(action){
		case"Evaluate JavaScript":tv = getEvaluateJavascriptViewer(step, stepContainer, controller,truLibrary); break;
		case "Wait": tv = getWaitViewer(step, stepContainer, controller,truLibrary) ;  break;
		case "Comment":tv = getCommentViewer(step, stepContainer, controller,truLibrary) ;  break;
		default:tv = getDefaultViewer(step, stepContainer, controller,truLibrary);
			
		}
		
		return tv;
	}*/
	
	
/*
	private static StepViewer getCommentViewer(Step step, StepContainer stepContainer, TcXmlController controller,TruLibrary truLibrary) throws TcXmlException {
		CommentView view = new CommentView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}*/


/*
	private static StepViewer getWaitViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		WaitView view = new WaitView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}*/
	
	/*

	private static StepViewer getEvaluateJavascriptViewer(Step step, StepContainer stepContainer,
			TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		EvaluateJavascriptView view = new EvaluateJavascriptView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		return stepviewer;
	}*/
	
	/*

	private static StepViewer getFunctionViewer(Step step, LibraryView stepContainer,
			TcXmlController controller, TruLibrary truLibrary)  throws TcXmlException {
		
		FunctionView view = new FunctionView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		//view.setLibrary(stepContainer.getLibrary());
		view.setLibName(stepContainer.getLibraryName());
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);

		stepviewer.populate(step);
		stepviewer.setPlayable(false);
		return stepviewer;
	}

	private static StepViewer getBlockViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException  {
		BlockView view = new BlockView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer); 

		stepviewer.populate(step);
		return stepviewer;
	}
*/
	
	/*
	private static StepViewer getDefaultViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException  {
		
		BasicView view = new BasicView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer); 

	stepviewer.populate(step);
		return stepviewer;
	}
	
	
*/
	
	/*
	private static StepViewer getCallFunctionViewer(Step step, StepContainer stepContainer, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException   {
		
		CallFunctionView view = new CallFunctionView(stepContainer.getBar(), SWT.NONE,controller,truLibrary);
		StepViewer stepviewer = new StepViewer( SWT.NONE,view,stepContainer);
		stepviewer.populate(step);
		return stepviewer;

	}*/

}
