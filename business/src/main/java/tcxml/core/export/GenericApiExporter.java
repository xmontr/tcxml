package tcxml.core.export;

import java.util.HashMap;

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
		
		case "VTS":	
		ret.append( exportVTSMethod(method)); break;
			
			
		
		case "TC": ret.append( exportTCMethod(method)); break;
			

			
		case "Utils":
			ret.append( exportUtilsMethod(method)); break;
			
			
		case "IO":
			
			ret.append(" // ****************************** warning not implemented IO ");	
				
				break;
		
		
		
		
		
		
		}
		
		return ret.toString();
	}

	private String  exportTCMethod(String method) throws TcXmlException {
		String ret ="";
		switch (method) {
		case "log": ret=exportMethod(method);  		break;

		default: ret = "\\\\************************************ WARNING NOT IMPLEMENTED *********** " + method  ;
		
		}
		return ret;
	}

	private String exportMethod(String method) throws TcXmlException {
		//HashMap<String, ArgModel> amap = tcXmlController.getArguments(step);
		 ArgModel[] lia = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = tcXmlController.generateJSobject(lia);
		
		
			//avoid confusion between log of TC api and log of LR api
			if(method.equals("log")) {
				method = "logg";
			}
		
			
			String func = "await TC." + method;
			String txt = TcxmlUtils.formatJavascriptFunction(
						func,
						argjs
						
						);
			
			return txt;
	}

	private Object exportVTSMethod(String method) throws TcXmlException {
		
	
		//HashMap<String, ArgModel> amap = tcXmlController.getArguments(step);
		 ArgModel[] lia = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = tcXmlController.generateJSobject(lia);
		
		
		
		
			
			String func = "await TC." + method;
			String txt = TcxmlUtils.formatJavascriptFunction(
						func,
						argjs
						
						);
			
			return txt;
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
		final String scriptSetAttrValue = " window.postMessage({ action:'clearLocalStorage'}, '*'); ";
		ret.append("await browser.executeScript(\"").append(scriptSetAttrValue).append("\");");   
		
		return ret.toString();
	}

	private String utilsClearcookies() {
		StringBuffer ret = new StringBuffer();
		final String scriptSetAttrValue = " window.postMessage({ action:'deleteAllCookies'}, '*'); ";
		ret.append("await browser.executeScript(\"").append(scriptSetAttrValue).append("\");");
			
		return ret.toString();
	}

}
