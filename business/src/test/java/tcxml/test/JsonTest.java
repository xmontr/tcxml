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
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.swing.SpringLayout.Constraints;

import org.apache.commons.lang.StringEscapeUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class JsonTest {
	
	
	/**
	 * 
	 *  read the file under resource folder for test and return contzint as a sring
	 * 
	 * @param fileresource
	 * @return
	 * @throws IOException
	 */
	
	
	protected  String fileResourceToString(String fileresource) throws IOException {
		
		  URL p = this.getClass().getResource(fileresource);
			
		String json = null;
		
		
		Path pa = null;
		if (System.getProperty("os.name").startsWith("Windows")) {
			
			pa=Paths.get(p.getPath().substring(1));
		}else {
			
			pa=Paths.get(p.getPath());	
		}
		
		

		byte[] b = Files.readAllBytes( pa);
		
		 json = new String(b);
		
		return json;
		
		
	}
	
	
	
	
	@Test
	public void testParsingJSONEvaljavascript() {
		
		
		TcXmlController controller = new TcXmlController("SMT");
		 try {
			 String json =	 fileResourceToString("/argEvalJavascript.json");
		

		
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
			 String json  =  fileResourceToString("/argWaitScript.json");		
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
	
	
@Test
public void testParsingNavigateArg() {
TcXmlController controller = new TcXmlController("SMT");
try {
	String json  =  fileResourceToString("/navigateArgument.json");
	
	JsonObject data = controller.readJsonObject(json);
JsonObject loc = data.getJsonObject("Location");
assertThat(loc, is(notNullValue()));

JsonString val = loc.getJsonString("value");
boolean isjs = loc.getBoolean("evalJavaScript");

//System.out.println(val.toString());
String expected ="\"LR.getParam(\"URL_Base\");\n//\"https://intragate.training.ec.europa.eu/smtweb/index.do\"\n//\"https://intragate.development.ec.europa.eu/smtweb/index.do\"\"";

//assertThat(val.toString(), equalToIgnoringCase(expected));


	
	
	
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
