package tcxml.remote.handler;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import tcxml.core.StepAction;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.RemoteRecordingSession;

public class NavigateToHandler extends AbstractHandler{

	public NavigateToHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/****
	 * 
	 * 
	 *  sample of command {"url":"https://ecasl.cc.cec.eu.int:7002/cas/login"}
	 *  
	 * 
	 */

	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		if(thejsonCommand == null) {
			throw new IllegalArgumentException("navigateTo step require a non null json command");
			
		}		
		Step ret = new Step();
		ret.setType(StepType.TESTOBJECT.getName());
		ret.setAction(StepAction.NAVIGATE.getName());
		//build the argument map for the step 
		HashMap<String, String> argmap = new HashMap<String, String>();
		argmap.put("Location", thejsonCommand.getString("url"));
		String scriptarg = argsTojson(argmap) ; 
		ret.setArguments(scriptarg);
		storeStepInsession(ret, recordingSession);
		
		
	
		
		
		
		
	}



	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		// TODO Auto-generated method stub
		
	}

}
