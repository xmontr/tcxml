package tcxml.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.json.JsonObject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.openqa.selenium.By;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruScript;
import tcxml.remote.Express;
import tcxml.remote.RecordingSessionListener;
import tcxml.remote.RemoteRecordingSession;

public class WebdriverTest {

	@Test
	public void test() {
		
		
		URL seleniumdriverurl = null;
		String ctx = "/wd/hub";
		try {
			seleniumdriverurl = new URL("http://localhost:4444/wd/hub");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
		fail("bad driver url ");
		}
		
		
		RecordingSessionListener listener = new RecordingSessionListener() {
			
			@Override
			public void onSessionEnd(	JsonObject data ) {

				System.out.println("session ended " + data);
				
			}
			
			@Override
			public void onSessionStart(JsonObject data) {
				System.out.println("session started " + data);
				
			}
			
			@Override
			public void onNewStep(Step newstep) {
				System.out.println("step added" + newstep);
				
	
				
				try {
					String stepsting = marchallstep(newstep);
					System.out.println(stepsting);
				} catch (JAXBException e) {
					fail("express failure" );
					e.printStackTrace();
				} catch (IOException e) {
					fail("express failure" );
					e.printStackTrace();
				}
				
				
				
				
				
			

				
				
				
			}



	

			@Override
			public void onError(Exception e) {
				System.out.println("error in script execution " + e.getMessage());
				
			}

			@Override
			public void onNewStep(Step fromRemote, By by) {
				// TODO Auto-generated method stub
				
			}

		

		
		};
		
		
		
		
		
		
		
	
		Express express = new Express(9999,ctx, seleniumdriverurl,Optional.empty());
		express.registerRecordingListenner(listener);
		try {
		RemoteRecordingSession rs = express.minuteListen(5);
		
		
		express.unregisterRecordingListener(listener);

		
		
		} catch (TcXmlException e) {
			fail("express failure" );
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

	protected String marchallstep(Step newstep) throws JAXBException, IOException {
		JAXBContext jaxbContext     = JAXBContext.newInstance( Step.class );
		Marshaller jaxbMarshaller   = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");		
		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(newstep, writer);
		writer.close();
		return writer.toString();
	}

}
