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
import tcxml.model.TruLibrary;

public class ModelTest {

	@Test
	public void testLoadSMTSample() {
		//C:\bin\eclipse\runtime-EclipseApplication\first project\Test Cases\SMT
		//"/ScriptSample/SMT"
	URL p = this.getClass().getResource("/ScriptSample/SMT");
	

		TcXmlController controller = new TcXmlController("SMT");
			
		try {
			controller.loadFromDisk(p.getPath());
		} catch (TcXmlException e) {
			e.printStackTrace();
			fail("unable to load default.xml ");
		
		}
		
		
		Map<String, Step> actionmap = controller.getActionMap();
		
		Map<String, TruLibrary> libmap = controller.getLibraries();
		Set<String> libs = libmap.keySet();
		
		String[] libraries = new String[] {
				"SMT"
				
				
		};
		
		
		
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
		
		for (String li : libraries) {
			checkPresence(li,libs);
			
		}
		

		
		
		
		
		
		
	
	}

	private void checkPresence(String action, Set<String> keys) {
		boolean hasinit = keys.contains("Init");
		
		assertTrue("found Init action", hasinit);
		
	}

}
