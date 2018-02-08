package tcxml.core;

import java.io.File;
import org.apache.commons.configuration.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import com.kscs.util.jaxb.BoundList;

import tcxml.core.parameter.StepParameter;
import tcxml.model.Ident;
import tcxml.model.ObjectFactory;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxml.model.TruScript;

public class TcXmlController {
	
	
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
    
    public TcXmlController(String name){
    	
    	log = Logger.getLogger(TcXmlController.class.getName());
    	log.setLevel(Level.ALL);
    	
    actionMap = new HashMap<String, Step>();
    
    
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

public void playSingleStep( Step theStep) throws TcXmlException{
	
	StepRunner ru = StepRunnerFactory.getRunner(theStep);
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
			ret =arg.getJsonString("value").toString();
			
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
	
	ret.add("Activate");
	ret.add("Activate Tab");
	ret.add("Add Tab");
	ret.add("Close");
	ret.add("Close Html Dialog");
	ret.add("Close Tab");
	ret.add("Dialog - Alert");
	ret.add("Dialog - Authenticate");
	ret.add("Dialog - Confirm");
	ret.add("Dialog - Prompt");
	ret.add("Dialog - Prompt Password");
	ret.add("Get Property");
	ret.add("Go Back");
	ret.add("Go Forward");
	ret.add("Navigate");
	ret.add("Reload");
	ret.add("Resize");
	ret.add("Scroll");
	ret.add("Stop");
	ret.add("Verfify");
	
	
return ret;	
	
	



	
}

public Object evaluateJS(String code) throws TcXmlException {
	
	   ScriptEngineManager m = new ScriptEngineManager();
	   ScriptEngine engine = m.getEngineByName("nashorn");
	   ScriptContext context = new SimpleScriptContext();
	   
	   Object api = new LrAPI();
	   
	   context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
	   context.setAttribute("LR", api, ScriptContext.ENGINE_SCOPE);
	try {
		Object ret = engine.eval(code, context);
		return ret;
	} catch (ScriptException e) {
		throw new TcXmlException("fail to evaluate js code ", e);
	
	}
	
	
	
	
}






}