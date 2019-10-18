package tcxml.core.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.IdentificationMethod;
import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Roles;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;


@Deprecated
 public class  TestObjectRunner extends StepRunner{
	


	

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
		case "Verify":verify(to, ctx);break;
		
		case "Evaluate JavaScript":evalJSOnObject(to,ctx);break;
		case "Select":select(to,ctx);break;
		case "Set" : doSet(to,ctx);break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}
	
	
	
	private void doSet(TestObject to, PlayingContext ctx) throws TcXmlException  {
		ArgModel thepath = argumentMap.get("Path");
		WebElement theelement = tcXmlController.identifyElement(to, ctx.getCurrentExecutionContext());
		String thefile = tcXmlController.evaluateJsArgument(thepath, ctx.getCurrentExecutionContext());
		File file = new File(thefile);	
		if( !file.exists()) {
			
		throw new TcXmlException("fail to upload file from location "+ thefile, new IllegalArgumentException(thefile))	;
			
		}
		tcXmlController.getLog().info("uploading file from " + thefile + " on element " + theelement.getTagName());
		
		theelement.sendKeys(thefile);
		
		
				
				
		
	}

	private void select(TestObject to, PlayingContext ctx) throws TcXmlException {
		ArgModel thetext = argumentMap.get("Text");
		ArgModel theordinal = argumentMap.get("Ordinal");
		WebElement theelement = tcXmlController.identifyElement(to, ctx.getCurrentExecutionContext());
		int index = 1;
		
		if(hasRole(to,"listbox")) {
			
			selectBySelect(theelement, thetext, theordinal);
			
		}else if (hasRole(to,"radiogroup")) {
			
			selectByRadioGroup(theelement, thetext, theordinal);
			
		}else {
			
			
			throw new TcXmlException("unsupported role for select action", new IllegalArgumentException());
		}
		
	
		
		
		
		
		
		
	}

	private void selectByRadioGroup(WebElement theelement, ArgModel thetext, ArgModel theordinal) {
	By path2allRadioInput = new ByXPath("descendant::input[@type='radio']");
	List<WebElement> listinput = theelement.findElements(path2allRadioInput);
	List<WebElement> listLabel = new ArrayList<WebElement>();
	WebElement selection = null;
	String searchedText = thetext.getValue();

	
	for (WebElement radioinput : listinput) {
		String inputid = radioinput.getAttribute("id");
		By path2label = new ByXPath("//label[@for='" + inputid +"']");
		List<WebElement> temp = radioinput.findElements(path2label);
		listLabel.addAll(temp);
	}//endfor
		
		if( ! searchedText.isEmpty() ) {// search by text
			
			for (WebElement label : listLabel) {
				String txt = label.getText();
				if(txt.equals(searchedText)) {
					label.click();break;
				}
				
			}
			
		
		
	}else {// search by ordinal
		
		int ordinal = Integer.parseInt(theordinal.getValue());
		listLabel.get(ordinal).click();
		
		
		
	}
	
		
	}

	private boolean hasRole(TestObject to, String therole) {
		boolean ret = false;
		 BoundList<String> allreoles = to.getRoles().getRole();
		 
		 for (String role : allreoles) {
			 if(role.equals(therole)) {
				 ret=true;break;
				 
			 }
			
		}
		
		
		return ret;
	}

	private void selectBySelect(WebElement theelement, ArgModel thetext, ArgModel theordinal) throws TcXmlException {
		Select theDrobBox = new Select(theelement);
		if(thetext != null  && !thetext.getValue().isEmpty()) {
		
			
			try {
		theDrobBox.selectByVisibleText(thetext.getValue());	
			}
			catch (Exception e) {
				throw new TcXmlException("fail select by visible text ", e);
				
			
			}
		}else {
			
			int index;
			try {
	 index = Integer.parseInt(theordinal.getValue())	;
			}catch ( Exception e) {
			throw new TcXmlException("fail to parse ordinal parameter for select action", e);
			}
	
			try {
			theDrobBox.selectByIndex(index);
			}
			catch (Exception e) {
				throw new TcXmlException("fail select by index ", e);
			}
		}
		
	}

	private void evalJSOnObject(TestObject to, PlayingContext ctx) throws TcXmlException {
		WebElement finded = tcXmlController.identifyElement(to,ctx.getCurrentExecutionContext());
		ArgModel code = argumentMap.get("Code");
		tcXmlController.evalJavascriptOnObject(code.getValue(),finded,ctx.getCurrentExecutionContext());
		
		
		
	}

	private void verify(TestObject to, PlayingContext ctx) {
		// TODO Auto-generated method stub
		
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
		
		 ArgModel cleararg = argumentMap.get("Clear");
		 boolean clear = new Boolean(cleararg.getValue());
			 
			 
	/// if argument is in js it should be evaluated before
			 if(isj) {
				 
			txt =(String) tcXmlController.evaluateJS(txt,ctx.getCurrentExecutionContext());	 
			 }
			 
			 tcXmlController.typeText(ctx.getCurrentExecutionContext(),to, txt, 20,clear);
		
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
		
		String location =  tcXmlController.evaluateJsArgument(locationArg,ctx.getCurrentExecutionContext());	
		
		
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
		
		

		
		WebElement finded = tcXmlController.identifyElement(to,ctx.getCurrentExecutionContext());
		tcXmlController.highlight(finded);
			final Actions actions = new Actions(tcXmlController.getDriver());
			actions.moveToElement(tcXmlController.identifyElement(to, ctx.getCurrentExecutionContext())).click().perform();
		 

		
	}
	
	private void waitOn(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		String identmethodstr = to.getIdents().getActive();
		
		IdentificationMethod identMetho = IdentificationMethod.get(identmethodstr);
		
		switch(identMetho) {
		
		case JAVASCRIPT: 
			throw new TcXmlException("wait on with identification by javascript is not implemented", new IllegalArgumentException(identmethodstr));

		
		case XPATH : 
		
		String xpath = tcXmlController.getXpathForTestObject(to);
		long TIMEOUTWAIT = 20;
		try {
		WebDriverWait w = new WebDriverWait(tcXmlController.getDriver(), TIMEOUTWAIT );
		By locator = By.xpath(xpath);
		w.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0)   );
		//tcXmlController.highLightXpath(xpath);
		}catch (TimeoutException e) {
			throw new TcXmlException("timeout " + TIMEOUTWAIT, e);
		}

			
			break;
		
		
		}	
		
		
	}

}
