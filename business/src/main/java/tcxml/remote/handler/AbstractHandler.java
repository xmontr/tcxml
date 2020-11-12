package tcxml.remote.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.protocol.HttpRequestHandler;

public abstract class AbstractHandler implements HttpRequestHandler{
	
	
	protected Map<String, String> param;
	
	
	
	protected AbstractHandler(Map<String, String> p) {
		
		this.param =p;
	}
	
	
	
	protected void dumpParams() {
		
	
	Set<String> listkey = param.keySet();
	for (String key : listkey) {
		System.out.println("handler parameter:" + key + " value:" + param.get(key) );
		
	}
			
		}
		
	}
	


