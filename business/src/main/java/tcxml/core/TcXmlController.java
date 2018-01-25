package tcxml.core;

import java.io.File;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kscs.util.jaxb.BoundList;

import tcxml.model.ObjectFactory;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxml.model.TruScript;

public class TcXmlController {
	
	
    private String name;
    
    private File path ;
    
    
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
	 
 if ( !listing.contains("Libraries")) {
		 
	 throw new TcXmlException("invalid  script: no Libraries folder founded" ,  new IllegalArgumentException());	 
	 }
	 
	
File mainscriptfile = new File(pathdir + "default.xml");
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


public String JSCodefromJSON( String json) {
	String ret = null;
	
	
	Reader reader = new StringReader(json);
			
	JsonReader jr = Json.createReader(reader );
	JsonStructure stru = jr.read();
	String thpointer="Code/value";
	JsonValue val = stru.getValue(thpointer);
	ret = val.toString();
	return ret;
	
}



}