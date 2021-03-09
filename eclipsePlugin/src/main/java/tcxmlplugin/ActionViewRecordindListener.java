package tcxmlplugin;

import javax.json.JsonObject;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.remote.RecordingSessionListener;
import tcxmlplugin.composite.ActionView;

public class ActionViewRecordindListener implements RecordingSessionListener{
	
	
	private ActionView theactionview ;
	
	private int stepIndex;
	
	
	public ActionViewRecordindListener( ActionView theview) {
		
		this.theactionview = theview ;
		this.stepIndex = 0 ;
		
		
		
	}



	@Override
	public void onNewStep(Step arg0) {
		
		
		System.out.println("*** new step ****************");
		
		this.stepIndex++;
		arg0.setIndex(new Integer(stepIndex).toString());
		theactionview.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					theactionview.addStep(arg0);
				} catch (TcXmlException e) {
					
					TcXmlPluginController.getInstance().error("fail to add step into recorded action", e);
				}
				
			}
		});
		
		

		
	}

	@Override
	public void onSessionEnd(JsonObject arg0) {
		System.out.println( "*********** session end ");
		
	}

	@Override
	public void onSessionStart(JsonObject arg0) {
		System.out.println("********session start***************");
		this.stepIndex =0;
		
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onNewStep(Step step, By by) {
		
		try {
			String xpath = by.toString().substring(10) ;
			
			TestObject to = theactionview.getController().generateNewTestObjectWithXpath(null, xpath);
			
			theactionview.getController().computeNames( by,to);
			
			
			step.setTestObject(to.getTestObjId());
		} catch (TcXmlException e1) {
			TcXmlPluginController.getInstance().error("fail to add step into recorded action", e1);
		}
		this.stepIndex++;
		step.setIndex(new Integer(stepIndex).toString());
		theactionview.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					theactionview.addStep(step);
				} catch (TcXmlException e) {
					
					TcXmlPluginController.getInstance().error("fail to add step into recorded action", e);
				}
				
			}
		});
		
		
		
		
		
	}

}
