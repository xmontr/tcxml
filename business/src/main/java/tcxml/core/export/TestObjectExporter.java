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
@Deprecated
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
		
		return	genericExport("await TC.set",to);

	}

	private String select(TestObject to) throws TcXmlException {
		
		return	genericExport("await TC.select",to);

		
	}

	private String evalJSOnObject(TestObject to) throws TcXmlException {
		
		return	genericExport("await TC.evaljsOnObject",to);
		
	
	}

	private String verify(TestObject to) throws TcXmlException {
		
		return	genericExport("await TC.verify",to);
		

	}

	private String waitOn(TestObject to) throws TcXmlException {
		return	genericExport("await TC.waitOn",to);

	}

	private String click(TestObject to) throws TcXmlException {
		
		return	genericExport("await TC.click",to);
		

	}

	private String typeText(TestObject to) throws TcXmlException {
		
		return	genericExport("await TC.type",to);
		

		
		
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
		
		
	return	genericExport("await TC.navigate");
		

	}

}
