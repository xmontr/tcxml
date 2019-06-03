package tcxml.core.runner;

import tcxml.core.PlayingContext;
import tcxml.core.StepRunner;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

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
		
		case "VTS":log.info(" ****************************** warning not implemented VTS ");		
			
			break;
		
		case "TC":
			
			log.info(" ****************************** warning not implemented TC ");	
			
		case "UTILS":
			ctx = runUtilsMethod(method, ctx); break;
			
			
		case "IO":
			
			log.info(" ****************************** warning not implemented IO ");	
				
				break;
		
		
		
		
		
		
		}
		
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
		// TODO Auto-generated method stub
		return ctx;
	}

	private PlayingContext utilsClearcookies(PlayingContext ctx) {
		// TODO Auto-generated method stub
		return ctx;
	}



}
