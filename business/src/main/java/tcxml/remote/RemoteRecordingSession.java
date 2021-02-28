package tcxml.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import tcxml.model.Step;

public class RemoteRecordingSession {
	
	
	private List<RecordingSessionListener> recordingsessionlisteners ;
	
	
	private JsonObject sessionData  ;


	private String sessionId;


	private JsonString thebrowsername;


	private BiMap<By, String> knownElements;


	private Logger log;
	
	
	
	
	
	public RemoteRecordingSession(Optional<SessionId> seleniumSessionId) {
		
      	log = Logger.getLogger(getClass().getName());
      	log.setLevel(Level.ALL);
		
		recordingsessionlisteners = new ArrayList<RecordingSessionListener>();
		
		knownElements = HashBiMap.create();
		
		if(seleniumSessionId.isPresent()) {
			this.sessionId = seleniumSessionId.get().toString();
			log.info("re using session " + this.sessionId);
			
			
		}
		
	}
	
	
	
	public void addListenner( RecordingSessionListener li) {
		
		this.recordingsessionlisteners.add(li);
	
	}
	
	
	
	
	public void removeListener(RecordingSessionListener li) {
		
		recordingsessionlisteners.remove(li);
		
	}

	public void addStep(Step fromRemote, By by) {

		recordingsessionlisteners.forEach(li -> li.onNewStep(fromRemote,by));
		
	}
	
	
	public void addStep(Step fromRemote) {

		recordingsessionlisteners.forEach(li -> li.onNewStep(fromRemote));
		
	}
	
	
	
	public void onError( Exception e) {
		
		recordingsessionlisteners.forEach(li -> li.onError(e));	
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
		knownElements.clear();
			
		}
	



	public void addKnownElements(By selector,String elemntid) {
		
		   if (!knownElements.containsValue(elemntid)) {
			   
			   knownElements.put(selector, elemntid);
			   
			   log.info("sessionid " +sessionId + "  adding " + elemntid + " in knownelements" + " " + knownElements.containsValue(elemntid) + " " + knownElements.size());
			   
			    }
		
		
		
		
		
	}
	
	
	
	public Optional<By> selectorForElement(String elementid) {
		By ret = null;
		if(knownElements.containsValue(elementid)) {
			log.info(" found " +elementid + " in knownelements");
			ret = knownElements.inverse().get(elementid);
			
			
		}else {
			
			log.warning("sessionid " +sessionId + " elementid:" +elementid + " is not in knownelements" +  " " + knownElements.size());
		}
		
	return Optional.ofNullable(ret);
		
	}



	
	
	
		
	}
	
	


