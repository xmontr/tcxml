package tcxml.core;

import tcxml.core.runner.BlockRunner;
import tcxml.core.runner.CallFunctionRunner;
import tcxml.core.runner.DefaulltRunner;
import tcxml.core.runner.EvalJavascriptRunner;
import tcxml.core.runner.TestObjectRunner;
import tcxml.core.runner.WaitRunner;
import tcxml.model.Step;
import tcxml.model.TruLibrary;


public class StepRunnerFactory {
	
	
	public static StepRunner  getRunner ( Step step, TcXmlController tcXmlController, TruLibrary lib) throws TcXmlException {
		StepRunner ru =null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			ru = getCallFunctionRunner(step,lib,tcXmlController)	;
			break;
		case "util":ru=getUtilRunner(step,lib,step.getAction(),tcXmlController);
		break;
			
		case "block":
			ru = getBlockRunner(step,lib,tcXmlController);
			break;
			
		
		case "function":

			ru=getFunctionRunner(step,lib,tcXmlController);
		break;	
		case "testObject":				
			ru=getTestObjectRunner(step,lib,tcXmlController);		
		break;
		default:
			ru = getDefaultRunner(step,lib,tcXmlController);
			break;
			
		}
		
		
		
		
		
		
	return ru;	
	}

	private static StepRunner getTestObjectRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		TestObjectRunner ret = new TestObjectRunner(step,lib, tcXmlController);
		return ret;
	}

	private static StepRunner getFunctionRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getBlockRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		BlockRunner ret = new BlockRunner(step,lib, tcXmlController);
		return ret;
	}

	private static StepRunner getDefaultRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		throw new TcXmlException("no runner for step" + step.getType(), new IllegalStateException());
		
		

	}

	private static StepRunner getUtilRunner(Step step, TruLibrary lib, String action, TcXmlController tcXmlController) throws TcXmlException {
		StepRunner ret = null;
		switch(action){
		case"Evaluate JavaScript":ret = new EvalJavascriptRunner(step,lib, tcXmlController); break;
		case "Wait":ret = new WaitRunner(step,lib, tcXmlController);  break;
		default:ret=new DefaulltRunner(step,lib, tcXmlController);
			
		}
		return ret;
	}

	private static StepRunner getCallFunctionRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		CallFunctionRunner ret = new CallFunctionRunner(step,lib, tcXmlController);
		return ret;
	}

}
