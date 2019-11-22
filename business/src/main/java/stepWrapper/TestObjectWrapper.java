package stepWrapper;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.IdentificationMethod;
import tcxml.core.LocatedByJavascript;
import tcxml.core.PlayingContext;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.TestObjectRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class TestObjectWrapper extends AbstractStepWrapper {
	
	
	private StepStat stat;

	public TestObjectWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		stat = new StepStat();
	}

	@Override
	public String getTitle() throws TcXmlException {
		String ret;
		TestObject to = null;
		if (!controller.isBrowserStep(step)) { // testobject is not browser

				to = controller.getTestObjectById(step.getTestObject(), library);

			String name = to.getAutoName() == null ? to.getManualName() : to.getAutoName() ;
			
			name = name == null ? to.getFallbackName() : name ;
			
			
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
		case "Mouse Over":addClickArgument(ret);break;
		case "Mouse Up":addClickArgument(ret);break;
		case "Mouse Down":addClickArgument(ret);break;
		case "Set":addSetArgument(ret);break;
		case "Evaluate JavaScript":addEvalJavascriptArgument(ret);break;
		case "Wait":addWaitArgument(ret);break;
		case "Verify" : addVerifyArgument(ret);break;
		case "Select":addSelectArgument(ret);break;
		case "Wait for Property" : addWaitForPropertyArgument(ret);break;
		
		default: throw new TcXmlException("no default value for step testobject action = " + step.getAction() + " id= " +step.getStepId()  , new IllegalArgumentException(step.getAction())) ; 
		
		}
		
		return ret;
	}
	
	private void addWaitForPropertyArgument(ArrayList<ArgModel> ret) {
		ArgModel val = new ArgModel("Value");
		val.setValue("");
		ret.add(val);
		
		
		ArgModel property = new ArgModel("Property");
		property.setValue("Visible Text");
		ret.add(property);
		
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
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException  {
		PlayingContext nctx ;
	
		
		
		if(controller.isBrowserStep(step)) { // test object is the browser
			
		  nctx = runBrowserStep(ctx);
		
			
		}else {
			
			
			 nctx =	 runTestObjectStep(ctx);	
			
			
		}
		
	return nctx;
		
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

		
		
		
		WebDriver dr = controller.getDriver();
		controller.ensureDriver();
		
		/*
		 * JsonObject locobj = arg.getJsonObject("Location"); String location =
		 * locobj.getJsonString("value").getString(); boolean isj =
		 * locobj.getBoolean("evalJavaScript");
		 */
		 
		ArgModel locationArg = argumentMap.get("Location") ;
		
		String location =  controller.evaluateJsArgument(locationArg,ctx.getCurrentExecutionContext());	
		
		
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
	
	private PlayingContext runTestObjectStep( PlayingContext ctx) throws TcXmlException {
		TestObject to;
		if (library == null) {
			to = controller.getTestObjectById(step.getTestObject());

		} else {
			to = controller.getTestObjectById(step.getTestObject(), library);

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
		case "Wait for Property":waitForProperty(to,ctx);break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}
	
	
	private void waitForProperty(TestObject to, PlayingContext ctx) throws TcXmlException {
		waitOn(to, ctx);
		
	}

	private void doSet(TestObject to, PlayingContext ctx) throws TcXmlException  {
		ArgModel thepath = argumentMap.get("Path");
		WebElement theelement = controller.identifyElement(to, ctx.getCurrentExecutionContext());
		String thefile = controller.evaluateJsArgument(thepath, ctx.getCurrentExecutionContext());
		File file = new File(thefile);	
		if( !file.exists()) {
			
		throw new TcXmlException("fail to upload file from location "+ thefile, new IllegalArgumentException(thefile))	;
			
		}
		controller.getLog().info("uploading file from " + thefile + " on element " + theelement.getTagName());
		
		theelement.sendKeys(thefile);
		
		
				
				
		
	}
	
	
	private void select(TestObject to, PlayingContext ctx) throws TcXmlException {
		ArgModel thetext = argumentMap.get("Text");
		ArgModel theordinal = argumentMap.get("Ordinal");
		WebElement theelement = controller.identifyElement(to, ctx.getCurrentExecutionContext());
		int index = 1;
		
		if(hasRole(to,"listbox")) {
			
			selectBySelect(theelement, thetext, theordinal);
			
		}else if (hasRole(to,"radiogroup")) {
			
			selectByRadioGroup(theelement, thetext, theordinal);
			
		}else {
			
			
			throw new TcXmlException("unsupported role for select action", new IllegalArgumentException());
		}
		
	
		
		
		
		
		
		
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
	
	
	private void verify(TestObject to, PlayingContext ctx) {
		// TODO Auto-generated method stub
		
	}
	
	private void evalJSOnObject(TestObject to, PlayingContext ctx) throws TcXmlException {
		WebElement finded = controller.identifyElement(to,ctx.getCurrentExecutionContext());
		ArgModel code = argumentMap.get("Code");
		controller.evalJavascriptOnObject(code.getValue(),finded,ctx.getCurrentExecutionContext());
		
		
		
	}
	
	private void waitOn(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		String identmethodstr = to.getIdents().getActive();
		
		IdentificationMethod identMetho = IdentificationMethod.get(identmethodstr);
		
		String identjs = controller.getIdentForTestObject(to, identmethodstr);
		long TIMEOUTWAIT = 20;
		WebDriverWait w = new WebDriverWait(controller.getDriver(), TIMEOUTWAIT );
		switch(identMetho) {
		
		case JAVASCRIPT:			
			ExpectedCondition<Boolean> lo = new LocatedByJavascript(identjs, controller, ctx.getCurrentExecutionContext()) ;
			try {
			w.until(lo);
			
		}catch (TimeoutException e) {
			throw new TcXmlException("timeout " + TIMEOUTWAIT, e);
		}
			break;
		
		case XPATH : 
		
		String xpath = controller.getXpathForTestObject(to);
		
		try {
		
		By locator = By.xpath(xpath);
		w.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0)   );
		//tcXmlController.highLightXpath(xpath);
		}catch (TimeoutException e) {
			throw new TcXmlException("timeout " + TIMEOUTWAIT, e);
		}

			
			break;
		
		
		}	
		
		
	}
	
	
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
	
	
public void doclick(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		

		
		WebElement finded = controller.identifyElement(to,ctx.getCurrentExecutionContext());
		controller.highlight(finded);
			final Actions actions = new Actions(controller.getDriver());
			actions.moveToElement(controller.identifyElement(to, ctx.getCurrentExecutionContext())).click().perform();
		 

		
	}

	
	private void typeText(TestObject to, PlayingContext ctx) throws TcXmlException {
		
		ArgModel txtarg = argumentMap.get("Value");
		String txt = txtarg.getValue();
		 boolean isj = txtarg.getIsJavascript();		
		
		 ArgModel cleararg = argumentMap.get("Clear");
		 boolean clear = new Boolean(cleararg.getValue());
			 
			 
	/// if argument is in js it should be evaluated before
			 if(isj) {
				 
			txt =(String) controller.evaluateJS(txt,ctx.getCurrentExecutionContext());	 
			 }
			 
			 controller.typeText(ctx.getCurrentExecutionContext(),to, txt, 20,clear);
		
	}
	
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		String ret;
		if(controller.isBrowserStep(step)) { // test object is the browser
			
			  ret = exportBrowserStep();
			
				
			}else {
				
				
				 ret =	 exportTestObjectStep();	
				
				
			}
		pw.println(ret);
		
		
	}
	
	private String exportBrowserStep() throws TcXmlException {
		String ret;
		switch (step.getAction()) {
		case "Navigate":ret = exportnavigate();break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		return ret;
	}
	
	private String exportnavigate() {
		
		
	return	genericExport("await TC.navigate");
		

	}
	
	private String exportTestObjectStep() throws TcXmlException {
		String ret = null;
		TestObject to;
		if (library == null) {
			to = controller.getTestObjectById(step.getTestObject());

		} else {
			to = controller.getTestObjectById(step.getTestObject(), library);

		}
		

	
		switch (step.getAction()) {
			
		case "Type": ret = genericExport("await TC.type",to); 
		break;
		case "Click":ret = genericExport("await TC.click",to);
		break;
		case "Wait":ret = genericExport("await TC.waitOn",to);
	break;
		case "Verify": ret = genericExport("await TC.verify",to); break;
		
		case "Evaluate JavaScript":ret = genericExport("await TC.evaljsOnObject",to);break;
		case "Select":ret = genericExport("await TC.select",to);break;
		case "Set" : ret = genericExport("await TC.set",to);break;
		
		default: ret = exportnotImplemented();
		}
		return ret;
	}
	
	private String exportnotImplemented() {
		// TODO Auto-generated method stub
		return " step not implemented " + step.getAction();
	}
	

}
