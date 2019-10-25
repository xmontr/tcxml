package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;


public class StepWrapperFactory {
	
	
	
	
	public static AbstractStepWrapper getWrapper( Step  step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		
		AbstractStepWrapper ret = null;
		String typeOfStep = step.getType();
		String action = step.getAction();
		switch (typeOfStep) {
		case "callFunction":
			ret = getCallFunctionWrapper(step,controller,truLibrary)	;
			break;
		case "util":ret=getUtilWrapper(step,controller,step.getAction(),truLibrary);
		break;
		default:
			ret = getDefaultWrapper(step,controller,truLibrary);
			break;
			
		case "block":
			ret = getBlockWrapper(step,controller,truLibrary);
			break;
			
		
		case "function":
		ret=getFunctionWrapper(step,controller,truLibrary);
		break;	
		case "testObject":				
				ret=getTestObjectWrapper(step,controller,truLibrary);
				break;
				
		case "alternative":
			ret=getAlernativeStep(step,controller,truLibrary);
			break;
				
		case "control":
			
			switch(action) {
			case "Call Action" :ret=getCallActionWrapper(step,controller,truLibrary); break;
			case "For" : ret=getForWrapper(step,controller,truLibrary); break;
			case "If" : ret=getIfWrapper(step,controller,truLibrary); break;
			case "If2" : ret=getIf2Wrapper(step,controller,truLibrary); break;
			default: throw new TcXmlException("type=" + typeOfStep + " action="+action + " not implemented", new IllegalStateException());
			
			
			}		
		break;
		
		case "genericAPIStep" : 
			ret=getGenericAPIStepWrapper(step,controller,truLibrary); break;
			
		case "runLogic"	:
			ret=getRunLogicStepWrapper(step,controller,truLibrary); break;
			
		}
		
		
	return ret ;	
		
		
		
	}

	private static AbstractStepWrapper getRunLogicStepWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		return new RunLogicWrapper(step,controller,truLibrary) ;
	
	}

	private static AbstractStepWrapper getGenericAPIStepWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		return new GenericApiWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getIf2Wrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		// TODO Auto-generated method stub
		return new If2Wrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getIfWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException   {
		// TODO Auto-generated method stub
		return new IfWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getForWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		return new ForWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getCallActionWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		return new CallActionWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getAlernativeStep(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		
		AlternativeStepWrapper ret  = new AlternativeStepWrapper(step, controller, truLibrary) ;
		return ret ;
		

	}

	private static AbstractStepWrapper getTestObjectWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		return new TestObjectWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getFunctionWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		return new  FunctionWrapper(step,  controller,truLibrary);
	}

	private static AbstractStepWrapper getBlockWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		
		
		switch (step.getAction()) {
		case "action": return new ActionWrapper(step,  controller,truLibrary);
		case "Init Block": return new RunBlockWrapper(step,  controller,truLibrary);
		case "Action Block"	:return new RunBlockWrapper(step,  controller,truLibrary);
		case "End Block":return new RunBlockWrapper(step,  controller,truLibrary);
		case "Run Block":return new RunBlockWrapper(step,  controller,truLibrary);

		default:return new  BlockWrapper(step,  controller,truLibrary);
		
		}
		

	}

	private static AbstractStepWrapper getDefaultWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		return new DefaultWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getUtilWrapper(Step step, TcXmlController controller, String action,
			TruLibrary truLibrary) throws TcXmlException {
		
		AbstractStepWrapper ret = null;
		switch(action){
		case"Evaluate JavaScript":ret = getEvaluateJavascriptWrapper(step,  controller,truLibrary); break;
		case "Wait": ret = getWaitWrapper(step,  controller,truLibrary) ;  break;
		case "Comment":ret = getCommentWrapper(step, controller,truLibrary) ;  break;
		default:ret = getDefaultWrapper(step, controller,truLibrary);
			
		}
		return ret;
	}

	private static AbstractStepWrapper getCommentWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		// TODO Auto-generated method stub
		return new CommentWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getWaitWrapper(Step step, TcXmlController controller, TruLibrary truLibrary) throws TcXmlException {
		return new WaitWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getEvaluateJavascriptWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
		
		return new EvalJavascriptWrapper(step, controller,truLibrary);
	}

	private static AbstractStepWrapper getCallFunctionWrapper(Step step, TcXmlController controller,
			TruLibrary truLibrary) throws TcXmlException {
	
		return new CallFunctionWrapper(step, controller,truLibrary);
	}

}
