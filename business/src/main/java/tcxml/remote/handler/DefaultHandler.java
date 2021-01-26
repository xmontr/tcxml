package tcxml.remote.handler;

import java.io.IOException;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import tcxml.model.Step;
import tcxml.remote.RemoteRecordingSession;

public class DefaultHandler extends AbstractHandler{

	public DefaultHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		
       
   
        
        dumpParams();
	log.info( "DefaultHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
	
	}

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}

}
