package tcxml.core;

import java.util.ArrayList;
import java.util.List;



import tcxml.model.ArgModel;
import tcxml.model.Step;



public class DefaultArgumentStepFactory {
	
	
	
	private Step  step ;
	
	
	private List<ArgModel> defaultList ;

	public DefaultArgumentStepFactory(Step step) {
		super();
		this.step = step;
		defaultList = new ArrayList<>();
	}
	
	
	
	public List<ArgModel> getDefaultArg() throws TcXmlException{
		String typeOfStep = step.getType();
		String action = step.getAction();
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();
		
		switch (typeOfStep) {
		case "callFunction":

			break;
		case "util":addUtilArgument(ret);
		break;
		default:

			break;
			
		case "block":

			break;
			
		
		case "function":

		break;	
		case "testObject":	addTestObjectArgument(ret);			

				break;
				
		case "alternative":

			break;
				
		case "control":
			
			switch(action) {
			case "Call Action" :break;
			case "For" :  break;
			case "If" : addIfArgument(ret); break;
			case "If2" :  break;
			default: throw new TcXmlException("type=" + typeOfStep + " action="+action + " default value for argument not implemented", new IllegalStateException());
			
			
			}		
		break;
		
		case "genericAPIStep" : addGenericApiStepArgument(ret);
			 break;
			
		}
		
		
	return ret ;	
		
		
	}



	private void addIfArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = new ArgModel("Condition");
mo.setValue("");
ret.add(mo);
		
	}



	private void addGenericApiStepArgument(ArrayList<ArgModel> ret) {
		switch (step.getCategoryName()) {
		case "VTS":
			addVtsArgument(ret);
			break;
		case "TC":addTCArgument(ret);break;
			default : 

		
			
		}
		
	}



	private void addTCArgument(ArrayList<ArgModel> ret) {
		switch (step.getMethodName()) {
		 
		case "log":
		addTClogArgument(ret);

			break;
			
			default : break;


		}
		
	}



	private void addTClogArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = new ArgModel("text");
		mo.setValue("");
		ret.add(mo);
		
		
	}



	private void addVtsArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = null;
		switch (step.getMethodName()) {
		 
		case "vtcConnect":
			
			mo = new ArgModel("serverName");
			mo.setValue("");
			ret.add(mo);
			mo = new ArgModel("port");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("Variable");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("vtsName");
			mo.setValue("");
			ret.add(mo);
			
			


			break;
			
			default : 



		}
		
	}



	private void addUtilArgument(ArrayList<ArgModel> ret) throws TcXmlException {
		switch(step.getAction()){
		case"Evaluate JavaScript":addEvalJavascriptArgument(ret); break;
		case "Wait": addWaitArgument(ret) ;  break;
		case "Comment":  break;
		default: throw new TcXmlException("id=" +step.getStepId()  + " default value for argument not implemented", new IllegalStateException());
			
		}
		
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



	private void addEvalJavascriptArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Code");
mo.setValue("");
ret.add(mo);
		
	}



	private void addTestObjectArgument(ArrayList<ArgModel> ret) throws TcXmlException {
		switch(step.getAction()) {
		
		case "Navigate":addNavigateArgument(ret); break;
		case "Type":addTypeArgument(ret);break;
		case "Click":addClickArgument(ret);break;
		case "Set":addSetArgument(ret);break;
		case "Evaluate JavaScript":addEvalJavascriptArgument(ret);break;
		case "Wait":addWaitArgument(ret);break;
		case "Verify" : addVerifyArgument(ret);break;
		
		default: throw new TcXmlException("no default value for step testobject action = " + step.getAction(), new IllegalArgumentException(step.getAction())) ; 
		
		}
		
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



	private void addSetArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Path");
mo.setValue("");
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



	

	private void addTypeArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Value");
		lo.setValue("");
		ret.add(lo);
		
	}



	private void addNavigateArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Location");
		lo.setValue("");
		ret.add(lo);
		
	}
	
	
	
	

	

}
