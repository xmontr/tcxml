package tcxml.test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
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
		
		
assertThat(keys.toArray(new String[keys.size()]), arrayContainingInAnyOrder(action));
		

		
	try {
		BoundList<Step> lif = controller.getFunctionsForLib("SMT");
		
		assertThat(lif.toArray(), arrayWithSize(9));
		
		
	
	
	
	List<String> lifunctname = controller.getFunctionsNameForLib("SMT");
	
	String[] liname = new String[] {
			"Login",
			"BT02 Create New inc",
			"BT02 Affected user",
			"BT02 Type CI",
			"BT02 Title_Description",
			"Logout",
			"Business_Servive",
			
			"CallCodeTime_OTHER",
				"Resolution"
			
			
	};
	
	assertThat(lifunctname.toArray(new String[lifunctname.size()]), arrayContainingInAnyOrder(liname));
	
	
String testobjectid = "testObj:{fa62753e-076f-4b9a-a014-ffd403ce666c}";


TestObject sampletestobject = controller.getTestObjectById(testobjectid);

String samplename = sampletestobject.getManualName();


assertThat(samplename, equalToIgnoringWhiteSpace("Image Link Urgency"));



	
	
		
	} catch (TcXmlException e) {

		e.printStackTrace();
		fail("unable to load function in lib smt ");
	}	
		
		
		
		
	
	}


	}
	
	


