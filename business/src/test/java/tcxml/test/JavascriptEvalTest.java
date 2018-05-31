package tcxml.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;

import javax.json.JsonObject;
import javax.json.JsonString;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class JavascriptEvalTest extends JsonTest{
	
	
	@Test
	public void testEvaluateJavascript() {
		
	URL p = this.getClass().getResource("/ScriptSample/TC01_OLAF_OCM");
		TcXmlController controller = new TcXmlController("TC01_OLAF_OCM");
		
		
		try {
			controller.loadFromDisk(p.getPath());
		} catch (TcXmlException e) {
			e.printStackTrace();
			fail("unable to load default.xml in /ScriptSample/TC01_OLF_OCM");
		
		}	
		String json;
		try {
			json = fileResourceToString("/sampleEvaluateJSForOlaf.json");
			JsonObject data = controller.readJsonObject(json);
			JsonObject code = data.getJsonObject("Code");
			JsonString js = code.getJsonString("value");
		
			PlayingContext ctx = new PlayingContext(controller);
			controller.evaluateJS(js.getString(), ctx );
			
			assertThat(ctx.getGlobalJsVariable("theselectorname"), instanceOf(String.class) );
			assertThat(ctx.getGlobalJsVariable("theselectorname"), equalTo("CSIRIBAN") );
			
		;	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("unable to read sampleEvaluateJSForOlaf.json");
		} catch (TcXmlException e) {
						e.printStackTrace();
						fail("unable to read json in sampleEvaluateJSForOlaf.json");
		}
		
	}
	
	
	@Test
	public void TestEvaluateLocationInNavigateArg() {
		
		URL p = this.getClass().getResource("/ScriptSample/SMT");
		TcXmlController controller = new TcXmlController("SMT");
		
		
		try {
			controller.loadFromDisk(p.getPath());
		} catch (TcXmlException e) {
			e.printStackTrace();
			fail("unable to load default.xml in /ScriptSample/SMT");
		
		}
		
		
		
		
	
			String json;
			try {
				json = fileResourceToString("/navigateArgument.json");
				JsonObject data = controller.readJsonObject(json);
				JsonObject loc = data.getJsonObject("Location");
				assertThat(loc, is(notNullValue()));

				JsonString val = loc.getJsonString("value");
				
				boolean isjs = loc.getBoolean("evalJavaScript");
				
			PlayingContext ctx= new PlayingContext(controller);
			Object location = controller.evaluateJS(val.getString(),ctx);
			
				
			assertThat(location, equalTo("https://intragate.stress.ec.europa.eu/smtweb"));	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("unable to read navigateArgument.json");
			} catch (TcXmlException e) {
			
				e.printStackTrace();
				fail("fail to evaluate navigate location argument");
			}
			

		
		
		
		
		
		
		
	}
	}



