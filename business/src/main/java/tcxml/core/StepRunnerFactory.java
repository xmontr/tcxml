package tcxml.core;

import tcxml.model.Step;


public class StepRunnerFactory {
	
	
	public static StepRunner  getRunner ( Step step) {
		StepRunner ru =null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			ru = getCallFunctionRunner(step)	;
			break;
		case "util":ru=getUtilRunner(step,step.getAction());
		break;
		default:
			ru = getDefaultRunner(step);
			break;
			
		case "block":
			ru = getBlockRunner(step);
			break;
			
		
		case "function":

			ru=getFunctionRunner(step);
		break;	
		case "testObject":				
			ru=getTestObjectRunner(step);
			
		
		break;
			
		}
		
		
		
		
		
		
	return ru;	
	}

	private static StepRunner getTestObjectRunner(Step step) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getFunctionRunner(Step step) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getBlockRunner(Step step) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getDefaultRunner(Step step) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getUtilRunner(Step step, String action) {
		// TODO Auto-generated method stub
		return null;
	}

	private static StepRunner getCallFunctionRunner(Step step) {
		// TODO Auto-generated method stub
		return null;
	}

}
