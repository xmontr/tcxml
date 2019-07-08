package tcxml.core.export;

import org.apache.commons.lang.StringEscapeUtils;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class GenericApiExporter extends StepExporter{

	public GenericApiExporter(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step, lib, tcXmlController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String export() throws TcXmlException {
		String category = step.getCategoryName();
		String method = step.getMethodName();
		StringBuffer ret = new StringBuffer();
		
		switch(category) {
		
		case "VTS":ret.append(" //  ****************************** warning not implemented VTS ");		
			
			break;
		
		case "TC":
			
			ret.append(" //  ****************************** warning not implemented TC ");	
			break;
			
		case "Utils":
			ret.append( exportUtilsMethod(method)); break;
			
			
		case "IO":
			
			ret.append(" // ****************************** warning not implemented IO ");	
				
				break;
		
		
		
		
		
		
		}
		
		return ret.toString();
	}

	private String  exportUtilsMethod(String method) throws TcXmlException {
		StringBuffer ret = new StringBuffer();
		switch (method) {
		case "clearCookies":	ret.append(utilsClearcookies()) ;
			break;
		case "clearCache":	 ret.append(utilsClearCache());	
			break;
	
	
				default:throw new TcXmlException(" ****************************** warning not implemented UTILS." + step.getMethodName(), new IllegalArgumentException(step.getMethodName()));	

		}
			return ret.toString();
	}



	private String utilsClearCache() {
		StringBuffer ret = new StringBuffer();
		ret.append("browser.getLocalStorage().clear();");
		
		return ret.toString();
	}

	private String utilsClearcookies() {
		StringBuffer ret = new StringBuffer();
		final String scriptSetAttrValue = " window.postMessage({ action:'deleteAllCookies'}, '*'); ";
		ret.append("browser.executeScript(").append( StringEscapeUtils.escapeJavaScript(scriptSetAttrValue)).append(");");
			
		return ret.toString();
	}

}
