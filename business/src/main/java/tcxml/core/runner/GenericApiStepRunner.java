package tcxml.core.runner;

import javax.json.JsonObject;

import tcxml.core.ExecutionContext;
import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.UtilsAPI;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;


@Deprecated
public class GenericApiStepRunner extends StepRunner {

	public GenericApiStepRunner(Step step, TruLibrary lib, TcXmlController tcXmlController) throws TcXmlException {
		super(step, lib, tcXmlController);
		// TODO Auto-generated constructor stub
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
			
			log.info(" ****************************** warning not implemented IO ");	
				
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
		String msg = tcXmlController.evaluateJsArgument(txtargu, exct );
		tcXmlController.getLog().info(msg);
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
		String vtsname = tcXmlController.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		JsonObject ret = tcXmlController.vtsDisconnect(vtsname);
		return ctx;
	}

	private PlayingContext runVtcAddCells(PlayingContext ctx) throws TcXmlException {
		String vtsname = tcXmlController.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		String colnames = tcXmlController.evaluateJsArgument(argumentMap.get("colNames"), ctx.getCurrentExecutionContext())	;
		String values = tcXmlController.evaluateJsArgument(argumentMap.get("values"), ctx.getCurrentExecutionContext())	;
		String option = tcXmlController.evaluateJsArgument(argumentMap.get("option"), ctx.getCurrentExecutionContext())	;


		JsonObject ret = tcXmlController.vtsAddCells(vtsname, colnames,values, option);

				return ctx;

	}
	
	
	
	private PlayingContext runVtcPopCells(PlayingContext ctx) throws TcXmlException {
		String vtsname = tcXmlController.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;
		String variable = tcXmlController.evaluateJsArgument(argumentMap.get("Variable"), ctx.getCurrentExecutionContext())	;



		JsonObject ret = tcXmlController.vtsPopCells(vtsname, variable);
		
		//get the data value
		JsonObject value = ret.getJsonObject("data");
		tcXmlController.addToCurrentJScontext(ctx,variable,value);
		
		
		
		

				return ctx;

	}
	
	

	private PlayingContext runVtcConnect(PlayingContext ctx) throws TcXmlException {
String sname = tcXmlController.evaluateJsArgument(argumentMap.get("serverName"), ctx.getCurrentExecutionContext())	;
String sport = tcXmlController.evaluateJsArgument(argumentMap.get("port"), ctx.getCurrentExecutionContext())	;
String vtsname = tcXmlController.evaluateJsArgument(argumentMap.get("vtsName"), ctx.getCurrentExecutionContext())	;


tcXmlController.vtsConnect(vtsname, sname, sport);

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
		UtilsAPI util = new UtilsAPI(tcXmlController);
		
		util.clearCache();
		
		return ctx;
	}

	private PlayingContext utilsClearcookies(PlayingContext ctx) {
		UtilsAPI util = new UtilsAPI(tcXmlController);
		
		util.clearCookies();
		return ctx;
	}



}
