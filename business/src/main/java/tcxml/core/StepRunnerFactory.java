package tcxml.core;

import tcxml.core.runner.BlockRunner;
import tcxml.core.runner.CallFunctionRunner;
import tcxml.core.runner.DefaulltRunner;
import tcxml.core.runner.EvalJavascriptRunner;
import tcxml.core.runner.TestObjectRunner;
import tcxml.core.runner.WaitRunner;
import tcxml.model.Step;


public class StepRunnerFactory {
	
	
	public static StepRunner  getRunner ( Step step, TcXmlController tcXmlController) throws TcXmlException {
		StepRunner ru =null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			ru = getCallFunctionRunner(step,tcXmlController)	;
			break;
		case "util":ru=getUtilRunner(step,step.getAction(),tcXmlController);
		break;
		default:
			ru = getDefaultRunner(step,tcXmlController);
			break;
			
		case "block":
			ru = getBlockRunner(step,tcXmlController);
			break;
			
		
		case "function":

			ru=getFunctionRunner(step,tcXmlController);
		break;	
		case "testObject":				
			ru=getTestObjectRunner(step,tcXmlController);
			
		
		break;
			
		}
		
		
		
		
		
		
	return ru;	
	}

	private static StepRunner getTestObjectRunner(Step step, TcXmlController tcXmlController) throws TcXmlException {
		TestObjectRunner ret = new TestObjectRunner(step, tcXmlController);
		return ret;
	}

	private static StepRunner getFunctionRunner(Step step, TcXmlController tcXmlController) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getBlockRunner(Step step, TcXmlController tcXmlController) throws TcXmlException {
		BlockRunner ret = new BlockRunner(step, tcXmlController);
		return ret;
	}

	private static StepRunner getDefaultRunner(Step step, TcXmlController tcXmlController) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getUtilRunner(Step step, String action, TcXmlController tcXmlController) throws TcXmlException {
		StepRunner ret = null;
		switch(action){
		case"Evaluate JavaScript":ret = new EvalJavascriptRunner(step, tcXmlController); break;
		case "Wait":ret = new WaitRunner(step, tcXmlController);  break;
		default:ret=new DefaulltRunner(step, tcXmlController);
			
		}
		return ret;
	}

	private static StepRunner getCallFunctionRunner(Step step, TcXmlController tcXmlController) throws TcXmlException {
		CallFunctionRunner ret = new CallFunctionRunner(step, tcXmlController);
		return ret;
	}

}
