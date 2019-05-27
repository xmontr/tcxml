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
import tcxml.model.ArgModel;
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
		

	
		switch (step.getAction()) {
			
		case "Type": typeText( to,ctx);
		break;
		case "Click":click(to,ctx);
		break;
		case "Wait":waitOn(to, ctx);
	break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}
	
	
	
	private PlayingContext runTestObjectStep2( PlayingContext ctx) throws TcXmlException {
		TestObject to;
		if (library == null) {
			to = tcXmlController.getTestObjectById(step.getTestObject());

		} else {
			to = tcXmlController.getTestObjectById(step.getTestObject(), library);

		}
		
		
		
		
	
	
		switch (step.getAction()) {
			
		case "Type": typeText( to,ctx);
		break;
		case "Click":click(to,ctx);
		break;
		case "Wait":waitOn(to, ctx);
	break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}
	
	
	

	
	/**
	 * 
	 * wait for the html element to be present 
	 * @param to
	 * @param ctx
	 * @throws TcXmlException 
	 */
	


	private void click(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		String button ="left";
		
	ArgModel buttonarg = argumentMap.get("Button");
		
		if(buttonarg != null) {
			 button = buttonarg.getValue();
	}
	
		 switch (button) {
		case "left":
			clickLeft(to,ctx);
			break;
		case "right":
			clickRight(to,ctx);
			break;

		default:notImplemented();
			break;
		}
		
	}

	private void clickRight(TestObject to, PlayingContext ctx) throws TcXmlException {
		notImplemented();
		
	}

	private void clickLeft(TestObject to, PlayingContext ctx) throws TcXmlException {
		doclick(to,ctx);
		
		
	}

	private void notImplemented() throws TcXmlException {
		throw new TcXmlException("action not imemented " + step.getAction(), new IllegalStateException());
		
	}

	private void typeText(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		ArgModel txtarg = argumentMap.get("Value");
		String txt = txtarg.getValue();
		 boolean isj = txtarg.getIsJavascript();		
		
		
			 
			 
	/// if argument is in js it should be evaluated before
			 if(isj) {
				 
			txt =(String) tcXmlController.evaluateJS(txt,ctx);	 
			 }
			 
			 tcXmlController.typeText(ctx,to, txt, 20);
		
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
		
		/*
		 * JsonObject locobj = arg.getJsonObject("Location"); String location =
		 * locobj.getJsonString("value").getString(); boolean isj =
		 * locobj.getBoolean("evalJavaScript");
		 */
		 
		ArgModel locationArg = argumentMap.get("Location") ;
		
		String location =  tcXmlController.evaluateJsArgument(locationArg,ctx);	
		
		
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
	
	
	public void doclick(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		

		
		WebElement finded = tcXmlController.identifyElement(to,ctx);
		tcXmlController.highlight(finded);
			final Actions actions = new Actions(tcXmlController.getDriver());
			actions.moveToElement(tcXmlController.identifyElement(to, ctx)).click().perform();
		 

		
	}
	
	private void waitOn(TestObject to, PlayingContext ctx) throws TcXmlException {
		WebElement finded = tcXmlController.identifyElement(to, ctx);
		tcXmlController.highlight(finded);

		long TIMEOUTWAIT = 20000;
		WebDriverWait w = new WebDriverWait(tcXmlController.getDriver(), TIMEOUTWAIT );
		w.until(ExpectedConditions.elementToBeClickable(finded));	

		
		
		
	}

}
