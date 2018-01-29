package tcxml.test;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SpringLayout.Constraints;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class JsonTest {
	
	
	
	@Test
	public void testParsingJSON() {
		
		
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
	
	
	

}
