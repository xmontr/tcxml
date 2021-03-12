package tcxml.remote.handler;

import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;

import tcxml.core.StepAction;
import tcxml.core.StepFactory;
import tcxml.core.StepType;
import tcxml.model.Step;
import tcxml.remote.RemoteRecordingSession;

public class WaitOnHandler extends AbstractHandler {

	private String currentElementId;
	public WaitOnHandler(Map<String, String> p, Optional<SessionId> seleniumSessionId) {
		super(p, seleniumSessionId);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * sample request /session/ecfcbfb983ceb132c6cacc2601be106d/element/0.0692885925120672-2/enabled
	 */
	
	@Override
	public void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
		this.currentElementId=param.get("elementId");
		
	}
	/**
	 * 
	 *  sample response : {"sessionId":"ecfcbfb983ceb132c6cacc2601be106d","status":0,"value":true}
	 * 
	 */

	@Override
	public void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) {
	String thesessionid = thejsonCommand.getJsonString("sessionId").getString();
		int thestatus = thejsonCommand.getInt("status");
		boolean isEnabled = thejsonCommand.getBoolean("value");
		if(isEnabled ) {
			
			Step thestep = StepFactory.newStep(StepType.TESTOBJECT);
			thestep.setType(StepType.TESTOBJECT.getName());
			thestep.setAction(StepAction.WAIT.getName());
			
			 Optional<By> optby = recordingSession.selectorForElement(currentElementId);
			 
			 if( !optby.isPresent()) {
				 
				throw new IllegalStateException(currentElementId  + " not found in knownelements ") ;
				 
			 }else {
				 
				 storeStepInsession(thestep, optby.get() , recordingSession); 
				 
			 }
			 
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
	}

}
