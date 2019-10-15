package stepWrapper;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.IdentificationMethod;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.MultipleStepWrapperRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;


public class If2Wrapper extends AbstractStepWrapper {

	private TestObject theTestObject;


	public If2Wrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		
		//add object to test	
		BoundList<Step> li = step.getStep();
		 Step tostep = li.get(0);
		String referencedob = tostep.getTestObject() ;
	theTestObject = controller.getTestObjectById(referencedob, library);
		
		
		
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), " " +  buildIfString() );
		return ret;
	}
	
	
	private String buildIfString() {
		// TODO Auto-generated method stub
		return "if("  + theTestObject.getAutoName()  + " exists =" +argumentMap.get("Exists").getValue() ;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments() throws TcXmlException {
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();
		ArgModel mo = new ArgModel("Exists");
		
mo.setValue("true");
ret.add(mo);

return ret;
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		
		String exist = argumentMap.get("Exists").getValue();
		PlayingContext ret = null ;
		if(exist== "true") {
			
		ret =	runifexist(ctx);
		}else {
			
		ret=	runifnotexist(ctx);
		}
		
	return ret;	
	}
	

	private PlayingContext runifnotexist(PlayingContext ctx) throws TcXmlException {
		throw new TcXmlException(" if exist with exixts = false is not yet implemented", new IllegalArgumentException("runifnotexist"));

	}

	private PlayingContext runifexist(PlayingContext ctx) throws TcXmlException { 
		BoundList<Step> li = step.getStep();
		//add object to test	
		 Step theTestobjectref = li.get(0);
		 String referencedob = theTestobjectref.getTestObject() ;
		 TestObject theTestObject = controller.getTestObjectById(referencedob,library);
		 
		 
		String timeout =  step.getObjectTimeout();
		long to=0L;
		
		if(timeout == "" || timeout == null) {
			
			
		}else {
			
		to = Long.parseLong(timeout)	;
		}
		 
		 
	String identmethodstr = theTestObject.getIdents().getActive();
		
		IdentificationMethod identMetho = IdentificationMethod.get(identmethodstr);
		
		switch(identMetho) {
		
		case JAVASCRIPT: 
			throw new TcXmlException("if exist with identification by javascript is not implemented", new IllegalArgumentException(identmethodstr));

		
		case XPATH : 
		
		String xpath = controller.getXpathForTestObject(theTestObject);
		long TIMEOUTWAIT = to;
		try {
	WebDriverWait w = new WebDriverWait(controller.getDriver(), TIMEOUTWAIT );
		By locator = By.xpath(xpath);
		List<WebElement> lifound = w.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0)   );
		//tcXmlController.highLightXpath(xpath);
		
		runChildIfSteps(ctx);
		}catch (TimeoutException e) {
			
			controller.getLog().info("timeout of " + TIMEOUTWAIT + " is reached in if exist step ");		
		
		}
		

			
			break;
		
		
		}	
		
		 
		 
		 
		 
		 
	return ctx;	 
		
		
	}
	
	
	private void runChildIfSteps(PlayingContext ctx) throws TcXmlException {
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(getIfChildren(), controller);
		mc.runSteps(ctx);
		
	}

	private List<AbstractStepWrapper> getIfChildren() throws TcXmlException {
		List<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		Step firstchild = li.get(1);				
		sanitycheck(firstchild);


for (Step thestep : firstchild.getStep()) {
	AbstractStepWrapper newWrapper = StepWrapperFactory.getWrapper(thestep, controller, library);
	ret.add(newWrapper);
}
		
		
		
		return ret;
	}
	
	
	private List<AbstractStepWrapper> getElseChildren() throws TcXmlException {
		List<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		if(li.size() > 2) { 	//add else children
			
			Step secondchild = li.get(2);
			for (Step thestep : secondchild.getStep()) {
				AbstractStepWrapper newWrapper = StepWrapperFactory.getWrapper(thestep, controller, library);
				ret.add(newWrapper);
			}

			



}
		
		
		
		return ret;
	}
	
	
	
	
	
	
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}	
	
}
