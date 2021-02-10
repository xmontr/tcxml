package tcxml.remote;

import javax.json.JsonObject;

import org.openqa.selenium.By;

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

	
	
	/***
	 * 
	 *  new selenium element identified with By
	 * 
	 * @param newstep
	 */
	public void onNewSelector(String  elemntid);

}
