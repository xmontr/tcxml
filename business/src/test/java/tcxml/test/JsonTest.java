package tcxml.test;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.SpringLayout.Constraints;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class JsonTest {
	
	
	
	@Test
	public void testParsingJSONEvaljavascript() {
		
		
		TcXmlController controller = new TcXmlController("SMT");
		 try {
		
		
		  URL p = this.getClass().getResource("/argEvalJavascript.json");
		
		String json = null;
		
		Path pa = Paths.get(p.getPath().substring(1));
		

		byte[] b = Files.readAllBytes( pa);
		
		json = new String(b);
		

		
		String ret = controller.JSCodefromJSON(json);
	
	
	assertThat(ret, containsString("document.createNSResolver( contextNode.ownerDocument == null ? contextNode.documentElement"));
	
	
		
	} catch (IOException  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		fail(" fail to read file argEvalJavascript.json");
	} catch (TcXmlException e) {
		fail(" fail to parse json structure  argEvalJavascript.json");
		e.printStackTrace();
	}

		
		
		
	}
	
	@Test
	public void testParsingJSONWait() {
		TcXmlController controller = new TcXmlController("SMT");
		 try {
		
		
		  URL p = this.getClass().getResource("/argWaitScript.json");
		
		String json = null;
		
		Path pa = Paths.get(p.getPath().substring(1));
		

		byte[] b = Files.readAllBytes( pa);
		
		json = new String(b);
		
	JsonObject data = controller.readJsonObject(json, "Interval");
	
	assertThat(data, not(nullValue()));
	
	  assertThat( data.containsKey("evalJavaScript"), is(true)); 
	
	boolean tt = data.getBoolean("evalJavaScript");
	

	
	assertThat(tt, is(true));
	
	String interval = data.getString("value");
	
	assertThat(interval, equalToIgnoringCase("3"));
		
		
		
		 }catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(" fail to read file argWaitScript.json");
			} catch (TcXmlException e) {
				fail(" fail to parse json structure  argWaitScript.json");
				e.printStackTrace();
			e.printStackTrace();
		}
	}
	

}
