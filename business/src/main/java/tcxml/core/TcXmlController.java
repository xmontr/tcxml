package tcxml.core;

import java.io.File;
import org.apache.commons.configuration.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.parameter.StepParameter;
import tcxml.model.Ident;
import tcxml.model.ObjectFactory;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxml.model.TruScript;

public class TcXmlController {
	
	
	 public static final String ACTION_VERFIFY = "Verfify";

	    public static final String ACTION_STOP = "Stop";

	    public static final String ACTION_SCROLL = "Scroll";

	    public static final String ACTION_RESIZE = "Resize";

	    public static final String ACTION_RELOAD = "Reload";

	    public static final String ACTION_NAVIGATE = "Navigate";

	    public static final String ACTION_GO_FORWARD = "Go Forward";

	    public static final String ACTION_GO_BACK = "Go Back";

	    public static final String ACTION_GET_PROPERTY = "Get Property";

	    public static final String ACTION_DIALOG_PROMPT_PASSWORD = "Dialog - Prompt Password";

	    public static final String ACTION_DIALOG_PROMPT = "Dialog - Prompt";

	    public static final String ACTION_DIALOG_CONFIRM = "Dialog - Confirm";

	    public static final String ACTION_DIALOG_AUTHENTICATE = "Dialog - Authenticate";

	    public static final String ACTION_DIALOG_ALERT = "Dialog - Alert";

	    public static final String ACTION_CLOSE_TAB = "Close Tab";

	    public static final String ACTION_CLOSE_HTML_DIALOG = "Close Html Dialog";

	    public static final String ACTION_CLOSE = "Close";

	    public static final String ACTION_ADD_TAB = "Add Tab";

	    public static final String ACTION_ACTIVATE_TAB = "Activate Tab";

	    public static final String ACTION_ACTIVATE = "Activate";
	
	
   
	private String name;
    
    private File path ;
    
    
    private Map<String,StepParameter> parameters;
    
    
    private Map<String, TruLibrary> libraries ;
    
    
    private Logger log;
    
    public Map<String, Step> getActionMap() {
		return actionMap;
	}

	public void setActionMap(Map<String, Step> actionMap) {
		this.actionMap = actionMap;
	}

	/***
     *  java model for the xml truscript
     * 
     */
    private TruScript script;
    
    /***
     * 
     *  actions in the script
     * 
     */
    private Map<String, Step> actionMap;
    
    
    /***
     * 
     *  the running logic of the script
     * 
     */
    
    private Step runLogic;

	private WebDriver driver;
    
    public TcXmlController(String name){
    	
    	log = Logger.getLogger(TcXmlController.class.getName());
    	log.setLevel(Level.ALL);
    	
    actionMap = new HashMap<String, Step>();
    parameters = new HashMap<String,StepParameter>();
    
    libraries = new HashMap<String,TruLibrary>();
    	
    this.name = name;	
    }
    
 
    public BoundList<Step> getFunctionsForLib( String libname) throws TcXmlException {
    TruLibrary lib = libraries.get(libname);	
    if(lib == null) {
    	
    	throw new TcXmlException("library with name" + libname +" does not exist ", new IllegalStateException());
    }
    	
    BoundList<Step> ret = lib.getStep().getStep();	
    
    return ret;
    	
    }
    
    
    
    
    public List<String> getFunctionsNameForLib(String libname) throws TcXmlException {
    	ArrayList<String> ret = new ArrayList<String>();
    	
    	BoundList<Step> libs = getFunctionsForLib(libname);
    	for (Step step : libs) {
    		ret.add(step.getAction());
			
		}
    	return ret;
    	
    	
    	
    	
    }
    
    public boolean isBrowserStep(Step step) {
    	boolean ret = false;
    	
    	if(step.getTestObject().equals("testObj:{00000000-0000-0000-0000-000000000001}")) {
    		ret =true;
    		
    		
    	}
    	
    return ret;	
    	
    }
    
    
    
    
    public TestObject getTestObjectById( String id) throws TcXmlException {
    	BoundList<TestObject> li = script.getTestObjects().getTestObject();	
    	return getTestObjectById(id,li);
    }
    
    
    public TestObject getTestObjectById( String id, TruLibrary lib) throws TcXmlException {
    	if(lib == null) {
    		
    		throw new TcXmlException("cannot find testobject : library is null", new IllegalArgumentException());
    	}
    	BoundList<TestObject> li = lib.getTestObjects().getTestObject();
    	return getTestObjectById(id,li);
    }
    
    
    
    
    
    private TestObject getTestObjectById( String id, BoundList<TestObject> li) throws TcXmlException {
    	TestObject ret = null;
    	
    	for (TestObject testObject : li) {
    		if (testObject.getTestObjId().equals(id) ){
    			
    			ret=testObject;break;
    		}
			
		}
    	
    	if(ret == null) {
    		
			throw new TcXmlException( " testobject with id " + id +  " not found" ,new IllegalStateException()  );
    	}
    	
    	return ret;
    }


/***
 * laod the script and store action - runlogic - handlers
 * 
 * 
 * 
 * @param inputStream
 * @throws TcXmlException
 */


private void loadScript( InputStream inputStream) throws TcXmlException {
	try {	
    //1. We need to create JAXContext instance
    JAXBContext jaxbContext =  JAXBContext.newInstance(ObjectFactory.class);



    //2. Use JAXBContext instance to create the Unmarshaller.
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    //3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
    JAXBElement<TruScript> unmarshalledObject = 
        (JAXBElement<TruScript>)unmarshaller.unmarshal(
            inputStream);

    //4. Get the instance of the required JAXB Root Class from the JAXBElement.
    script = unmarshalledObject.getValue();
    
    
	jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
} catch (JAXBException e) {
String mess = "fail to load main script" ;
log.severe(mess);

throw ( new TcXmlException(mess, e));
}
	
log.info("loaded xcript - engine = " +script.getEngineVersion());	
	
parseMainXml();	
	
	
}


private  TruLibrary loadLibrary( InputStream inputStream) throws TcXmlException {
	
	TruLibrary library =null;
	
	try {	
	    //1. We need to create JAXContext instance
	    JAXBContext jaxbContext =  JAXBContext.newInstance(ObjectFactory.class);



	    //2. Use JAXBContext instance to create the Unmarshaller.
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

	    //3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
	    JAXBElement<TruLibrary> unmarshalledObject = 
	        (JAXBElement<TruLibrary>)unmarshaller.unmarshal(
	            inputStream);

	    //4. Get the instance of the required JAXB Root Class from the JAXBElement.
	    library = unmarshalledObject.getValue();
	    log.info("found library" + library.getStep().getAction());
	    
	    
		jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
	} catch (JAXBException e) {
	String mess = "fail to loadlibrary" ;
	log.severe(mess);

	throw ( new TcXmlException(mess, e));
	}
		
	log.info("loaded library - engine = " +library.getEngineVersion());	
		
	parseLibraryXml(library);	
	
	return library ;
	
	
	
	
}





public void loadFromDisk(String pathdir) throws TcXmlException {
	FileInputStream in = null;
	List<String> listing =null ;	
	if(!pathdir.endsWith("/")) {pathdir+= "/" ;}
	File file = new File(pathdir);
	
	
	if ( !file.isDirectory()) {
		
		throw new TcXmlException("invalid path " + pathdir + " for script: found file but  directory extected " ,  new IllegalArgumentException());
		
		
	}
	
//search main script 
	  listing = Arrays.asList(file.list()) ;
	 
	 if ( !listing.contains("default.xml")) {
		 
		 throw new TcXmlException("invalid  script: no default.xml founded in " + pathdir ,  new IllegalArgumentException());	 
	 }
//search lib folder 	 
 if ( !listing.contains("Libraries")) {
		 
	 throw new TcXmlException("invalid  script: no Libraries folder founded" ,  new IllegalArgumentException());	 
	 }
 
 //search parameter file
 if ( !listing.contains(name + ".prm")) {
	 
	 throw new TcXmlException("invalid  script: no parameter filr .prm founded in " + pathdir ,  new IllegalArgumentException());	 
 }	 
	
File mainscriptfile = new File(pathdir + "default.xml");
File parameterFile = new File(pathdir + name + ".prm");
File libdir = new File(pathdir + "Libraries");
try {
	 in = new FileInputStream(mainscriptfile);
} catch (FileNotFoundException e) {
	 throw new TcXmlException("invalid  path for script : no default.xml  founded in " + pathdir  ,  new FileNotFoundException());
}

loadScript(in);
// browse al libraries
File[] libfiles = libdir.listFiles();
for (File file2 : libfiles) {
	try {
		FileInputStream fin = new FileInputStream(file2);
		TruLibrary li = loadLibrary(fin);
		String libname = li.getStep().getAction();
		
		libraries.put(libname, li);
		log.info("adding library " + libname  + " to script");
		
		
		
		
	} catch (FileNotFoundException e) {
		 throw new TcXmlException("exception when loading library" ,  new FileNotFoundException());
	}
	

	
	
}

//load parameters
loadParameters(parameterFile);


	
}

/***
 *  parse the parameter file and populate the parameters map
 * 
 * @param parameterFile
 */

private void loadParameters(File parameterFile) {	
	
	HierarchicalINIConfiguration conf;
	SubnodeConfiguration se;
	try {				
		conf = new HierarchicalINIConfiguration(parameterFile);
		log.fine("analysing parameter file " + parameterFile.getPath());
		Set lisection = conf.getSections();
		Iterator it = lisection.iterator();
		while (it.hasNext()) {
			String  secname = (String ) it.next();	
			log.fine("found section" + secname);
			StepParameter para = StepParameter.buildParameter(conf,secname);
			parameters.put(para.getName(), para);

		
		
		}
		
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	


	
	
}



public Map<String, StepParameter> getParameters() {
	return parameters;
}

public File getPath() {
	return path;
}

public void setPath(File path) {
	this.path = path;
}

public Map<String, TruLibrary> getLibraries() {
	return libraries;
}

public void setLibraries(Map<String, TruLibrary> libraries) {
	this.libraries = libraries;
}

public TruScript getScript() {
	return script;
}

public void setScript(TruScript script) {
	this.script = script;
}

public Step getRunLogic() {
	return runLogic;
}

public void setRunLogic(Step runLogic) {
	this.runLogic = runLogic;
}

private void parseLibraryXml(TruLibrary library) throws TcXmlException {
	Step topelement = library.getStep();
	
	if(!topelement.getType().equals("library")) {
	
		throw new TcXmlException("invalid library  xml - first step type of truLibrary: founded" + topelement.getType() + " expected : library " ,  new IllegalStateException());
		
	}
	
	
}

private void parseMainXml() throws TcXmlException {
	 Step topStep = script.getStep();
	 String action = topStep.getAction();
	 if(!action.equals("TopStep")) {
		 
		throw new TcXmlException("invalid xml - first step of truScript: action expected:TopStep founded:"+action,  new IllegalStateException()); 
	 }
	 
	 
	 Step scriptStep = topStep.getStep().get(0); // script  
	String section = scriptStep.getSection();
	 if(!section.equals("Script")) {
		 
		throw new TcXmlException("invalid xml - first step of topstep : section expected :Script founded:"+section,  new IllegalStateException()); 
	 }
	
	 
	 Step libraryStep = topStep.getStep().get(1);   //  Libraries
	 section = libraryStep.getSection();
	 if(!section.equals("Libraries")) {
		 
		throw new TcXmlException("invalid xml - second  step of topstep : section expected expected:Libraries founded:"+section,  new IllegalStateException()); 
	 }
	 
// browse all step of the script
	 List<Step> childs = scriptStep.getStep();
	 Iterator<Step> it = childs.iterator();
	 while (it.hasNext()) {
		Step currentstep = (Step) it.next();
		action = currentstep.getAction();
		switch (action) {
		case "action":
		String actionName = currentstep.getActionName();	
		actionMap.put(actionName, currentstep);
			log.fine(" found action name=" +  actionName );
			
			break;
		case "default":
			String type = currentstep.getType(); 
			if(!type.equals("runLogic")) {
				log.warning("found unknown default expected type:runLogic found:" + type + ".SKIPPED");
				
			}else {
				
				runLogic = currentstep;
				log.fine(" found runlogic"  );
			}
			
			
			
			
			break;
		case "Handlers":
			log.fine(" found handler"  );
			
			break;

		default:
			log.warning("found unknown action:" + action + ".SKIPPED");
			break;
		}
		
	}
	 
	 
	 
	 
}
/****
 *  play only one step 
 * 
 * 
 * 
 * @param theStep
 * @throws TcXmlException
 */

public void playSingleStep( Step theStep, TruLibrary lib) throws TcXmlException{
	
	log.fine("start running step " +theStep.getStepId() + " type is " + theStep.getType());
	StepRunner ru = StepRunnerFactory.getRunner(theStep, this, lib);
	ru.runStep();
	
}


public String JSCodefromJSON( String json) throws TcXmlException {
	String ret = null;
	
	
	JsonValue codeobj = readJsonObject(json, "Code");

ret = ((JsonObject)codeobj).get("value").toString();
	


	return ret;
	
}

public JsonObject readJsonObject(String json , String rootKey) throws TcXmlException {
	

	JsonObject stru = readJsonObject(json);

if(!stru.containsKey(rootKey))	{
	
	throw new TcXmlException("key:" + rootKey + " not found in json:" + json, new IllegalStateException());
	
}
JsonValue codeobj = stru.get(rootKey);

if(!codeobj.getValueType().equals(ValueType.OBJECT)) {
	
	throw new TcXmlException(" invalid structure  of json: expected object but found  " + codeobj.getValueType(),  new IllegalStateException());
	
}
	return (JsonObject) codeobj;
}




public JsonObject readJsonObject(String json ) throws TcXmlException {
	
json=	StringEscapeUtils.unescapeHtml(json);
	Reader reader = new StringReader(json);
			
	JsonReader jr = Json.createReader(reader );
	JsonObject stru = jr.readObject();
	jr.close();
	


if(!stru.getValueType().equals(ValueType.OBJECT)) {
	
	throw new TcXmlException(" invalid structure  of json: expected object but found  " + stru.getValueType(),  new IllegalStateException());
	
}
	return  stru;
}



public String getXpathForTestObject( TestObject obj) throws TcXmlException {
	String ret =null;
	boolean isXpathDefined = false;
	
	BoundList<Ident> identificators = obj.getIdents().getIdent();
	
	
	for (Ident ident : identificators) {
		if(ident.getType().equals("XPath")){
			isXpathDefined =true;
			String json= ident.getValue();
			JsonObject arg = readJsonObject(json) ;
			arg = arg.getJsonObject("implData");
			ret =arg.getJsonString("value").getString();
			ret=StringEscapeUtils.unescapeJavaScript(ret);
			
		}
		
	}
	
	if(!isXpathDefined) {
		
		throw new TcXmlException("no xpath defined to identify testobject id:" + obj.getTestObjId(), new IllegalStateException());
		
		
		
	}
	
	return ret;
	
	
}

public List<String> getAvailableActionForStep( Step s , TruLibrary lib) throws TcXmlException{
	ArrayList<String> ret = new ArrayList<String>();
	if(isBrowserStep(s)) {
		
	ret.addAll(getActionsForBrowser()) ;
		

	} else {
		
		String tid = s.getTestObject();
		 TestObject to = getTestObjectById(tid, lib);
		ret.addAll(getAvailableActionForTestobject(to)) ;
		
	}

	return ret;
}




public List<String> getAvailableActionForTestobject(TestObject obj){
	ArrayList<String> ret = new ArrayList<String>();
	
	ret.add("click");
	ret.add("dbl click");
	
return ret;	
}



public List<String> getActionsForBrowser() {
	ArrayList<String> ret = new ArrayList<String>();
	
	ret.add(ACTION_ACTIVATE);
	ret.add(ACTION_ACTIVATE_TAB);
	ret.add(ACTION_ADD_TAB);
	ret.add(ACTION_CLOSE);
	ret.add(ACTION_CLOSE_HTML_DIALOG);
	ret.add(ACTION_CLOSE_TAB);
	ret.add(ACTION_DIALOG_ALERT);
	ret.add(ACTION_DIALOG_AUTHENTICATE);
	ret.add(ACTION_DIALOG_CONFIRM);
	ret.add(ACTION_DIALOG_PROMPT);
	ret.add(ACTION_DIALOG_PROMPT_PASSWORD);
	ret.add(ACTION_GET_PROPERTY);
	ret.add(ACTION_GO_BACK);
	ret.add(ACTION_GO_FORWARD);
	ret.add(ACTION_NAVIGATE);
	ret.add(ACTION_RELOAD);
	ret.add(ACTION_RESIZE);
	ret.add(ACTION_SCROLL);
	ret.add(ACTION_STOP);
	ret.add(ACTION_VERFIFY);
	
	
return ret;	
	
	



	
}

public Object evaluateJS(String code) throws TcXmlException {
	
	   ScriptEngineManager m = new ScriptEngineManager();
	   ScriptEngine engine = m.getEngineByName("nashorn");
	   ScriptContext context = new SimpleScriptContext();
	   
	   Object api = new LrAPI(this);
	   
	   context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
	   context.setAttribute("LR", api, ScriptContext.ENGINE_SCOPE);
	try {
		Object ret = engine.eval(code, context);
		return ret;
	} catch (ScriptException e) {
		throw new TcXmlException("fail to evaluate js code ", e);
	
	}
	
	
	
	
}


public StepParameter getParameterByName ( String name) throws TcXmlException  {
	
	StepParameter ret = this.parameters.get(name) ;
	if(ret== null) {
		
		throw new TcXmlException("unknown parameter "+name, new IllegalArgumentException("name"));
	}
	
	return ret;
	
	
}


public void openBrowser (String type, String driverPath) throws TcXmlException {
	 String extpath = getClass().getClassLoader().getResource("jqueryHighlighter.crx").getFile();
	File extfile = new File(extpath);

	// only chrome at this point
	System.setProperty("webdriver.chrome.driver", "C:/bin/selenium/driver/2.33/chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities caps = DesiredCapabilities.chrome();
	caps.setCapability(ChromeOptions.CAPABILITY, options);
	
	options.addArguments("disable-infobars");
	options.addArguments("start-maximized");
	 File pathtohighlighter = new File("jqueryHighlighter.crx");
	 options.addExtensions(extfile);

	options.setExperimentalOption("useAutomationExtension", false);
	driver = new ChromeDriver(options);
	

}	
	
	public void highLightXpath(String xpath) throws TcXmlException {
		final ByXPath xp2 = new ByXPath(xpath);
		List<WebElement> elements = driver.findElements(xp2);
		for (WebElement webElement : elements) {
			highlight(webElement);
			
		}
		
		
	}
	
	
	
	public void ensureDriver () throws TcXmlException {
		
		if(driver == null) {
			
			throw new TcXmlException("no driver found- browser is maybe not opened", new IllegalStateException());
			
		}
		
		
		
		
	}
	
	
	private void checkUnicity(final List<WebElement> list, final String xp) throws Exception {
		if (Objects.isNull(list) || list.isEmpty()) {
			throw new TcXmlException("No item found for xpath: " + xp ,new IllegalStateException());
		}
		if ((list).size() > 1) {
			throw new TcXmlException("Multiple items found for xpath: " + xp, new IllegalStateException());
		}
	}
	
	



private void highlight(final WebElement webElement) throws TcXmlException {
	
	ensureDriver();
	// store the webelement in the dom and call the highlighter extension

	final JavascriptExecutor js = (JavascriptExecutor) driver;
	final String scriptSetAttrValue = "var event = new CustomEvent('highlight', { detail: 'xa' });arguments[0].dispatchEvent(event);";
	js.executeScript(scriptSetAttrValue, webElement);
}

public WebDriver getDriver() {
	return driver;
}

public void closeBrowser() {
	
	driver.close();
}






}