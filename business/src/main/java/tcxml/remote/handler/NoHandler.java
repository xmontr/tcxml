package tcxml.remote.handler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.openqa.selenium.remote.SessionId;

import tcxml.core.StepAction;
import tcxml.core.StepFactory;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.RecordingSessionListener;
import tcxml.remote.RemoteRecordingSession;

public class NoHandler extends AbstractHandler {

	public NoHandler(Map<String, String> p,Optional<SessionId> seleniumSessionId) {
		super(p,seleniumSessionId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        dumpParams();
	log.info( "NoHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}

	@Override
	public void processRequest(JsonObject thejsonCommand,RemoteRecordingSession recordingSession) {
		
		// http call managed by the noHandler handler generate comment step 
		Step ret = StepFactory.newStep(StepType.UTIL);
		
		ret.setAction(StepAction.COMMENT.getName());
		StringBuffer sb = new StringBuffer();
		StringWriter sw = new StringWriter();
		JsonWriter jsonwriter = Json.createWriter(sw);
		jsonwriter.writeObject(thejsonCommand);
		sb.append(" skipped http request ").append(requestMethod).append(currentRequest).append(" ").append(StringEscapeUtils.escapeHtml(sw.toString()));
		ret.setComment(sb.toString());
		
		recordingSession.addStep(ret);
		
	
	}

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}

}
