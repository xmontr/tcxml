package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

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
	
	
	

}
