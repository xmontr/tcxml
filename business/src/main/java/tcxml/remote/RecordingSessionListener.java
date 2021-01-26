package tcxml.remote;

import javax.json.JsonObject;

import tcxml.model.Step;

public interface RecordingSessionListener {
	
	
	/**
	 *  start new selenium session
	 * 
	 * @param sessionId
	 */
	public void onSessionStart(JsonObject sessionData);
	
	/***
	 * 
	 *  new selenium action
	 * 
	 * @param newstep
	 */
	public void onNewStep(Step newstep);
	
	
	/***
	 *  end of the selenium session
	 * 
	 * @param sessionId
	 */
	public void onSessionEnd(JsonObject sessionData);

}
