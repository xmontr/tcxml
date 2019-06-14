package tcxml.core;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.script.Bindings;
import javax.script.ScriptContext;

import tcxml.model.CallFunctionAttribut;

public class ExecutionContext {
	
	
	private List<CallFunctionAttribut> arrgumentsList;
	
	
	private PlayingContext parent;
	
	private String name ;
	
	private ScriptContext jsContext;
	

	

	
	
	public PlayingContext getParent() {
		return parent;
	}

	public void setParent(PlayingContext parent) {
		this.parent = parent;
	}

	public List<CallFunctionAttribut> getArrgumentsList() {
		return arrgumentsList;
	}


	public void dumpJsContext() {
		
		Logger log = parent.getController().getLog();
		
		log.info("dumping jscontext " + jsContext + " for execution context " + name);
		
		log.info(" browsing global variable for JS context" + jsContext);
		Bindings nashorn_global = (Bindings) jsContext.getAttribute("nashorn.global");

		
		Set<Entry<String, Object>> globalval = nashorn_global.entrySet();
		for (Entry<String, Object> entry : globalval) {
			log.info("   found global var " + entry.getKey() + " value= " + entry.getValue());
		}
		log.info(" browsing local variable for JS context"  + jsContext );
		Set<Entry<String, Object>> localval = jsContext.getBindings(ScriptContext.ENGINE_SCOPE).entrySet();
		for (Entry<String, Object> entry : localval) {
			
			
			log.info(" found local var " + entry.getKey()+ " value= " + entry.getValue());
			
			
			
		}
		
		
		
		
		
		
	}

	public ExecutionContext(String name , List<CallFunctionAttribut> arrgumentsList,ScriptContext jsContext) {
		super();
		this.name = name;
		this.arrgumentsList = arrgumentsList;
		this.jsContext = jsContext ;
	}

	public ScriptContext getJsContext() {
		return jsContext;
	}

	public String getName() {
		return name;
	}


	
	
	public Object getGlobalJsVariable(String name) {
		Bindings nashorn_global = (Bindings) jsContext.getAttribute("nashorn.global");
		if(nashorn_global == null) {
			
			return null ;
		}
		
		return nashorn_global.get(name);
		
	}

	
	


	
	
	
	

}
