package stepWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import tcxml.core.ExecutionContext;
import tcxml.core.JsonObjectWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.UtilsAPI;

import tcxml.model.ArgModel;
import tcxml.model.ListArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

public class GenericApiWrapper extends AbstractStepWrapper {

	public GenericApiWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
	StringBuffer sb  = new StringBuffer("Execute.").append(step.getCategoryName()).append(".").append(step.getMethodName());
		
		return( formatTitle(step.getIndex(), sb.toString()) );
	}
	
	
	public String getCategory() {
		
		return step.getCategoryName();
	}
	
	
	public String getMethod() {
		
		return step.getMethodName();
		
	}
	
	
	
	public void SaveCategoryMethod( String category, String method) {
		
		step.setCategoryName(category);
		step.setMethodName(method);
		
		
		
	}
	
	
	
	

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException{
		ArrayList<ArgModel> ret = new ArrayList<ArgModel>();	
		
		switch (step.getCategoryName()) {
		case "VTS":
			addVtsArgument(ret);
			break;
		case "TC":addTCArgument(ret);break;
			default : 

		
			
		}
		
		return ret;
	}

	private void addTCArgument(ArrayList<ArgModel> ret) {
		switch (step.getMethodName()) {
		 
		case "log":
		addTClogArgument(ret);

			break;
			
			default : break;


		}
		
	}
	
	private void addTClogArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = new ArgModel("text");
		mo.setValue("");
		ret.add(mo);
		
		ListArgModel level = TcxmlUtils.getLogLevelListArgModel("Level", "Standard");
		ret.add(level);
		
		
	}

	private void addVtsArgument(ArrayList<ArgModel> ret) {
		ArgModel mo = null;
		switch (step.getMethodName()) {
		 
		case "vtcConnect":
			
			mo = new ArgModel("serverName");
			mo.setValue("");
			ret.add(mo);
			mo = new ArgModel("port");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("Variable");
			mo.setValue("");
			ret.add(mo);
			
			mo = new ArgModel("vtsName");
			mo.setValue("");
			ret.add(mo);
		break;
		
	
		
		
		
		
		
			
			default : 



		}
		
	}

	
	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		String category = step.getCategoryName();
		String method = step.getMethodName();
		
		
		switch(category) {
		
		case "VTS":ctx = runVtsMethod(method, ctx); break;		
			
			
		
		case "TC": ctx = runTCMethod(method, ctx); break;
			
				
			
		case "Utils":
			ctx = runUtilsMethod(method, ctx); break;
			
			
		case "IO":
			
			controller.getLog().warning(" ****************************** warning not implemented IO ");	
				
				break;
		
		
		
		
		
		
		}
		
		return ctx;
	}
	
	
private PlayingContext runTCMethod(String method, PlayingContext ctx) throws TcXmlException {
		
		PlayingContext ret = null;

		switch (method) {
		
		
		
		case "log":ret = TclogMessage(ctx);	break;

		default:
			throw new TcXmlException("method not implemented " +method , new IllegalAccessException(method));
		}
		
		return ret;
	}

	private PlayingContext TclogMessage(PlayingContext ctx) throws TcXmlException {
		
		ArgModel txtargu = argumentMap.get("text");
		
		ExecutionContext exct = ctx.getCurrentExecutionContext() ;
		String msg = controller.evaluateJsArgument(txtargu, exct );
		controller.getLog().info(msg);
		return ctx;
		
	}

	private PlayingContext runVtsMethod(String method, PlayingContext ctx) throws TcXmlException {
		switch( method) {
		case"vtcConnect": ctx = runVtcConnect(ctx); break;
		case"vtcAddCells": ctx = runVtcAddCells(ctx); break;
		case"vtcPopCells": ctx = runVtcPopCells(ctx); break;
		case"vtcDisconnect": ctx = runVtcDisConnect(ctx); break;
		
		
		
		
		}

		return ctx;
	}

	private PlayingContext runVtcDisConnect(PlayingContext ctx) throws TcXmlException {
		String vtsname = controller.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		JsonObject ret = controller.vtsDisconnect(vtsname);
		return ctx;
	}

	private PlayingContext runVtcAddCells(PlayingContext ctx) throws TcXmlException {
		String vtsname = controller.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		String colnames = controller.evaluateJsArgument(argumentMap.get("colNames"), ctx.getCurrentExecutionContext())	;
		String values = controller.evaluateJsArgument(argumentMap.get("values"), ctx.getCurrentExecutionContext())	;
		String option = controller.evaluateJsArgument(argumentMap.get("option"), ctx.getCurrentExecutionContext())	;


		JsonObject ret = controller.vtsAddCells(vtsname, colnames,values, option);

				return ctx;

	}
	
	
	
	private PlayingContext runVtcPopCells(PlayingContext ctx) throws TcXmlException {
		String vtsname = controller.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		String variable = controller.evaluateJsArgument(argumentMap.get("Variable"), ctx.getCurrentExecutionContext())	;



		JsonObject ret = controller.vtsPopCells(vtsname, variable);
		
		//get the data value
		JsonObject value = ret.getJsonObject("data");
		JsonObjectWrapper jsob = new JsonObjectWrapper(value);
		//controller.addToCurrentJScontext(ctx,variable,value);
		controller.addToCurrentJScontext(ctx,variable,jsob);
		
		
		
		

				return ctx;

	}
	
	

	private PlayingContext runVtcConnect(PlayingContext ctx) throws TcXmlException {
String sname = controller.evaluateJsArgument(argumentMap.get("serverName"), ctx.getCurrentExecutionContext())	;
String sport = controller.evaluateJsArgument(argumentMap.get("port"), ctx.getCurrentExecutionContext())	;
String vtsname = controller.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;


controller.vtsConnect(vtsname, sname, sport);

		return ctx;
	}

	private PlayingContext runUtilsMethod(String method, PlayingContext ctx) throws TcXmlException {
	switch (method) {
	case "clearCookies":	ctx = utilsClearcookies(ctx);
		break;
	case "clearCache":	ctx = utilsClearCache(ctx);	
		break;
			default:throw new TcXmlException(" ****************************** warning not implemented UTILS." + step.getMethodName(), new IllegalArgumentException(step.getMethodName()));	

	}
		return ctx;
	}

	private PlayingContext utilsClearCache(PlayingContext ctx) {
		UtilsAPI util = new UtilsAPI(controller);
		
		util.clearCache();
		
		return ctx;
	}

	private PlayingContext utilsClearcookies(PlayingContext ctx) {
		UtilsAPI util = new UtilsAPI(controller);
		
		util.clearCookies();
		return ctx;
	}
	
	public void export(PrintWriter pw) throws TcXmlException {
	
		pw.println(" // " + getTitle());
		
		
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
		

	pw.println(ret);	
	
	
		
	}
	
	private String exportMethod(String method) throws TcXmlException {
		
		 ArgModel[] lia = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = controller.generateJSobject(lia);
		
		
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


	private Object exportVTSMethod(String method) throws TcXmlException {
		
		
		
		 ArgModel[] lia = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
			String argjs = controller.generateJSobject(lia);
		
		
		
		
			
			String func = "await TC." + method;
			String txt = TcxmlUtils.formatJavascriptFunction(
						func,
						argjs
						
						);
			
			return txt;
	}
	
	private String  exportTCMethod(String method) throws TcXmlException {
		String ret ="";
		switch (method) {
		case "log": ret=exportMethod(method);  		break;

		default: ret = "\\\\************************************ WARNING NOT IMPLEMENTED *********** " + method  ;
		
		}
		return ret;
	}
}
