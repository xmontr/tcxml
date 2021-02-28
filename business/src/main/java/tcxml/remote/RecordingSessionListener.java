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
	 *  end of the selenium session
	 * 
	 * @param sessionId
	 */
	public void onSessionEnd(JsonObject sessionData);

	
	
	
	public void onError ( Exception e);
	
	/***
	 * 
	 *  new selenium action
	 * 
	 * @param newstep
	 */

	public void onNewStep(Step fromRemote, By by);
	
	public void onNewStep(Step fromRemote);

}
