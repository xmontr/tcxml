package tcxml.core;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class StepFactory {
	
	
	
	public static Step getStep( String steptype, TcXmlController controller, TruLibrary library) throws TcXmlException {
		
		Step  ret = null;
		
		switch(steptype) {
		
		case "WAIT": ret = generateWaitStep( controller,  library);  break;
		case "CALLFUNCTION": ret =  generateCallFunctionStep()  ; break;
		case "GENAPI" :ret = generateGenericApiStep();break;
		case "WAITFOROBJECT" : ret = generateWaitForObjectStep(); break ; 
		case "NODATA" : 
		default :  throw new TcXmlException("unknown step type "+steptype,  new IllegalAccessException(steptype)) ;
		}
		
		
		
		return ret;
	}

	private static Step generateWaitForObjectStep() {
		

		
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

	private static Step generateGenericApiStep() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Step generateCallFunctionStep() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Step generateWaitStep(TcXmlController controller, TruLibrary library) throws TcXmlException {
		Step ret = new Step();
		ret.setType("util");
		ret.setAction("Wait");
		
	AbstractStepWrapper thewrapper = StepWrapperFactory.getWrapper(ret, controller, library);		
		return thewrapper.getStep();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
