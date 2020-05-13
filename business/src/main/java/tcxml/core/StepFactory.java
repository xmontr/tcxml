package tcxml.core;

import java.util.UUID;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class StepFactory {
	
	
	
	public static Step getStep( String steptype, TcXmlController controller, TruLibrary library) throws TcXmlException {
		
		Step  ret = null;
		
		switch(steptype) {
		
		case "WAIT": ret = generateWaitStep( controller,  library);  break;
		case "CALLFUNCTION": ret =  generateCallFunctionStep()  ; break;
		case "GENAPI" :ret = generateGenericApiStep();break;
		case "WAITFOROBJECT" : ret = generateWaitForObjectStep(controller, library); break ; 
		case "OBJECTACTION":ret = generateObjectStep(controller, library); break ;
		case "BROWSERACTION": ret = generateBrowserStep(controller, library); break ;
		case "COMMENT": ret = generateCommentStep(controller, library); break ;
		case "FOR": ret = generateForStep(controller, library); break ;
		case "If": ret = generateIfStep(controller, library); break ;
		case "IFEXIST":ret = generateIfExistStep(controller, library); break ;
		case "NODATA" : 
		default :  throw new TcXmlException("unknown step type "+steptype,  new IllegalAccessException(steptype)) ;
		}
		
		String id = "step:{" + UUID.randomUUID().toString() + "}";	
		ret.setStepId(id);
		ret.setLevel("1");
		return ret;
	}

	private static Step generateIfStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("control");
		ret.setAction("If");
		ret.setArguments("{\"Condition&quot;:{&quot;value\":\" 2 == 1\"}}");
		Step internalBlockif= buildInternalBlock() ;
		Step commentStepif = generateCommentStep(controller, library);
		commentStepif.setComment("Insert the step for If here");
		internalBlockif.getStep().add(commentStepif );
		
		Step internalBlockelse= buildInternalBlock() ;
		Step commentStepelse = generateCommentStep(controller, library);
		commentStepelse.setComment("Insert the step for Else here");
		internalBlockelse.getStep().add(commentStepelse );
		ret.getStep().add(internalBlockif);
		ret.getStep().add(internalBlockelse);
		
		
		
		return ret;
	}

	private static Step generateIfExistStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("control");
		ret.setAction("If2");
		Step tostep = new Step();
		tostep.setType("testObject");
		tostep.setAction("Wait");
		TestObject newTestObject = controller.generateNewTestObject( library); 
		tostep.setTestObject(newTestObject.getTestObjId());
		Step internalBlockif= buildInternalBlock() ;
		Step commentStepif = generateCommentStep(controller, library);
		commentStepif.setComment("Insert the step for If here");
		internalBlockif.getStep().add(commentStepif );
		
		Step internalBlockelse= buildInternalBlock() ;
		Step commentStepelse = generateCommentStep(controller, library);
		commentStepelse.setComment("Insert the step for Else here");
		internalBlockelse.getStep().add(commentStepelse );
		ret.getStep().add(tostep);
		ret.getStep().add(internalBlockif);
		ret.getStep().add(internalBlockelse);
		
		
		return ret;
	}

	private static Step generateCommentStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("util");
		ret.setAction("Comment");
		ret.setComment("this is a comment");
		return ret;
	}

	private static Step generateForStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("control");
		ret.setAction("For");
		ret.setArguments("{&quot;Init&quot;:{&quot;value&quot;:&quot;var i = 0&quot;,&quot;evalJavaScript&quot;:true},&quot;Condition&quot;:{&quot;value&quot;:&quot;i &lt; 1&quot;,&quot;evalJavaScript&quot;:true},&quot;Increment&quot;:{&quot;value&quot;:&quot;i++&quot;,&quot;evalJavaScript&quot;:true}}");
		Step internalBlock= buildInternalBlock() ;
		Step commentStep = generateCommentStep(controller, library);
		commentStep.setComment("Insert the step for the loop here");
		internalBlock.getStep().add(commentStep );
		ret.getStep().add(internalBlock);
		return ret;
	}

	private static Step buildInternalBlock() {
		Step ret = new Step();
		ret.setType("block");
		ret.setAction("internal");
		ret.setLevel("43");
		return ret;
	}

	private static Step generateBrowserStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("testObject");
		ret.setTestObject("testObj:{00000000-0000-0000-0000-000000000001}");
		ret.setAction("Navigate");
		return ret;
	}

	private static Step generateObjectStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("testObject");
		ret.setAction("Click");
		TestObject newTestObject = controller.generateNewTestObject( library); 
		ret.setTestObject(newTestObject.getTestObjId());
		return ret;
	}

	private static Step generateWaitForObjectStep(TcXmlController controller, TruLibrary library) {
		Step ret = new Step();
		ret.setType("testObject");
		ret.setAction("Wait");
		TestObject newTestObject = controller.generateNewTestObject( library); 
		ret.setTestObject(newTestObject.getTestObjId());
		return ret;

	}

	private static Step generateNewTestObject(TcXmlController controller, TruLibrary library) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Step generateGenericApiStep() {
		Step ret = new Step();
		ret.setType("genericAPIStep");
		ret.setCategoryName("VTS");
		ret.setMethodName("vtcConnect");
		return ret ;
	}

	private static Step generateCallFunctionStep() {
		Step ret = new Step();
		ret.setType("callFunction");

		return ret ;
	}

	private static Step generateWaitStep(TcXmlController controller, TruLibrary library) throws TcXmlException {
		Step ret = new Step();
		ret.setType("util");
		ret.setAction("Wait");
		
	AbstractStepWrapper thewrapper = StepWrapperFactory.getWrapper(ret, controller, library);		
		return thewrapper.getStep();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
