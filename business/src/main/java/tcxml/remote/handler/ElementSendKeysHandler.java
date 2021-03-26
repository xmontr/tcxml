package tcxml.remote.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;

import tcxml.core.StepAction;
import tcxml.core.StepFactory;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.RemoteRecordingSession;

public class ElementSendKeysHandler   extends AbstractHandler{

	public ElementSendKeysHandler(Map<String, String> p, Optional<SessionId> seleniumSessionId) {
		super(p, seleniumSessionId);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *  sample commande {"value":["S","t","r","3","s","s","m","3"],"text":"Str3ssm3"}
	 * 
	 */

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		String elementid = param.get("elementId");
		if(elementid == null) throw new IllegalStateException("no elementid in uri for send key handler ");
	Optional<By> theselector = recordingSession.selectorForElement(elementid);
	
	if( !theselector.isPresent()) throw new IllegalStateException("no selector for elementid"+ elementid);
	
	Step ret = StepFactory.newStep(StepType.TESTOBJECT);
	
	ret.setAction(StepAction.TYPE.getName());
	//build the argument map for the step 
	HashMap<String, String> argmap = new HashMap<String, String>();
	argmap.put("Value", thejsonCommand.getString("text"));
	String scriptarg = argsTojson(argmap) ; 
	ret.setArguments(scriptarg);
	ret.setTestObject(elementid);//trmporary set the ref before the real testobject is created

	
	
	storeStepInsession(ret,theselector.get(), recordingSession);
		
	}

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}

}
