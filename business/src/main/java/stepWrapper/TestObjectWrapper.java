package stepWrapper;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

import tcxml.core.FoundedElement;
import tcxml.core.IdentificationMethod;
import tcxml.core.LocatedByJavascript;
import tcxml.core.LocatedByXpath;
import tcxml.core.PlayingContext;
import tcxml.core.StepStat;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

import tcxml.model.ArgModel;
import tcxml.model.Ident;
import tcxml.model.ListArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class TestObjectWrapper extends AbstractStepWrapper {
	
	
	private StepStat stat;
	
	private TestObject theTestObject;
	private boolean isBrowserStep ;

	private Logger log;

	

	public TestObjectWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		log = Logger.getLogger( TestObjectWrapper.class.getName());
    	log.setLevel(Level.ALL);
		
		stat = new StepStat();
		if (!controller.isBrowserStep(step)) { // testobject is not browser
		theTestObject = step.getTestObject() == null ? null  : controller.getTestObjectById(step.getTestObject(), library) ; 
		isBrowserStep = false ;
		
	} else {
		isBrowserStep = true ;
		theTestObject = null;
		
	}
	}
	
/***
 * 
 * 	
 * @return if the testobject act on the browser or on an htmlelement
 */
	
public boolean isBrowserStep() {
	
	return isBrowserStep;
	
}


public TestObject getTheTestObject() {
	return theTestObject;
}

public void setTheTestObject(TestObject newTestObject) {
	this.theTestObject = newTestObject;
	step.setTestObject(newTestObject.getTestObjId());
}

public ArrayList<String> getIdentificationsMethods() {
	
	ArrayList<String> li = new ArrayList<String>();
	li.add("XPath");
	li.add("JavaScript");	
	
	return li;
	
}


public String getName() {
	
	if( theTestObject == null) {
		
		return "please select an object in the browser" ;
		
	}
	
	
	String name = theTestObject.getAutoName() == null ? theTestObject.getManualName() : theTestObject.getAutoName() ;
	
	name = (name == null || name.isEmpty() )? theTestObject.getFallbackName() : name ;
	
	return name;
	
}
	
	

	@Override
	public String getTitle() throws TcXmlException {
		String ret;
		
		if (!isBrowserStep ) { // testobject is not browser

			
				
				 ret = formatTitle(step.getIndex(), step.getAction() + " " + getShortArgumentForAction(step.getAction()) + " on " + getName() );
				 
			
			
			
			
			
			
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
		case "Upload":addUploadArgument(ret);break;
		case "Evaluate JavaScript":addEvalJavascriptArgument(ret);break;
		case "Wait":addWaitArgument(ret);break;
		case "Verify" : addVerifyArgument(ret);break;
		case "Select":addSelectArgument(ret);break;
		case "Wait for Property" : addWaitForPropertyArgument(ret);break;
		case "Go Forward":addGoBackArgument(ret);break;
		case "Go Back":addGoBackArgument(ret);break;
		case "Reload":addReloadArgument(ret);break;
		case "Activate Tab":addActivateTabArgument(ret);break;
		
		default: throw new TcXmlException("no default value for step testobject action = " + step.getAction() + " id= " +step.getStepId()  , new IllegalArgumentException(step.getAction())) ; 
		
		}
		
		return ret;
	}
	
	private void addActivateTabArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Ordinal");
mo.setValue("1");
ret.add(mo);
		
	}

	private void addUploadArgument(ArrayList<ArgModel> ret) {
		ArgModel mo;
		mo = new ArgModel("Path");
mo.setValue("");
ret.add(mo);
		
	}

	private void addReloadArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Location");
		lo.setValue("");
		ret.add(lo);
		
	}

	private void addGoBackArgument(ArrayList<ArgModel> ret) {
		ArgModel lo = new ArgModel("Count");
		lo.setValue("1");
		ret.add(lo);
		
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
				mo = TcxmlUtils.getTruefalseListArgModel("Alt Key", "false");	

		ret.add(mo);
		
		mo = TcxmlUtils.getTruefalseListArgModel("Ctrl Key", "false");

ret.add(mo);

mo =  TcxmlUtils.getTruefalseListArgModel("Shift Key", "false");

ret.add(mo);
ArrayList<String>val = new ArrayList<String>();
val.add("Left");
val.add("Middle");
val.add("Right");

mo = new ListArgModel("Button", val);
mo.setValue("Left");

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
	
		
		
		if(isBrowserStep) { // test object is the browser
			
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
		case "Go Back":ret = goback(ctx);break;
		case "Go Forward":ret = goforward(ctx);break;
		case "Reload":ret=reload(ctx);break;
		case "Activate Tab":ret=activateTab(ctx);break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		return ret;
		
	}
	
	
	private PlayingContext activateTab(PlayingContext ctx) throws TcXmlException {
		WebDriver dr = controller.getDriver();
		controller.ensureDriver();
		ArgModel tabNum = argumentMap.get("Ordinal") ;
		
		String evaltabNum  =  controller.evaluateJsArgument(tabNum,ctx.getCurrentExecutionContext());	
		log.info(" activate Tab  " +evaltabNum );
		Set<String> allTabs = dr.getWindowHandles();
		String[] tabArray = allTabs.toArray(new String[allTabs.size()]);
		try {
		int tabindex = Integer.parseInt(evaltabNum);
		dr.switchTo().window(tabArray[tabindex -1 ]);
		}catch (Exception e) {
			
			throw new TcXmlException("fail to activate tab with  " + evaltabNum, e);
		}
		return ctx;
	}

	private PlayingContext reload(PlayingContext ctx) throws TcXmlException  {
		WebDriver dr = controller.getDriver();
		controller.ensureDriver();
		ArgModel location = argumentMap.get("Location") ;
		
		String evalloc  =  controller.evaluateJsArgument(location,ctx.getCurrentExecutionContext());	
		if(evalloc.isEmpty()) {
			dr.navigate().refresh();
			}else {
				
				dr.navigate().refresh();
				log.info(" location in  browser reload is not implemented yet " +evalloc );
			}
		
		log.info(" browser reload location " +evalloc );
		
		
		return ctx;
	}

	private PlayingContext goforward(PlayingContext ctx) throws TcXmlException {
		WebDriver dr = controller.getDriver();
		controller.ensureDriver();
		ArgModel nbpages = argumentMap.get("Count") ;
		
		String nb =  controller.evaluateJsArgument(nbpages,ctx.getCurrentExecutionContext());	
		if(nb.equals("Default (1)")) {
			nb="1";
			}
		((JavascriptExecutor) dr).executeScript("window.history.go(+"+ nb + ")");
		log.info(" browser go forward of " +nb + "pages");
		
		
		return ctx;
	}

	private PlayingContext goback(PlayingContext ctx) throws TcXmlException {
		WebDriver dr = controller.getDriver();
		controller.ensureDriver();
		ArgModel nbpages = argumentMap.get("Count") ;
		
		String nb =  controller.evaluateJsArgument(nbpages,ctx.getCurrentExecutionContext());
		if(nb.equals("Default (1)")) {
		nb="1";
		}
		
		((JavascriptExecutor) dr).executeScript("window.history.go(-"+ nb + ")");
		log.info(" browser go back of " +nb + "pages");
		
		
		return ctx;
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
	        
	        try {
	        wait.until(pageLoadCondition);
	        }
	        catch(Exception e) {
	        	
	        controller.getLog().warning(e.getMessage());
	        
	        e.printStackTrace();
	        
	        }
	        stat.setTimeComplete(System.currentTimeMillis());
	        return ctx;
	     
		 
		 
		 
		
	}
	
	
	public List<String> getAvailableAction(){
		
		ArrayList<String> ret = new ArrayList<String>();
	if( ! isBrowserStep())	{//object action
		ret.add("Type");
		ret.add("Click");
		ret.add("Wait");
		ret.add("dbl click");
		ret.add("Verify");
		ret.add("Select");
		ret.add("Evaluate JavaScript");
		ret.add("Wait for Property");
		
	} else { //browser action
		ret.add("Navigate");
		ret.add("Go Back");
		ret.add("Go Forward");
	
		
	}
		

		
	return ret;
		
		
		
	}
	
	public String getAction() {
		
		return step.getAction();
		
		
	}
	
	
	private PlayingContext runTestObjectStep( PlayingContext ctx) throws TcXmlException {

		

	
		switch (step.getAction()) {
			
		case "Type": typeText( ctx);
		break;
		case "Click":click(ctx);
		break;
		case "Wait":waitOn( ctx);
	break;
		case "Verify":verify( ctx);break;
		
		case "Evaluate JavaScript":evalJSOnObject(ctx);break;
		case "Select":select(ctx);break;
		case "Set" : doSet(ctx);break;
		case "Upload": doUpload(ctx);break;
		case "Wait for Property":waitForProperty(ctx);break;
		
		default: notImplemented();
		}
		
	return ctx;	
		
	}
	
	
	private void doUpload(PlayingContext ctx) throws TcXmlException {
		ArgModel thepath = argumentMap.get("Path");
		FoundedElement theelement = controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext());
		String thefile = controller.evaluateJsArgument(thepath, ctx.getCurrentExecutionContext());
		File file = new File(thefile);	
		if( !file.exists()) {
			
		throw new TcXmlException("fail to upload file from location "+ thefile, new IllegalArgumentException(thefile))	;
			
		}
		controller.getLog().info("uploading file from " + thefile + " on element " + theelement.getElement().getTagName());
		
		theelement.getElement().sendKeys(thefile);
		
	}

	private void waitForProperty( PlayingContext ctx) throws TcXmlException {
		waitOn( ctx);
		
	}

	private void doSet( PlayingContext ctx) throws TcXmlException  {
		ArgModel thepath = argumentMap.get("Path");
		FoundedElement theelement = controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext());
		String thefile = controller.evaluateJsArgument(thepath, ctx.getCurrentExecutionContext());
		File file = new File(thefile);	
		if( !file.exists()) {
			
		throw new TcXmlException("fail to upload file from location "+ thefile, new IllegalArgumentException(thefile))	;
			
		}
		controller.getLog().info("uploading file from " + thefile + " on element " + theelement.getElement().getTagName());
		
		theelement.getElement().sendKeys(thefile);
		
		
				
				
		
	}
	
	
	private void select( PlayingContext ctx) throws TcXmlException {
		ArgModel thetext = argumentMap.get("Text");
		ArgModel theordinal = argumentMap.get("Ordinal");
		FoundedElement theelement = controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext());
		int index = 1;
		
		if(hasRole(theTestObject,"listbox")) {
			
			selectBySelect(theelement.getElement(), thetext, theordinal,ctx);
			
		}else if (hasRole(theTestObject,"radiogroup")) {
			
			selectByRadioGroup(theelement.getElement(), thetext, theordinal);
			
		}else {
			
			
			throw new TcXmlException("unsupported role for select action", new IllegalArgumentException());
		}
		
	
		
		
		
		
		
		
	}
	
	
	private void selectBySelect(WebElement theelement, ArgModel thetext, ArgModel theordinal, PlayingContext ctx) throws TcXmlException {
		Select theDrobBox = new Select(theelement);
		int index;
		if(thetext != null  && !thetext.getValue().isEmpty()) {
		
			
			try {
				
				String dynval =	controller.evaluateJsArgument(thetext, ctx.getCurrentExecutionContext());		
		theDrobBox.selectByVisibleText(dynval);	
			}
			catch (Exception e) {
				throw new TcXmlException("fail select by visible text ", e);
				
			
			}
		}else {// as text field is null use ordinal 
			
			
	String dynval =	controller.evaluateJsArgument(theordinal, ctx.getCurrentExecutionContext());
		try {
 index = Integer.parseInt(dynval)	;
 
 
 
 
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
	
	
	private void verify( PlayingContext ctx) {
		// TODO Auto-generated method stub
		
	}
	
	private void evalJSOnObject( PlayingContext ctx) throws TcXmlException {
		FoundedElement finded = controller.identifyElement(theTestObject,ctx.getCurrentExecutionContext());
		ArgModel code = argumentMap.get("Code");
		controller.evalJavascriptOnObject(code.getValue(),finded.getElement(),ctx.getCurrentExecutionContext());
		
		
		
	}
	
	private void waitOn( PlayingContext ctx) throws TcXmlException {
		
		String identmethodstr = theTestObject.getIdents().getActive();
		
		IdentificationMethod identMetho = IdentificationMethod.get(identmethodstr);
		
		String identjs = this.getIdentForTestObject( identmethodstr);
		long TIMEOUTWAIT = 30;
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
			ExpectedCondition<Boolean> loxp = new LocatedByXpath(controller, ctx.getCurrentExecutionContext(), this.theTestObject);
		//String xpath = controller.getXpathForTestObject(theTestObject);
		
		try {
		
		//By locator = By.xpath(xpath);
			
			
			w.until(loxp);
		//tcXmlController.highLightXpath(xpath);
		}catch (TimeoutException e) {
			throw new TcXmlException("timeout " + TIMEOUTWAIT, e);
		}

			
			break;
		
		
		}	
		
		
	}
	
	
	private void click( PlayingContext ctx) throws TcXmlException {
		
		String button ="left";
		
	ArgModel buttonarg = argumentMap.get("Button");
		
		if(buttonarg != null) {
			 button = buttonarg.getValue();
	}
	
		 switch (button) {
		case "Left":
		case "left":	
		case "Default (\"Left\")":
			clickLeft(ctx);
			break;
		case "right":
		case "Right":
			clickRight(ctx);
			break;

		default:notImplemented();
			break;
		}
		
	}

	private void clickRight( PlayingContext ctx) throws TcXmlException {
		notImplemented();
		
	}

	private void clickLeft( PlayingContext ctx) throws TcXmlException {
		doclick(ctx);
		
		
	}

	private void notImplemented() throws TcXmlException {
		throw new TcXmlException("action not implemented:" + step.getAction()+"---", new IllegalStateException());
		
	}
	
	
public void doclick( PlayingContext ctx) throws TcXmlException {
		
		

		
		FoundedElement finded = controller.identifyElement(theTestObject,ctx.getCurrentExecutionContext());
		controller.switchToCorrectFrame(finded);
		controller.highlight(finded);
			final Actions actions = new Actions(controller.getDriver());
		//	actions.moveToElement(controller.identifyElement(to, ctx.getCurrentExecutionContext())).click().perform();
			
		
			actions.moveToElement(controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext()).getElement()).perform();
			actions.moveToElement(controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext()).getElement()).click().perform();
	
			
			controller.switchToDefaultFrame();
	}

	
	private void typeText( PlayingContext ctx) throws TcXmlException {
		
		ArgModel txtarg = argumentMap.get("Value");
		String txt = txtarg.getValue();
		 boolean isj = txtarg.getIsJavascript();
		 boolean isparam = txtarg.getIsParam();
		
		 ArgModel cleararg = argumentMap.get("Clear");
		 boolean clear = new Boolean(cleararg.getValue());
			
		 final Actions actions = new Actions(controller.getDriver());
		 actions.moveToElement(controller.identifyElement(theTestObject, ctx.getCurrentExecutionContext()).getElement()).perform(); 
	/// if argument is in js it should be evaluated before
			 if(isj || isparam) {
				 
			//txt =(String) controller.evaluateJS(txt,ctx.getCurrentExecutionContext());	
				 txt =(String) controller.evaluateJsArgument(txtarg,ctx.getCurrentExecutionContext());	
			
			
			 }
			 
			 controller.typeText(ctx.getCurrentExecutionContext(),theTestObject, txt, 20,clear);
		
	}
	
	
	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		String ret;
		if(isBrowserStep) { // test object is the browser
			
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
		case "Go Back":ret = exportgoback();break;
		case "Go Forward":ret = exportgoForward();break;


		default:throw new TcXmlException("not implemented", new IllegalStateException());
		}
		return ret;
	}
	
	private String exportgoForward() {
		return	genericExport("await TC.goForward");
		
	}

	private String exportgoback() {
		return	genericExport("await TC.goBack");
	}

	private String exportnavigate() {
		
		
	return	genericExport("await TC.navigate");
		

	}
	
	
	private String genericExportTestObjectStep(String targetFuncName ) throws TcXmlException {
		 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = controller.generateJSobject(li);	
			
			
		
			String ret = TcxmlUtils.formatJavascriptFunction(
					targetFuncName,
						argjs,
						controller.generateJsTestObject(theTestObject)
					
						);
			
			
			return ret;
	}
	
	
	
	
	
	private String exportTestObjectStep() throws TcXmlException {
		String ret = null;

		switch (step.getAction()) {
			
		case "Type": ret = genericExportTestObjectStep("await TC.type"); 
		break;
		case "Click":ret = genericExportTestObjectStep("await TC.click");
		break;
		case "Wait":ret = genericExportTestObjectStep("await TC.waitOn");
	break;
		case "Verify": ret = genericExportTestObjectStep("await TC.verify"); break;
		
		case "Evaluate JavaScript":ret = genericExportTestObjectStep("await TC.evaljsOnObject");break;
		case "Select":ret = genericExportTestObjectStep("await TC.select");break;
		case "Set" : ret = genericExportTestObjectStep("await TC.set");break;
		
		default: ret = exportnotImplemented();
		}
		return ret;
	}
	
	private String exportnotImplemented() {
		// TODO Auto-generated method stub
		return " step not implemented " + step.getAction();
	}
	
	
	public String getActiveIdentification() {
		
		if(isBrowserStep) throw new UnsupportedOperationException(" browser step has no identifgication method");
		
	return theTestObject.getIdents().getActive();	
		
	}
	/**
	 * 
	 * 
	 * @param idmethod
	 * @return the identification string fot thetestobject ( can be an xpath query, a javascript snippet according the identification method)
	 * @throws TcXmlException
	 */
	
	public String getIdentForTestObject(  String idmethod) throws TcXmlException {
		String ret =null;		
		if( idmethod.equals("Electors")) {
		throw new TcXmlException("unsupported identification method", new IllegalArgumentException("Electors"))	;			
		}
		BoundList<Ident> identificators = theTestObject.getIdents().getIdent();
		for (Ident ident : identificators) {
			if(ident.getType().equals(idmethod)){
				
				String json= ident.getValue();
				JsonObject arg =controller.readJsonObject(json) ;
				arg = arg.getJsonObject("implData");
				ret =arg.getJsonString("value").getString();
				//ret=StringEscapeUtils.unescapeJavaScript(ret);				
			}			
		}
	return ret;
	}
	
	
	/**
	 *  set the identification string to the identification method
	 * 
	 * @param idmethod
	 * @param ident
	 * @throws TcXmlException
	 */
public void setIdentForTestObject(  String idmethod, String identstring) throws TcXmlException {
	if(identstring == null ) {
		identstring="";
	}
	boolean foundedIdent =false;
	BoundList<Ident> identificators = theTestObject.getIdents().getIdent();
	for (Ident ident : identificators) {
		if(ident.getType().equals(idmethod)){
			 foundedIdent = true;
			String json= ident.getValue();
			JsonObject argjson =controller.readJsonObject(json) ;
			 JsonObject implDatajson = argjson.getJsonObject("implData");
		
			
			JsonObjectBuilder newArg  = Json.createObjectBuilder(argjson);
			JsonObjectBuilder newimpl  = Json.createObjectBuilder(implDatajson);
			
			newimpl.remove("value");			
			newimpl.add("value", identstring);
			newArg.remove("implData");
			newArg.add("implData", newimpl);
			
			 StringWriter writer = new StringWriter();
		     JsonWriter jwriter = Json.createWriter(writer);
		    jwriter.writeObject(newArg.build());
			
			String newIdentValue = writer.toString();
			
			 
				 writer = new StringWriter();
			     jwriter = Json.createWriter(writer);
			     jwriter.writeObject(argjson);
			 String oldIdentValue = writer.toString();
			 
			controller.getLog().fine("update identifification " + idmethod + " new value is :"+newIdentValue);
			 
			controller.getLog().fine( " old  value was :"+oldIdentValue);
						
		ident.setValue(newIdentValue);					
		}
}
	
if(!foundedIdent && (! identstring.isEmpty() ) )	{ // ident was not previously existing, create it
Ident newident = new Ident() ;
newident.setType(idmethod);
JsonObjectBuilder newValJson  = Json.createObjectBuilder();
JsonObjectBuilder newImpl  = Json.createObjectBuilder();
newImpl.add("value", identstring);
newValJson.add("implData", newImpl);

newident.setValue(newValJson.toString());
theTestObject.getIdents().getIdent().add(newident);
	
}	

}



public void setActiveIdentMehod(String idmethod) {
	
	theTestObject.getIdents().setActive(idmethod);	
	
}

/***
 *  return the printable argument for the step
 * 
 * 
 * @param action
 * @return
 */

private String getShortArgumentForAction(String action) {
	String ret ="";
	
	switch (action) {
	case "Type": ret = getShortTypeArgument();break;
	case "Select": ret = getShortSelectArgument(); break;
	case "Set": ret = getShortSetArgument();break;
	
	
	
	}
	
	
	
	
	return ret;
}

private String getShortSetArgument() {
	ArgModel thepath = argumentMap.get("Path");
	return thepath.getValue();
}

/**
 *  printable agrument for select step
 * 
 * @return
 */
private String getShortSelectArgument() {
	String ret ="";
	String txt="";
	ArgModel thearg = getSelectArgument();
	if (thearg.getName().equals("Text")) { // forrmat text
		
		txt = thearg.getValue();
		int size = txt.length();
		 ret=txt;
		if(size > 30) {
			StringBuffer sb = new StringBuffer();
			sb.append(txt.substring(0, 15));
			sb.append(".......");
			sb.append(txt.substring( size -15 , size )) ;
			ret = sb.toString();
		}
		
		
		
	}else { /// form mat ordinal
		txt = thearg.getValue();	
	StringBuffer sb = new StringBuffer();
		
	if(txt.equals("0"))	{
		sb.append(" randomly ");
	} else {
		sb.append("#").append(txt);
		ret = sb.toString();
	}
		
		
		
		
	}
	
	
	return ret;
}

/***
 * 
 * 
 * 
 * @return either Text or Ordinal argument for select 
 */

private ArgModel getSelectArgument() {
	ArgModel thetext = argumentMap.get("Text");
	ArgModel theordinal = argumentMap.get("Ordinal");
	if(thetext != null  && !thetext.getValue().isEmpty()) {	
	
	return thetext;	
} else {
	
	return theordinal;
}
	
}




		
		





/***
 * 
 *  gives the text argument of the testobject , split to be shown in the GUI usefule for Type action
 * 
 * 
 * @return
 */
private String getShortTypeArgument() {
	ArgModel txtarg = argumentMap.get("Value");
	String txt = txtarg.getValue();

	
	
	
	
	int size = txt.length();
	String ret=txt;
	if(size > 30) {
		StringBuffer sb = new StringBuffer();
		sb.append(txt.substring(0, 15));
		sb.append(".......");
		sb.append(txt.substring( size -15 , size )) ;
		ret = sb.toString();
	}
	




	return ret;
}





}
