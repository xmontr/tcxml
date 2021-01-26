package tcxml.remote.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import stepWrapper.StepWrapperFactory;
import tcxml.core.StepAction;
import tcxml.core.StepFactory;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.DriverRequestHandler;
import tcxml.remote.RemoteRecordingSession;

public class ExecuteScriptHandler extends AbstractHandler {

	public ExecuteScriptHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {    
   
        
        dumpParams();
	log.info( "ExecuteScrptHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}



	
	/**
	 * sample of command
	 * {"script":" window.postMessage({ action:'clearLocalStorage'}, '*'); ","args":[]}
	 * 
	 */
	@Override
	public void processRequest(JsonObject thejsonCommand,RemoteRecordingSession recordingSession) {
		
		
		if(thejsonCommand == null) {
			throw new IllegalArgumentException("Execute script step require a non null json command");
			
		}
		
		Step ret = new Step();
		ret.setType(StepType.UTIL.getName());
		ret.setAction(StepAction.EVALJAVASCRIPT.getName());		
		//build the argument map for the step 
		HashMap<String, String> argmap = new HashMap<String, String>();
		argmap.put("Code", thejsonCommand.getString("script"));
		String scriptarg = argsTojson(argmap) ; 
		ret.setArguments(scriptarg);
		storeStepInsession(ret, recordingSession);

		}
		
	



	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}




	
	
	
	
	

}
