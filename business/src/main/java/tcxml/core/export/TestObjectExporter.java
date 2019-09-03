package tcxml.core.export;

import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class TestObjectExporter  extends StepExporter{

	public TestObjectExporter(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step, lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String export() throws TcXmlException {
		String ret;
		if(tcXmlController.isBrowserStep(step)) { // test object is the browser
			
			  ret = exportBrowserStep();
			
				
			}else {
				
				
				 ret =	 exportTestObjectStep();	
				
				
			}
		return ret;
	}

	private String exportTestObjectStep() throws TcXmlException {
		String ret = null;
		TestObject to;
		if (library == null) {
			to = tcXmlController.getTestObjectById(step.getTestObject());

		} else {
			to = tcXmlController.getTestObjectById(step.getTestObject(), library);

		}
		

	
		switch (step.getAction()) {
			
		case "Type": ret =  typeText( to);
		break;
		case "Click":ret = click(to);
		break;
		case "Wait":ret = waitOn(to);
	break;
		case "Verify": ret = verify(to);break;
		
		case "Evaluate JavaScript":ret = evalJSOnObject(to);break;
		case "Select":ret = select(to);break;
		case "Set" : ret = doSet(to);break;
		
		default: ret = notImplemented();
		}
		return ret;
	}

	private String notImplemented() {
		// TODO Auto-generated method stub
		return " step not implemented " + step.getAction();
	}

	private String doSet(TestObject to) throws TcXmlException {
		ArgModel thepath = argumentMap.get("Path");
		
		String func = "await TC.set";	
		
		String objarg = tcXmlController.generateJSobject(thepath);
		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,tcXmlController.generateJsTestObject(to) ,
					
					objarg );
		return ret;	
	}

	private String select(TestObject to) throws TcXmlException {
		ArgModel thetext = argumentMap.get("Text");
		ArgModel theordinal = argumentMap.get("Ordinal");
		String func = "await TC.select";	
		
		String objarg = tcXmlController.generateJSobject(theordinal,thetext);
		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,objarg,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;	
		
	}

	private String evalJSOnObject(TestObject to) throws TcXmlException {
		ArgModel argcode = argumentMap.get("Code");
		
		String argjs = tcXmlController.generateJSobject(argcode);
		
		
		
	String func = "TC.evaljsOnObject";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs  ,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;
	}

	private String verify(TestObject to) throws TcXmlException {
		ArgModel argprop = argumentMap.get("Property");
		ArgModel argcondition = argumentMap.get("Condition");
	
		
		
		
		
		String argjs = tcXmlController.generateJSobject(argprop,argcondition);
		
		
		
	String func = "TC.verify";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs  ,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;
	}

	private String waitOn(TestObject to) throws TcXmlException {
		String func = "await TC.waitOn";		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;
	}

	private String click(TestObject to) throws TcXmlException {
		String func = "await TC.click";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;
	}

	private String typeText(TestObject to) throws TcXmlException {
		ArgModel argtext = argumentMap.get("Value");
		ArgModel argclear = argumentMap.get("Clear");
		
		String argjs = tcXmlController.generateJSobject(argtext,argclear);
		
		
		
		
	String func = "await TC.type";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs  ,
					tcXmlController.generateJsTestObject(to) 
					);
		return ret;
		
		
	}

	private String exportBrowserStep() throws TcXmlException {
		String ret;
		switch (step.getAction()) {
		case "Navigate":ret = navigate();break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		return ret;
	}

	private String navigate() {
		ArgModel argloc = argumentMap.get("Location");
		
		String argjs = tcXmlController.generateJSobject(argloc);
		
		
		
	String func = "await TC.navigate";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs  
				
					);
		return ret;
	}

}
