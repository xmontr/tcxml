package tcxml.test;

import java.io.InputStream;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class MyTest {

	public static void main(String[] args) {

		
		
		loadtestfile("smt.xml");

	}

	private static void loadtestfile(String file) {
		
	InputStream in = MyTest.class.getResourceAsStream(file)	;
	TcXmlController controller = TcXmlController.getInstance();
	try {
		controller.loadXml(in);
	} catch (TcXmlException e) {
		System.out.println(" fail to load xml : smt");
		e.printStackTrace();
	}
		
	}

}
