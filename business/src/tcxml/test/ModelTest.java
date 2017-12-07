package tcxml.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

public class ModelTest {

	@Test
	public void testLoadSMTSample() {
		
		InputStream in = ModelTest.class.getResourceAsStream("smt.xml")	;
		TcXmlController controller = TcXmlController.getInstance();
		try {
			controller.loadXml(in);
		} catch (TcXmlException e) {
			fail("unable to load smt.xml");
		}
		
		
		Map<String, Step> actionmap = controller.getActionMap();
		
		Set<String> keys = actionmap.keySet();
		
	boolean hasinit = keys.contains("init");
	
	assertTrue("found init action", hasinit);
		
		
		
		
		
		
	
	}

}
