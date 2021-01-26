package tcxml.remote;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonString;

import tcxml.model.Step;

public class RemoteRecordingSession {
	
	
	private List<RecordingSessionListener> recordingsessionlisteners ;
	
	
	private JsonObject sessionData  ;


	private String sessionId;


	private JsonString thebrowsername;
	
	
	public RemoteRecordingSession() {
		
		recordingsessionlisteners = new ArrayList<RecordingSessionListener>();
		
	}
	
	
	
	public void addListenner( RecordingSessionListener li) {
		
		this.recordingsessionlisteners.add(li);
	
	}
	
	
	
	
	public void removeListener(RecordingSessionListener li) {
		
		recordingsessionlisteners.remove(li);
		
	}

	public void addStep(Step fromRemote) {

		recordingsessionlisteners.forEach(li -> li.onNewStep(fromRemote));
		
	}
	
	
	
	public void startSession(JsonObject data ) {
		
		this.sessionData=data;
		sessionId = data.getString("sessionId");
		
		JsonObject sessionData = data.getJsonObject("value");
		thebrowsername = data.getJsonString("browserName");
		recordingsessionlisteners.forEach(li -> li.onSessionStart(data));
			
		}
	
	
	public String getSessionId() {
		return sessionId;
	}



	public void endSession(JsonObject data ) {
		
		this.sessionData=data;
		recordingsessionlisteners.forEach(li -> li.onSessionEnd(data));
			
		}
	
		
	}
	
	


