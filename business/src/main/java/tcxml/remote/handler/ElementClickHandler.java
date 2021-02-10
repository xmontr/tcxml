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
import org.openqa.selenium.By;

import tcxml.core.StepAction;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.RemoteRecordingSession;

public class ElementClickHandler extends AbstractHandler{

	public ElementClickHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        dumpParams();
	log.info( "ElementClickHandler handle request:" + request.getRequestLine());
	super.handle(request, response, context);
		
	}
	
	

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		String elementid = param.get("elementId");
		if(elementid == null) throw new IllegalStateException("no elementid in uri for click handler ");
	Optional<By> theselector = recordingSession.selectorForElement(elementid);
	
	if( !theselector.isPresent()) throw new IllegalStateException("no selector for elementid"+ elementid);
	
	Step ret = new Step();
	ret.setType(StepType.TESTOBJECT.getName());
	ret.setAction(StepAction.CLICK.getName());
	ret.setTestObject(elementid);//trmporary set the ref before the real testobject is created

	
	
	storeStepInsession(ret, recordingSession);
	
	
	
	
	
	
	
	
	
		
	}

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}

}
