package core;

import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.ObjectFactory;
import model.TruScript;

public class TcXmlController {
	
	
    private static TcXmlController instance;
    
    
    private Logger log;
    
    
    private TruScript script;
    
    private TcXmlController(){
    	
    	log = Logger.getLogger(TcXmlController.class.getName());
    	
    	
    	
    	
    }
    
    public static synchronized TcXmlController getInstance(){
        if(instance == null){
            instance = new TcXmlController();
        }
        return instance;
    }
    





public void loadXml( String path) throws TcXmlException {
	try {	
    //1. We need to create JAXContext instance
    JAXBContext jaxbContext = null;



    //2. Use JAXBContext instance to create the Unmarshaller.
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    //3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
    JAXBElement<TruScript> unmarshalledObject = 
        (JAXBElement<TruScript>)unmarshaller.unmarshal(
            ClassLoader.getSystemResourceAsStream("problem/expense.xml"));

    //4. Get the instance of the required JAXB Root Class from the JAXBElement.
    script = unmarshalledObject.getValue();
    
    
	jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
} catch (JAXBException e) {
String mess = "fail to load xml with path " + path;
log.severe(mess);

throw ( new TcXmlException(mess, e));
}
	
	
	
	
	
	
}


}