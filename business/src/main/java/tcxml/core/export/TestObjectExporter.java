package tcxml.core.export;

import java.util.ArrayList;

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

	private String doSet(TestObject to) {
		// TODO Auto-generated method stub
		return null;
	}

	private String select(TestObject to) throws TcXmlException {
		ArgModel thetext = argumentMap.get("Text");
		ArgModel theordinal = argumentMap.get("Ordinal");
		String func = "TC.select";	
		
		StringBuilder objarg  = new StringBuilder();
		objarg.append("{\n");
		objarg.append("Text:").append(thetext).append("\n");
		objarg.append("Ordinal:").append(theordinal).append("\n");		
		objarg.append("}\n");
		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,objarg.toString(),
					tcXmlController.generateJsObject(to) 
					);
		return ret;	
		
	}

	private String evalJSOnObject(TestObject to) {
		// TODO Auto-generated method stub
		return null;
	}

	private String verify(TestObject to) {
		// TODO Auto-generated method stub
		return null;
	}

	private String waitOn(TestObject to) throws TcXmlException {
		String func = "TC.click";		
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					tcXmlController.generateJsObject(to) 
					);
		return ret;
	}

	private String click(TestObject to) throws TcXmlException {
		String func = "TC.click";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					tcXmlController.generateJsObject(to) 
					);
		return ret;
	}

	private String typeText(TestObject to) throws TcXmlException {
		ArgModel argtext = argumentMap.get("Value");
		String value = argtext.getIsJavascript() == true ? argtext.getValue(): TcxmlUtils.escapeStringParameter(argtext.getValue(), "\"");
		String func = "TC.type";
		String ret = TcxmlUtils.formatJavascriptFunction(
					func,
					value  ,
					tcXmlController.generateJsObject(to) 
					);
		return ret;
		
		
	}

	private String exportBrowserStep() {
		// TODO Auto-generated method stub
		return null;
	}

}
