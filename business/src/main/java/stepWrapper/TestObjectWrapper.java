package stepWrapper;

import java.util.ArrayList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.TestObjectRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class TestObjectWrapper extends AbstractStepWrapper {

	public TestObjectWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret;
		TestObject to = null;
		if (!controller.isBrowserStep(step)) { // testobject is not browser

				to = controller.getTestObjectById(step.getTestObject(), library);

			String name = to.getAutoName() == null ? to.getManualName() : to.getAutoName() ;
			
			
			 ret = formatTitle(step.getIndex(), step.getAction() + " on " + name );
			
			
		}else {// testobject is browser
			
			ret = formatTitle(step.getIndex(), step.getAction() + " on Browser ");	
			
		}
		
		

		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>() ;	
		switch(step.getAction()) {
		
		case "Navigate":addNavigateArgument(ret); break;
		case "Type":addTypeArgument(ret);break;
		case "Click":addClickArgument(ret);break;
		case "Set":addSetArgument(ret);break;
		case "Evaluate JavaScript":addEvalJavascriptArgument(ret);break;
		case "Wait":addWaitArgument(ret);break;
		case "Verify" : addVerifyArgument(ret);break;
		case "Select":addSelectArgument(ret);break;
		
		default: throw new TcXmlException("no default value for step testobject action = " + step.getAction() + " id= " +step.getStepId()  , new IllegalArgumentException(step.getAction())) ; 
		
		}
		
		return ret;
	}
	
	private void addNavigateArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Location");
		lo.setValue("");
		ret.add(lo);
		
	}
	
	private void addEvalJavascriptArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Code");
mo.setValue("");
ret.add(mo);
		
	}
	
	private void addWaitArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Interval");
mo.setValue("3");
ret.add(mo);


mo = new ArgModel("Unit");
mo.setValue("Seconds");
ret.add(mo);
		
	}
	
	private void addClickArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
				mo = new ArgModel("Alt Key");
		mo.setValue("");
		ret.add(mo);
		
		mo = new ArgModel("Ctrl Key");
mo.setValue("");
ret.add(mo);

mo = new ArgModel("Shift Key");
mo.setValue("");
ret.add(mo);

mo = new ArgModel("Button");
mo.setValue("left");
ret.add(mo);

mo = new ArgModel("X Coordinate");
mo.setValue("");
ret.add(mo);

mo = new ArgModel("Y Coordinate");
mo.setValue("");
ret.add(mo);
	}
	

	private void addSelectArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Text");
mo.setValue("");
ret.add(mo);
mo = new ArgModel("Ordinal");
mo.setValue("1");
ret.add(mo);
		
	}
	
	private void addVerifyArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Property");
mo.setValue("Visible Text");
ret.add(mo);
mo = new ArgModel("Condition");
mo.setValue("Contain");
ret.add(mo);
		
	}
	
	private void addTypeArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Value");
		lo.setValue("");
		ret.add(lo);
		ArgModel cl = new ArgModel("Clear");
		cl.setValue("true");
		ret.add(cl);
		
	}
	
	
	private void addSetArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Path");
mo.setValue("");
ret.add(mo);
		
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		TestObjectRunner runner = new TestObjectRunner(step,library, controller);
		
	PlayingContext ret = runner.runStep(ctx);
	return ret;
	}
	
	
	
	

}
