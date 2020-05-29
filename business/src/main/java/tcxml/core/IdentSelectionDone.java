package tcxml.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class IdentSelectionDone implements ExpectedCondition<Boolean> {
	
	private TcXmlController controller;

	@Override
	public Boolean apply(WebDriver input) {
	boolean ret = false;
	final JavascriptExecutor js = (JavascriptExecutor) controller.getDriver();
	final String scriptjs = "return document.getElementById('identStorage').getAttribute('status');";
	String status = (String) js.executeScript(scriptjs);
	controller.getLog().fine("waiting for the completion of the selection, identbox status is :" + status);
	if(status != null &&  !status.equals("run")) {
		
		ret = true;
	}
	
	
	
	
	
		return ret;
	}

	public IdentSelectionDone(TcXmlController controller) {
		super();
		this.controller = controller;
	}

}
