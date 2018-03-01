package tcxml.core.runner;

import javax.json.JsonObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import tcxml.core.StepRunner;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class TestObjectRunner extends StepRunner{
	


	

	public TestObjectRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step,lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runStep() throws TcXmlException  {
	
	
		
		
		if(tcXmlController.isBrowserStep(step)) { // test object is the browser
			
		 runBrowserStep();
		
			
		}else {
			
			
			 runTestObjectStep();	
			
			
		}
		
	
		
	}

	private void runTestObjectStep() throws TcXmlException {
		TestObject to;
		if (library == null) {
			to = tcXmlController.getTestObjectById(step.getTestObject());

		} else {
			to = tcXmlController.getTestObjectById(step.getTestObject(), library);

		}
		
		String thexpath = tcXmlController.getXpathForTestObject(to);
	
		switch (step.getAction()) {
			
		case "Type": typeText( thexpath);
		}
		
		
		
	}

	private void typeText(String thexpath) throws TcXmlException {
		JsonObject val = arg.getJsonObject("Value");
		String txt = val.getJsonString("value").getString();
			 boolean isj = val.getBoolean("evalJavaScript",false);
			 
			 
	/// if argument is in js it should be evaluated before
			 if(isj) {
				 
			txt =(String) tcXmlController.evaluateJS(txt);	 
			 }
			 
			 tcXmlController.typeTextXpath(thexpath, txt, 20);
		
	}

	private void runBrowserStep() throws TcXmlException {
		switch (step.getAction()) {
		case "Navigate":navigate();break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		
	}

	private void navigate() throws TcXmlException {
		
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };

		
		
		
		WebDriver dr = tcXmlController.getDriver();
		tcXmlController.ensureDriver();
		
		JsonObject locobj = arg.getJsonObject("Location");
		String location = locobj.getJsonString("value").getString();
		 boolean isj = locobj.getBoolean("evalJavaScript");
	log.fine("found location for navigate:" + location);
		 
		 if(isj == true) { // need to evaluate js argument
	location =		(tcXmlController.evaluateJS(location)).toString(); 
		log.fine("after eval JS location is:" + location);	 
		 }
		
		 stat.setTimeStart(System.currentTimeMillis());
		 try {
		 dr.get(location);
		 }catch (Exception e) {
			throw new TcXmlException("driver fail to get url " + location, e);
		} 
		 
		 stat.setTimeLoaded(System.currentTimeMillis());
	        WebDriverWait wait = new WebDriverWait(dr, 30);
	        wait.until(pageLoadCondition);
	        stat.setTimeComplete(System.currentTimeMillis());
	        
	     
		 
		 
		 
		
	}

}
