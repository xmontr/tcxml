package tcxml.remote.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;

import tcxml.remote.BySelector;
import tcxml.remote.RemoteRecordingSession;

public class FindElementsHandler extends AbstractHandler{

	private String selectorId;
	private String selectorValue;
	private By selector;




	public FindElementsHandler(Map<String, String> p,Optional<SessionId> seleniumSessionId) {
		super(p,seleniumSessionId);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        dumpParams();
	log.info( "findElementsHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}
	/***
	 * 
	 *  sample json {"using":"xpath","value":"//input[@type=\"text\" and @name=\"username\"]"}
	 * 
	 */

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		if(thejsonCommand == null) {
			throw new IllegalArgumentException("find elements  step require a non null json command");
			
		}
		
		 selector = new BySelector().pickFromJsonParameters(thejsonCommand);
		 

		
	}
	
	
	
	
	/***
	 * 
	 *
	 * 
	 *  sample json {
  "state": "success",
  "sessionId": null,
  "class": "org.openqa.selenium.remote.Response",
  "value": [
    {
      "element-6066-11e4-a52e-4f735466cecf": "6d503981-3fb2-428d-b826-a58a351fde99"
    }
  ],
  "status": 0
}
	 * 
	 * 
	 * 
	 */

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		if(commandSuccess(thejsonCommand)) {
			
		JsonArray values = thejsonCommand.getJsonArray("value");	
		
		int nbelement = values.size();
		if(nbelement == 1) {
		JsonObject ret = values.get(0).asJsonObject() ;
	Optional<String> key = ret.keySet().stream().findFirst();
	if(key.isPresent()) {
		
		String elementid = ret.getJsonString(key.get()).getString() ;
		
		storeSelectorInsession(selector, elementid, recordingSession);
		
		
		
	log.info("adding new element id=" + elementid + " in knownelements with value " + selector.toString());	
	}
			
			
			
			
		}else {
			
			log.info("*********** multiple object returned by selector");
			recordingSession.onError(new IllegalStateException("selector returned " + nbelement  +"   non unique element " + selector.toString()));
		}
		
		
		
		
			
			
		}
		
	}

}
