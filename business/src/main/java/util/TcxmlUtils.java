package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.FileChooserUI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import tcxml.core.TcXmlException;
import tcxml.core.parameter.StepParameter;
import tcxml.core.parameter.TableParameter;

public class TcxmlUtils {
	
	
	/**
	 *  search in the .prm paramerter file the files associated to the table parameters
	 * 
	 * @param path
	 * @return
	 * @throws TcXmlException 
	 */
	
	public static List<String> listFilesinParameterfiles(File parameterFile) throws TcXmlException {
		
		
		
		
		ArrayList<String> ret = new ArrayList<String>() ;
		
		HierarchicalINIConfiguration conf;
		try {
			conf = new HierarchicalINIConfiguration(parameterFile);
		} catch (ConfigurationException e) {
		throw new TcXmlException("unable to parse parameter config file", e);
		}
	
		Set lisection = conf.getSections();
		Iterator it = lisection.iterator();
		while (it.hasNext()) {
			String  secname = (String ) it.next();	
			 SubnodeConfiguration se = conf.getSection(secname);
				String type = se.getString("Type");	
				if(type.equals("Table")) {
					TableParameter tp = new TableParameter(conf,secname);
					String path = tp.getTable();
					String basedir = parameterFile.getParentFile().getAbsolutePath() + File.separator;
					ret.add(basedir + tp.getTable() );	
					
				
					
					
					
				}

		
		
		}
		
		
		
		
		
		
		
	return ret;	
	}
	
	
	public static List<String> listExtraFilesinUsrfiles(File usrFile) throws TcXmlException {
		
		
		
		
		ArrayList<String> ret = new ArrayList<String>() ;
		
		HierarchicalINIConfiguration conf;
		try {
			conf = new HierarchicalINIConfiguration(usrFile);
		} catch (ConfigurationException e) {
		throw new TcXmlException("unable to parse usr config file", e);
		}
		
		SubnodeConfiguration se =	conf.getSection("ManuallyExtraFiles");
		
		
		Iterator keys = se.getKeys();
		while (keys.hasNext()) {
			String file = (String) keys.next();
			String basedir = usrFile.getParentFile().getAbsolutePath() + File.separator;
			ret.add(basedir + file );			 // bug 2 points in string
		}		
	return ret;	
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 *  root tag for xml file doesn't contain always a namespace. prevent jaxb from mapping to java classes.
	 * 
	 * 
	 * @param source
	 * @throws TcXmlException 
	 */
	
	public static File addNameSpaceToXmlFile( File source  ) throws TcXmlException {
		
		try {
			
			String namespace="http://www.example.org/tcxml";
			File ret = File.createTempFile("tcxml", "xml");
			
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
			Document doc = dBuilder.parse(source);
			  Element documentElement = doc.getDocumentElement();
			 documentElement.setAttribute("xmlns", namespace);
			 String xml = transformXmlNodeToXmlString(documentElement);
			PrintStream ps = new PrintStream(ret);
			ps.println(xml);
			
			
			
			return ret;
			
		} catch (Exception e) {
throw new TcXmlException("fail to add name space to file" + source.getAbsolutePath(), e);
		}
		
		
		
		
		
		
		
	}

	private static String transformXmlNodeToXmlString(Node node)   throws TransformerException {
	    	    TransformerFactory transFactory = TransformerFactory.newInstance();
	    	    Transformer transformer = transFactory.newTransformer();
	    	    StringWriter buffer = new StringWriter();
	    	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    	    transformer.transform(new DOMSource(node), new StreamResult(buffer));
	    	    String xml = buffer.toString();
	    	    return xml;
	}
	

}
