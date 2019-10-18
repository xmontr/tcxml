package stepWrapper;

import java.util.ArrayList;

import javax.json.JsonObject;

import tcxml.core.ExecutionContext;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.UtilsAPI;
import tcxml.core.runner.GenericApiStepRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

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
			
			controller.getLog().info(" ****************************** warning not implemented IO ");	
				
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
		case"vtcDisConnect": ctx = runVtcDisConnect(ctx); break;
		
		
		
		
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
		controller.addToCurrentJScontext(ctx,variable,value);
		
		
		
		

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



}
