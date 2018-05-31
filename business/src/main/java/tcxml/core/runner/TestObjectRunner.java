package tcxml.core.runner;

import java.util.List;

import javax.json.JsonObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tcxml.core.PlayingContext;
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
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException  {
		PlayingContext nctx ;
	
		
		
		if(tcXmlController.isBrowserStep(step)) { // test object is the browser
			
		  nctx = runBrowserStep(ctx);
		
			
		}else {
			
			
			 nctx =	 runTestObjectStep(ctx);	
			
			
		}
		
	return nctx;
		
	}

	private PlayingContext runTestObjectStep( PlayingContext ctx) throws TcXmlException {
		TestObject to;
		if (library == null) {
			to = tcXmlController.getTestObjectById(step.getTestObject());

		} else {
			to = tcXmlController.getTestObjectById(step.getTestObject(), library);

		}
		
		String thexpath = tcXmlController.getXpathForTestObject(to);
	
		switch (step.getAction()) {
			
		case "Type": typeText( thexpath,ctx);
		break;
		case "Click":click(thexpath,ctx);
		break;
		case "Wait":waitOn(thexpath, ctx);
	break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}

	
	/**
	 * 
	 * wait for the html element to be present 
	 * @param thexpath
	 * @param ctx
	 * @throws TcXmlException 
	 */
	


	private void click(String thexpath, PlayingContext ctx) throws TcXmlException {
		
		String button ="left";
		
		if(arg.containsKey("Button")) {
			 button = arg.getJsonObject("Button").getJsonString("value").getString() ;
	}
	
		 switch (button) {
		case "left":
			clickLeft(thexpath);
			break;
		case "right":
			clickRight(thexpath);
			break;

		default:notImplemented();
			break;
		}
		
	}

	private void clickRight(String thexpath) throws TcXmlException {
		notImplemented();
		
	}

	private void clickLeft(String thexpath) throws TcXmlException {
		clickByXpath(thexpath);
		
		
	}

	private void notImplemented() throws TcXmlException {
		throw new TcXmlException("action not imemented " + step.getAction(), new IllegalStateException());
		
	}

	private void typeText(String thexpath, PlayingContext ctx) throws TcXmlException {
		JsonObject val = arg.getJsonObject("Value");
		String txt = val.getJsonString("value").getString();
			 boolean isj = val.getBoolean("evalJavaScript",false);
			 
			 
	/// if argument is in js it should be evaluated before
			 if(isj) {
				 
			txt =(String) tcXmlController.evaluateJS(txt,ctx);	 
			 }
			 
			 tcXmlController.typeTextXpath(thexpath, txt, 20);
		
	}

	private PlayingContext runBrowserStep(PlayingContext ctx) throws TcXmlException {
		PlayingContext ret;
		switch (step.getAction()) {
		case "Navigate":ret = navigate(ctx);break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		return ret;
		
	}

	private PlayingContext navigate(PlayingContext ctx) throws TcXmlException {
		
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
	location =		(tcXmlController.evaluateJS(location,ctx)).toString(); 
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
	        return ctx;
	     
		 
		 
		 
		
	}
	
	
	public void clickByXpath(String xpath) throws TcXmlException {
		tcXmlController.ensureDriver();
		WebDriver driver = tcXmlController.getDriver();
		final ByXPath xp2 = new ByXPath(xpath);	
		List<WebElement> elements = driver.findElements(xp2);
		tcXmlController.checkUnicity(elements, xpath);

		tcXmlController.highlight(driver.findElements(xp2).get(0));
			final Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElements(xp2).get(0)).click().perform();
		 

		
	}
	
	private void waitOn(String thexpath, PlayingContext ctx) throws TcXmlException {
		WebDriver dr = tcXmlController.getDriver();
		tcXmlController.ensureDriver();
		ByXPath xp = new ByXPath(thexpath);

		long TIMEOUTWAIT = 20000;
		WebDriverWait w = new WebDriverWait(dr, TIMEOUTWAIT );
		w.until(ExpectedConditions.presenceOfElementLocated(xp));	
		tcXmlController.highLightXpath(thexpath);
		
		
		
	}

}
