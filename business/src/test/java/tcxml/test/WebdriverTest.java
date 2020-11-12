package tcxml.test;

import static org.junit.Assert.*;

import org.junit.Test;

import tcxml.core.TcXmlException;
import tcxml.remote.Express;

public class WebdriverTest {

	@Test
	public void test() {
	
		Express express = new Express(9999);
		try {
			express.minuteListen(3);
		} catch (TcXmlException e) {
			fail("express failure" );
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

}
