package tcxml.core;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import jdk.nashorn.api.scripting.JSObject;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.parameter.StepParameter;
import tcxml.model.ArgModel;
import tcxml.model.CallFunctionAttribut;
import tcxml.model.Function;
import tcxml.model.Ident;
import tcxml.model.ObjectFactory;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.Transaction;
import tcxml.model.Transactions;
import tcxml.model.TruLibrary;
import tcxml.model.TruScript;
import util.TcxmlUtils;

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
	
	
   /***
    * 
    *  the name of the controller - basicaly the directory's name containig all the file of the script ( default.xml,*;dat, , .prm , libraries/**
    * 
    */
	private String name;
	
	/**
	 * 
	 * 
	 *  path of the directory containg all files of the script
	 */
    
    private File path ;
    
    /**
     *  map for the parameters of the script
     * 
     */
    private Map<String,StepParameter> parameters;
    
    /**
     * 
     *  map for the libraries of the script path/libraries/*
     */
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

	private File highlighterExtension;
	
	private File chromeApiExtension;

	private String scriptDir;
	
	
	private static  ScriptEngineManager  scriptFactory = new ScriptEngineManager();
	
	
	
    
    public TcXmlController(String name){
    	
    	
    	
    	log = Logger.getLogger(TcXmlController.class.getName());
    	log.setLevel(Level.ALL);
    	
    actionMap = new HashMap<String, Step>();
    parameters = new HashMap<String,StepParameter>();
    
    libraries = new HashMap<String,TruLibrary>();
    	
    this.name = name;	
    
  
    		
    		 
    }
    
 
  

	public List<Function> getFunctionsForLib( String libname) throws TcXmlException {
    	List<Function> ret = new ArrayList<Function>();
    TruLibrary lib = libraries.get(libname);	
    if(lib == null) {
    	
    	throw new TcXmlException("library with name" + libname +" does not exist ", new IllegalStateException());
    }
    	
    BoundList<Step> li = lib.getStep().getStep();	
    for (Step step : li) {
		Function nf = new Function();
		nf.setName(step.getAction());
		nf.setArgumentSchema(step.getArgsSchema());
		ret.add(nf);
	}
    
    return ret;
    	
    }
	
	
	public  HashMap<String, ArgModel> getArguments(String src) throws TcXmlException{
		HashMap<String, ArgModel> ret = new HashMap<String, ArgModel>() ;
	if(src != null) {
			
			JsonObject arg = this.readJsonObject(src);
			Set<String> keys = arg.keySet();
			for (String key : keys) {
			
				addArgument(ret, src, key);
			}
			
			
		}	
	return ret;	
		
	}
	
	
	
	public  HashMap<String, ArgModel> getArguments(Step step) throws TcXmlException{
		
	String src = step.getArguments();			
		HashMap<String, ArgModel> ret = new HashMap<String, ArgModel>() ;
		
	DefaultArgumentStepFactory fac = new DefaultArgumentStepFactory(step);
	List<ArgModel> def = fac.getDefaultArg();
	
		if(src != null) {
			
			JsonObject arg = this.readJsonObject(src);
			Set<String> keys = arg.keySet();
			for (String key : keys) {
			
				addArgument(ret, src, key);
			}
			
			
		}

 // add default arg
		
for (ArgModel val : def) {
	String k = val.getName();
	if( !ret.containsKey(k)) {
		ret.put(k, val);
		
	}
	

}
		
		return ret;
		
	}
	
	
	private  void addArgument(HashMap<String, ArgModel> li , String src, String name  ) throws TcXmlException {
		
		JsonObject arg = this.readJsonObject(src);		
		ArgModel p = new ArgModel(name);				
		p.populateFromJson(arg.getJsonObject(name));
		li.put( name,p);
		
		
	}
	
    
    
    
    
    public List<String> getFunctionsNameForLib(String libname) throws TcXmlException {
    	ArrayList<String> ret = new ArrayList<String>();
    	
    	 List<Function> libs = getFunctionsForLib(libname);
    	for (Function f : libs) {
    		ret.add(f.getName());
			
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
    	if(lib == null) { // no lib , look in the script
    		return getTestObjectById(id);
    		
    	}
    	else { // look in the lib
    		
    	   	BoundList<TestObject> li = lib.getTestObjects().getTestObject();
        	return getTestObjectById(id,li);	
    	}
 
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
	
	this.scriptDir= pathdir;
	
//search main script 
	  listing = Arrays.asList(file.list()) ;
	 
	 if ( !listing.contains("default.xml")) {
		 
		 throw new TcXmlException("invalid  script: no default.xml founded in " + pathdir ,  new IllegalArgumentException());	 
	 }
//search lib folder 	 
 if ( !listing.contains("Libraries")) {
	 
	 log.info(" no Libraries folder founded" + pathdir );
		 
		 
	 }
 
 //search parameter file
 if ( !listing.contains(name + ".prm")) {
	 log.info(" no parameter file .prm founded in " + pathdir);
	// throw new TcXmlException("invalid  script: no parameter file .prm founded in " + pathdir ,  new IllegalArgumentException());	 
 }	 
	
File mainscriptfile = new File(pathdir + "default.xml");
File parameterFile = new File(pathdir + name + ".prm");
File libdir = new File(pathdir + "Libraries");
try {
	 in = new FileInputStream(mainscriptfile);
} catch (FileNotFoundException e) {
	 throw new TcXmlException("invalid  path for script : no default.xml  founded in " + pathdir  ,  new FileNotFoundException());
}

loadScript(addExpectedNamespace("truScript", "xmlns=\"http://www.example.org/tcxml\"", in));
try {
	in.close();
} catch (IOException e1) {
	
	e1.printStackTrace();
}

// browse al libraries
if(libdir.exists()) {
	
	File[] libfiles = libdir.listFiles();
	for (File file2 : libfiles) {
		try {
			FileInputStream fin = new FileInputStream(file2);
			TruLibrary li = loadLibrary(addExpectedNamespace("truLibrary", "xmlns=\"http://www.example.org/tcxml\"", fin));
			String libname = li.getStep().getAction();
			
			libraries.put(libname, li);
			log.info("adding library " + libname  + " to script");
			try {
				fin.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			 throw new TcXmlException("exception when loading library" ,  new FileNotFoundException());
		}
		

		
		
	}
	
	
	
	
	
}




//load parameters
if(parameterFile.exists()) {
	loadParameters(parameterFile);	
	
}




setPath(file);
	
}
public String getScriptDir() {
	return scriptDir;
}

/**
 * 
 *  the default.xml doesn't contains any namespace for truScript so add xmlns="http://www.example.org/tcxml" to eanable the jaxb parsing
 * 
 * 
 * @param in
 * @return
 */
private InputStream addExpectedNamespace(String element, String ns, FileInputStream in) {
	// xmlns=\"http://www.example.org/tcxml\"
	
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	StringBuffer old = new StringBuffer();
	StringBuffer rep = new StringBuffer();
	old.append("<").append(element);
	rep.append(old.toString()). append(" ").append(ns).append("  ");
	
	java.util.function.Function<String,String> addNS= (String line ) ->{
		
		
		
		String  ret = line.replace(old.toString(), rep.toString());
		
		return ret;
	};
	
String tmp = br.lines().map(addNS).collect(Collectors.joining());
	
	
	return new ByteArrayInputStream(tmp.getBytes());
}

/***
 *  parse the parameter file and populate the parameters map
 * 
 * @param parameterFile
 * @throws TcXmlException 
 */

private void loadParameters(File parameterFile) throws TcXmlException {	
	
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
	ru.runStep(null);
	
}


	/*
	 * public String JSCodefromJSON( String json) throws TcXmlException { String ret
	 * = null;
	 * 
	 * 
	 * JsonObject codeobj = readJsonObject(json, "Code"); JsonString js =
	 * codeobj.getJsonString("value");
	 * 
	 * ret =js.getString();
	 * 
	 * 
	 * 
	 * return ret;
	 * 
	 * }
	 */



public String readStingArgumentByName(String json,String name) throws TcXmlException {
	String ret = null;
	
	
	 JsonObject obj = readJsonObject(json, name);
	JsonString js = obj.getJsonString("value");

ret =js.getString();
	


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


/***
 * 
 *  read the Json of the persistence model (default.xml)
 * 
 * 
 * @param json
 * @return
 * @throws TcXmlException
 */

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

/***
 * 
 *   as object identification is multiple : xpath, js, automatic, return the xpath one if defined, exception if not.
 * 
 * @param obj
 * @return
 * @throws TcXmlException
 */

public String getXpathForTestObject( TestObject obj) throws TcXmlException {

	
	return getIdentForTestObject(obj, "XPath");
	
	
}


public String getIdentForTestObject( TestObject obj, String idmethod) throws TcXmlException {
	String ret =null;
	boolean isXpathDefined = false;
	
	BoundList<Ident> identificators = obj.getIdents().getIdent();
	
	
	for (Ident ident : identificators) {
		if(ident.getType().equals(idmethod)){
			isXpathDefined =true;
			String json= ident.getValue();
			JsonObject arg = readJsonObject(json) ;
			arg = arg.getJsonObject("implData");
			ret =arg.getJsonString("value").getString();
			//ret=StringEscapeUtils.unescapeJavaScript(ret);
			
		}
		
	}
	
	if(!isXpathDefined) {
		
		throw new TcXmlException("no id method " + idmethod + " defined to identify testobject id:" + obj.getTestObjId(), new IllegalStateException());
		
		
		
	}
	
	return ret;
	
	
}













/**
 * 
 *  return the identification mode that is active for the testobject : javascript, xpath, automatic ..
 * 
 * @param obj
 * @return
 * @throws TcXmlException
 */
public String getActiveIdentificationForTestObject(TestObject  obj) throws TcXmlException {
	
return obj.getIdents().getActive();	
	
	
	
	
	
}




/**
 * 
 * 
 *   gives available action for a step whether it is a browser step or testobject step
 * 
 * 
 * @param s
 * @param lib
 * @return
 * @throws TcXmlException
 */
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



/**
 * 
 *  list all available action for a test object 
 * 
 * @param obj
 * @return
 */
public List<String> getAvailableActionForTestobject(TestObject obj){
	ArrayList<String> ret = new ArrayList<String>();
	ret.add("Type");
	ret.add("click");
	ret.add("dbl click");
	
return ret;	
}


/**
 * 
 *  gives the available actions for the browser i.e : add a tab ...
 * 
 * @return
 */
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


public ScriptEngine  getJSengine() {
	
	return  scriptFactory.getEngineByName("nashorn");
	
}



/***
 * 
 *  evaluate the javascript code. 
 *  LR object of type LrApi is in the context for the evaluation
 * 
 * 
 * @param code
 * @param ctx 
 * @return
 * @throws TcXmlException
 */

public Object evaluateJS(String code, ExecutionContext ctx) throws TcXmlException {
	
	 
	ScriptEngine engine = getJSengine();
	   ScriptContext context = ctx.getJsContext();
	   
	   log.info(" evaluationg javascript:\n " + code);
	   log.info(" context id is" + context);
	try {
		Object ret = engine.eval(code, context);
		
		log.info("return for evaluateJS is " + ret);
		// save global variable in the global context 
		Bindings nashorn_global = (Bindings) context.getAttribute("nashorn.global");
		context.getBindings( ScriptContext.ENGINE_SCOPE ).putAll(nashorn_global);
		
		return ret;
	} catch (ScriptException e) {
		ctx.dumpJsContext();
		throw new TcXmlException("fail to evaluate js code" + code , e);
	
	}
	
	
	
	
}



public Logger getLog() {
	return log;
}

public ScriptContext buildCallFunctionContext(ExecutionContext ec) throws TcXmlException {
	ScriptContext context = ec.getJsContext();
	   ScriptEngine engine = scriptFactory.getEngineByName("nashorn");
	  JSObject funcrgsobj = createJsObject();
	
	  
		 List<CallFunctionAttribut> li = ec.getArrgumentsList() ;
		 for (Iterator iterator = li.iterator(); iterator.hasNext();) {
			CallFunctionAttribut callFunctionAttribut = (CallFunctionAttribut) iterator.next();
			String name = callFunctionAttribut.getName();
			Object value=evaluate(callFunctionAttribut,ec);
			
			
				
			funcrgsobj.setMember(name, value);
		      

		      
				
				log.info("jscontext " + context + " adding entry to FuncArgs: "+name + " value is " + value +  " for execution context " + ec.getName());
		 	}
		 
		 log.info("jscontext " + context + " adding new  FuncArgs into context : " + funcrgsobj );
		 
    context.setAttribute("FuncArgs", funcrgsobj, ScriptContext.ENGINE_SCOPE);		 
		 return context;
			
			
			
			
			
			
		
		   
	   
	
	
	
	
	
}




private Object evaluate(CallFunctionAttribut callFunctionAttribut, ExecutionContext ctx) throws TcXmlException {
	Object ret ;
	if( callFunctionAttribut.isJs()) { // value is js that need to be evaluated
		String code = callFunctionAttribut.getValue();
		ret= evaluateJS(code, ctx);
		
	}else {
		
	ret = callFunctionAttribut.getValue();	
		
	}
	
	
	
	return ret;
}

public  ScriptContext   buildInitialJavascriptContext() throws TcXmlException {
	
	ScriptEngine engine = getJSengine();
	ScriptContext context = new SimpleScriptContext();

	context.setBindings(engine.createBindings(), ScriptContext.GLOBAL_SCOPE);
	
	// import LR object
	   Object api = new LrAPI(this);
	   
	  
	   context.setAttribute("LR", api, ScriptContext.GLOBAL_SCOPE);
	   context.setAttribute("TC", api, ScriptContext.GLOBAL_SCOPE);
	   
	   // import Utils obj
	   Object utils = new UtilsAPI(this);
	   context.setAttribute("Utils", utils, ScriptContext.GLOBAL_SCOPE);
	
	   


	   
	   
	
return context;	
}


public StepParameter getParameterByName ( String name) throws TcXmlException  {
	
	StepParameter ret = this.parameters.get(name) ;
	if(ret== null) {
		
		throw new TcXmlException("unknown parameter "+name, new IllegalArgumentException("name"));
	}
	
	return ret;
	
	
}
/**
 * 
 * open the browser relative to the driver chosen firefox / chrome
 * 
 * 
 * @param type
 * @param driverPath
 * @throws TcXmlException
 */

public void openBrowser (String type, String driverPath) throws TcXmlException {
	
	
	//policy to destroy HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome
	


	// only chrome at this point
	System.setProperty("webdriver.chrome.driver",driverPath );
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities caps = DesiredCapabilities.chrome();
	caps.setCapability(ChromeOptions.CAPABILITY, options);
	
	options.addArguments("disable-infobars");
	options.addArguments("start-maximized");
	if(highlighterExtension == null) {
		highlighterExtension = generatePathToLocalTemporaryResource("jqueryHighlighter.crx").toFile();
	}
	 
	 options.addExtensions(highlighterExtension);
	 
	 
		if(chromeApiExtension == null) {
			chromeApiExtension = generatePathToLocalTemporaryResource("chromeApiInjector.zip").toFile();
		}
		 
		 options.addExtensions(chromeApiExtension);
	 
	 

	options.setExperimentalOption("useAutomationExtension", false);
	
	try {
	driver = new ChromeDriver(options);
	// ensure at least a page is loaded ( required by utils.clearcache )
	driver.get("chrome://version/");
	}
	catch (Exception e) {
		throw new TcXmlException("failure opening browser", e);
	}
	

}	

/***
 * 
 * 
 * 
 * @param exportPath
 * @return
 * @throws TcXmlException 
 */

public File exportTestcentreJSresource(java.nio.file.Path exportPath, String resource) throws TcXmlException {
	
	  InputStream src = getClass().getClassLoader().getResourceAsStream(resource) ;
	Path target = exportPath.resolve(resource);
	
	try {
		Files.copy(src, target, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		throw new TcXmlException("fail to export "+resource+ " file", e);
	}	
	
	return target.toFile();
	
	
	
}



private Path generatePathToLocalTemporaryResource(String resourceName) throws TcXmlException {
	
	
	 InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);

try {
	FileAttribute<?>[] fileatt= new FileAttribute<?>[0];
	
	Path tempLocal = Files.createTempFile(resourceName, null, fileatt);
	Files.copy(in, tempLocal,  StandardCopyOption.REPLACE_EXISTING);
	return tempLocal;
	
	
	
} catch (IOException e) {
	throw new TcXmlException("failure when creating local temp resources for " + resourceName, e);
}
	
}






/**
 *  visual effect on the element before acting to 
 * 
 * 
 * 
 * @param xpath
 * @throws TcXmlException
 */
	
	public void highLightXpath(String xpath) throws TcXmlException {
		ensureDriver();
		final ByXPath xp2 = new ByXPath(xpath);
		List<WebElement> elements = driver.findElements(xp2);
		for (WebElement webElement : elements) {
			highlight(webElement);
			
		}
		
		
	}
	
	
	public WebElement evalXPath(String xpath) throws TcXmlException {
		ensureDriver();
		final ByXPath xp2 = new ByXPath(xpath);
		List<WebElement> elements = driver.findElements(xp2);
		checkUnicity(elements, xpath);
		return elements.get(0);
		
		
	}
	
	
	
	
	
	
	/***
	 *  type text in a input
	 * @param ctx 
	 * 
	 * 
	 * 
	 * @param xpath loaction of input
	 * @param text  text to type
	 * @param typingInterval  interval in millisecond between each letter
	 * @throws TcXmlException
	 */
	
	public void typeText(ExecutionContext ctx, TestObject to ,String text, long typingInterval, boolean clear) throws TcXmlException {
		ensureDriver();
		
		WebElement finded = this.identifyElement(to,ctx);
		
	
		
		 highlight(finded);
		 if(clear) {
			 finded.clear();
			 
		 }

			final String[] aletter = text.split(StringUtils.EMPTY);
			for(int i = 0 ; i < aletter.length ; i++) {
				finded.sendKeys(aletter[i]);
				try {
					Thread.sleep(typingInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		
		
		
		
	}
	
	
	
	public void ensureDriver () throws TcXmlException {
		
		if(driver == null) {
			
			throw new TcXmlException("no driver found- browser is maybe not opened", new IllegalStateException());
			
		}
		
		
		
		
	}
	
	
	public void checkUnicity(final List<WebElement> list, final String xp) throws TcXmlException {
		if (Objects.isNull(list) || list.isEmpty()) {
			throw new TcXmlException("No item found for xpath: " + xp ,new IllegalStateException());
		}
		if ((list).size() > 1) {
			throw new TcXmlException("Multiple items found for xpath: " + xp, new IllegalStateException());
		}
	}
	
	



public void highlight(final WebElement webElement) throws TcXmlException {
	
	
	// store the webelement in the dom and call the highlighter extension

	final JavascriptExecutor js = (JavascriptExecutor) driver;
	final String scriptSetAttrValue = "var event = new CustomEvent('highlight', { detail: 'xa' });arguments[0].dispatchEvent(event);";
	js.executeScript(scriptSetAttrValue, webElement);
}

public WebDriver getDriver() {
	return driver;
}

public void closeBrowser() {
	
	if(driver != null) {
		driver.quit();
		driver.close();
	}
	
	
}




protected void generateSnapshotOnError(final Exception e) {
	screenshot("error-" + UUID.randomUUID().toString() + ".png");
}




/**
 * 
 * @param filename
 */
protected void screenshot(final String filename) {
	final WebDriver augmentedDriver = new Augmenter().augment(driver);
	final File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

	final File dest = new File(getSnapshotDir() + filename);
	screenshot.renameTo(dest);
}

private String getSnapshotDir() {
	// TODO Auto-generated method stub
	return "C:\\tmp\\";
}




private String getRecordSnapshotsAbsolutePath() {
	String ret= path + "/snapshots/";
	return ret;
	
	
	
}




public String getRecordSnaphotImage4step( Step st) {
	

	StringBuffer sb = new StringBuffer();
	sb.append(getRecordSnapshotsAbsolutePath());
	sb.append("/").append(st.getSnapshotId()).append(".png");	
	
	return sb.toString();
}




/**
 *  return the list of the transactions declared in the script or in the libs
 * @return 
 * 
 * 
 * 
 */
public List<Transaction> getAlltransactions() {
	
	List<Transaction> ret = new ArrayList<Transaction>();
	
	
	ret.addAll(script.getTransactions().getTransaction());
	
	Collection<TruLibrary> liblist = libraries.values();
	for (TruLibrary truLibrary : liblist) {
		
		if( truLibrary.getTransaction()  != null) {
			ret.addAll(truLibrary.getTransaction().getTransaction());			
		}
		
		
	}	
	return ret;
	
	
	
	
	
	
	
}
/**
 *  uniquely identify the testobject acording the identification method or theow TcxmlExceptio
 * 
 * 
 * @param to
 * @param ctx 
 * @return
 */


public WebElement identifyElement(TestObject to, ExecutionContext ctx) throws  TcXmlException{
	String method = to.getIdents().getActive();
	
IdentificationMethod identmetho = IdentificationMethod.get(method);
	WebElement ret = null;
	switch (identmetho) {
	case XPATH:
		String xp = getIdentForTestObject(to, method);
		this.ensureDriver();
		WebDriver driver = this.getDriver();
		final ByXPath xp2 = new ByXPath(xp);	
		List<WebElement> elements = driver.findElements(xp2);
		this.checkUnicity(elements, xp);
		ret = elements.get(0);
		
		
		
		break;
	case JAVASCRIPT :   
		String identjs = getIdentForTestObject(to, method);
		ret = evalJavascriptForIdentification(identjs,ctx);
		
		
		break;

	default:
		throw new TcXmlException("identification method not supported " + method, new IllegalArgumentException());
		
	}
	return ret;
}

/**
 * 
 * evaluate the javascript code in the case of a javascript identification 
 * 
 * 
 * @param identjs
 * @param ctx
 * @return
 * @throws TcXmlException
 */

private WebElement evalJavascriptForIdentification(String identjs, ExecutionContext ctx) throws  TcXmlException{
	
	ScriptContext currentjscontext = ctx.getJsContext();
	ScriptContext identificationcontext = buildIdentificationJavascriptContext(ctx);
	ScriptEngine engine = getJSengine();
	
	   log.info(" evaluationg javascript for identification :\n " + identjs);
	   log.info(" context id is" + identificationcontext);
	   try {
		   WebElement ret =(WebElement) engine.eval(identjs, identificationcontext);
			
			log.info("return for evaluateJS is " + ret);

			
			return ret;
		} catch (ScriptException e) {
			ctx.dumpJsContext();
			throw new TcXmlException("fail to evaluate js code for identification ", e);
		
		}   
	   
	   
	

}




public void evalJavascriptOnObject(String identjs,WebElement element, ExecutionContext ctx) throws  TcXmlException{
	
	ScriptContext currentjscontext = ctx.getJsContext();
	ScriptContext identificationcontext = buildEvalOnObjectJavascriptContext(ctx, element);
	ScriptEngine engine = getJSengine();
	
	   log.info(" evaluationg javascript for evaljsonobject :\n " + identjs);
	   log.info(" context id is" + identificationcontext);
	   
	   try {
		    engine.eval(identjs, identificationcontext);
			// save global variable in the global context 
			Bindings nashorn_global = (Bindings) identificationcontext.getAttribute("nashorn.global");
			identificationcontext.getBindings( ScriptContext.ENGINE_SCOPE ).putAll(nashorn_global);
			
			

			
			
		} catch (ScriptException e) {
			ctx.dumpJsContext();
			throw new TcXmlException("fail to evaluate js code for evaljsonobject ", e);
		
		} 
	   
	   
}

private JSObject createJsObject() throws TcXmlException  {

	JSObject ret = null;
	ScriptEngine engine = getJSengine();
	try {
		JSObject objConstructor= (JSObject)engine.eval("Object");
		ret= (JSObject) objConstructor.newObject();
		
		
		
	} catch (ScriptException e) {
		throw new TcXmlException("fail to create js object constructor", e);
	}
	return ret;
}









/***
 * 
 * build the javascript context that enable the execution of the eval javascript on object step
 * 
 * 
 * @param curentexeccontext
 * @param currentjscontext
 * @param element
 * @return
 * @throws TcXmlException 
 */



private ScriptContext buildEvalOnObjectJavascriptContext(ExecutionContext curentexeccontext,
		 WebElement element) throws TcXmlException {
	ScriptContext context = curentexeccontext.getJsContext(); 	 
		   JSObject thisobject = new WebElementWrapper(element);
		   context.setAttribute("object", thisobject, ScriptContext.GLOBAL_SCOPE);			
		
	return context;
}

/***
 *  build the javascript context for the identification
 *  ie with evalXpath method and ArgsContext object
 * 
 * 
 * @param curentexeccontext
 * @param currentjscontext
 * @return
 * @throws TcXmlException 
 */
private ScriptContext buildIdentificationJavascriptContext(ExecutionContext curentexeccontext) throws TcXmlException {
	//ScriptEngine engine = getJSengine();
	//ScriptContext context = new SimpleScriptContext();
	//context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
	
	// the evalXPath function
	EvalXpathFunction evalXPath = new EvalXpathFunction(this);	
	curentexeccontext.getJsContext().setAttribute("evalXPath", evalXPath, ScriptContext.ENGINE_SCOPE);
	 
	 //build Argscontext objet
		   JSObject argscontext = createJsObject();
		   curentexeccontext.getJsContext().setAttribute("ArgsContext", argscontext, ScriptContext.ENGINE_SCOPE);
	 
	 //copy already exsiting global variables as member of  ARgsContext	   
	 
		
	Bindings bd = curentexeccontext.getJsContext().getBindings( ScriptContext.ENGINE_SCOPE );
		
			
			Set<String>  keys = bd.keySet();
			for (String key : keys) {
				Object var = bd.get(key);
				 argscontext.setMember(key, var);
				 log.info("adding entry to ArgsContext: "+key + " value is :" + var);					
			
				
			
			
				
		}
			
			
	// FuncArgs  is accessible in global scope even if it should be accessed via argscontext		
		
			 
			Object FuncArgs = bd.get("FuncArgs");
		
			curentexeccontext.getJsContext().setAttribute("FuncArgs", FuncArgs, ScriptContext.ENGINE_SCOPE); 
			 log.info("copy funcargs "+FuncArgs + " into context: " + curentexeccontext.getJsContext() );
	 
	return curentexeccontext.getJsContext();
}

public String evaluateJsArgument(ArgModel theArg, ExecutionContext ctx) throws TcXmlException {
	boolean isj = theArg.getIsJavascript();
	
	String ret = theArg.getValue();
	 
	log.fine("evaluation of argument" + theArg.getName());
		 
		 if(isj == true) { // need to evaluate js argument
	ret =		(this.evaluateJS(theArg.getValue(),ctx)).toString(); 
	
	log.fine("before eval JS " +  theArg.getName() + "is:" + theArg.getValue());	
	log.fine("after eval JS " +  theArg.getName() + "is:" + ret); 

}



return ret;


}

/**
 *  generate the js code that create the funcarggs argument for function  
 * 
 * 
 * @param li
 * @return
 */

public String generateJsFunctArgument( List<CallFunctionAttribut> li) {
	StringBuffer ret = new StringBuffer();
	ret.append(" {\n"  );
	for (CallFunctionAttribut callFunctionAttribut : li) {
		String attname =callFunctionAttribut.getName();
		boolean isjs = callFunctionAttribut.isJs() ;
		String value="";
		if(isjs) { // javascript attribut
			
		value = generateNonAnonymousFunctionForJScode(callFunctionAttribut.getValue());	
			
		}else { //plain text attribut
			
			value = callFunctionAttribut.getValue();
			
		}
		
		
		
		
	ret.append(attname).append(":").append(TcxmlUtils.formatAsJsString(value, "\"")).append(",\n");	
		
	}
	
	
	ret.append("}\n");
return ( ret.toString() );	
}


private String generateNonAnonymousFunctionForJScode(String value) {
	StringBuffer sb = new StringBuffer();
	sb.append("function(){");
	
	sb.append(value);
	sb.append("}");
	
	
	
	return sb.toString();
}


public String generateFunctArgJSobject(List<CallFunctionAttribut> listArguments) {
	StringBuffer ret = new StringBuffer();
	ret.append("{\n"  );
	for (CallFunctionAttribut argmo : listArguments) {
		String attname =argmo.getName();
		boolean isjs = argmo.isJs() ;

			
			String value = TcxmlUtils.formatAsJsString(argmo.getValue(), "\"");
			
		
		
	ret.append(attname).append(":").append("{");
	ret.append("name:").append(TcxmlUtils.formatAsJsString(attname, "\""));
	ret.append(",");
	ret.append("value:").append(value);
	ret.append(",");
	ret.append("isjs:").append(isjs);
	
	
	
	
	ret.append("},\n");	
		
	}
	
	
	ret.append("}\n");
return ( ret.toString() );		
	
	
}




public String generateJSobject( ArgModel...  models ) {
	StringBuffer ret = new StringBuffer();
	ret.append("{\n"  );
	for (ArgModel argmo : models) {
		String attname =argmo.getName();
		boolean isjs = argmo.getIsJavascript() ;
		String value="";

			
			value = TcxmlUtils.formatAsJsString(argmo.getValue(), "\"");
			
	
		
	ret.append(TcxmlUtils.formatAsJsString(attname, "\"")).append(":").append("{");
	ret.append("name:").append(TcxmlUtils.formatAsJsString(attname, "\""));
	ret.append(",");
	ret.append("value:").append(value);
	ret.append(",");
	ret.append("isjs:").append(isjs);
	
	
	
	ret.append("},\n");	
		
	}
	
	
	ret.append("}\n");
return ( ret.toString() );		
	
	
	
}







public String getName() {
	return name;
}

private String generateAnonymousFunctionForJScode(String jscode) {
	StringBuffer sb = new StringBuffer();
	sb.append("(function(){");
	
	sb.append(jscode);
	sb.append("})()");
	
	
	
	return sb.toString();
}



/**
 * 
 *  generate the code in javascript that create e new testobject
 * 
 * 
 * @param to
 * @return
 * @throws TcXmlException
 */

public String generateJsTestObject(TestObject to) throws TcXmlException {
	StringBuffer ret = new StringBuffer() ;
	String identMethod = to.getIdents().getActive();
	String ident = getIdentForTestObject(to, identMethod);
	String val = TcxmlUtils.formatAsJsString(ident, "\"");
	
	

	

	String txt = TcxmlUtils.formatJavascriptFunction(
			"TC.testObject",TcxmlUtils.formatAsJsString(identMethod, "\""),val					
			);
	
	
	
	ret.append("new ").append(txt.substring(0, txt.length()-2)) ;
	
	return ret.toString();
}

}