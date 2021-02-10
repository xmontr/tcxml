package tcxml.remote;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import tcxml.remote.handler.AbstractHandler;

public class SessionHandler extends AbstractHandler{

	protected SessionHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        dumpParams();
	log.info( "sessionHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		
		
	}
	
	/**
	 * 
	 *  
	 * 
	 */

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		
		Optional<RemoteRecordingSession> rs = Optional.ofNullable(recordingSession);
		
		
		
		if(commandSuccess(thejsonCommand) ) {
			
			switch (requestMethod) {
			case "post": log.info("adding new session");
			if(rs.isPresent())
			rs.get().startSession(thejsonCommand);
				
				
				break;
				
			case "delete":log.info("delete  session");
			break;

			default:log.info("unsupported method " + requestMethod + " in  session");
				break;
			
			}	
		
			
		}else {
			
			log.info(" failure in  processing session with command " + thejsonCommand );		
		}

		
		
		
	}

}
