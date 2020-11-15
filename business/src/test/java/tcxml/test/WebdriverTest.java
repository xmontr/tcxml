package tcxml.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import tcxml.core.TcXmlException;
import tcxml.remote.Express;

public class WebdriverTest {

	@Test
	public void test() {
		
		
		URL seleniumdriverurl = null;
		try {
			seleniumdriverurl = new URL("http://localhost:4444/wd/hub");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
		fail("bad driver url ");
		}
	
		Express express = new Express(9999, seleniumdriverurl);
		try {
			express.minuteListen(3);
		} catch (TcXmlException e) {
			fail("express failure" );
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

}
