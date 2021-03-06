package tcxml.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.RunLogicWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

public class FullApiTest {

	@Ignore
	public void test() {
		try {
		File basedir = new File("C:\\vugenworkspace\\testApi");
		
		TcXmlController controller = new TcXmlController("testapi");		
	
			controller.loadFromDisk(basedir.getAbsolutePath());
			
			
			 RunLogicWrapper runlogic = controller.getRunLogic();
			
			
			
			
			String driverPath = "C:\\bin\\seleniumDriver\\chrome_version_76\\chromedriver.exe";
			controller.openBrowser("chome", driverPath );
			
			PlayingContext ctx = new PlayingContext(controller);
			runlogic.play(ctx);
			
			
			
			
			
			
			
		} catch (TcXmlException e) {
		fail( e.getMessage());
		}
		
		
	}

}
