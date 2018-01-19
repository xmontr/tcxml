package tcxml.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

public class ModelTest {

	@Test
	public void testLoadSMTSample() {
	URL p = this.getClass().getResource("/ScriptSample/SMT/");
	

		TcXmlController controller = new TcXmlController("SMT");
			
		try {
			controller.loadFromDisk(p.getPath());
		} catch (TcXmlException e) {
			e.printStackTrace();
			fail("unable to load default.xml ");
		
		}
		
		
		Map<String, Step> actionmap = controller.getActionMap();
		
		Set<String> keys = actionmap.keySet();
		
		String[] action = new String[] {
				"Init",
				"Action",
				"TC01",
				"TC02",
				"TC03",
				"End",
				
				
		};
		
		
		for (String act : action) {
			checkPresence(act,keys);
			
		}
		

		
		
		
		
		
		
	
	}

	private void checkPresence(String action, Set<String> keys) {
		boolean hasinit = keys.contains("Init");
		
		assertTrue("found Init action", hasinit);
		
	}

}
