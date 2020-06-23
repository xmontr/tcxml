package stepWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import org.apache.commons.lang.StringEscapeUtils;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.MultipleStepWrapperRunner;
import tcxml.model.ArgModel;
import tcxml.model.FunctionArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;


public class FunctionWrapper extends AbstractStepWrapper {
	
	
	private HashMap<String, FunctionArgModel> schemaArgs ;
	
	

	public FunctionWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		schemaArgs = new HashMap<String, FunctionArgModel>();
		buildFunctionArgDef();
	}

	public HashMap<String, FunctionArgModel> getSchemaArgs() {
		return schemaArgs;
	}

	@Override
	public String getTitle() {
		String ret = "Function " + step.getAction();
		return ret;
	}

	@Override
	public ArrayList<ArgModel> getDefaultArguments()  throws TcXmlException {
		// TODO Auto-generated method stub
		return new ArrayList<ArgModel>();
	}

	@Override
	public PlayingContext runStep(PlayingContext ctx) throws TcXmlException {
		BoundList<Step> children = step.getStep();
		
		List<AbstractStepWrapper> li = new ArrayList<AbstractStepWrapper>() ;
		for (Step child : children) {
			
		AbstractStepWrapper childwrapper = StepWrapperFactory.getWrapper(child, controller, library);	
	li.add(childwrapper);
		
		
			
		}
		
		controller.getLog().info("running function " + getTitle());
		
		MultipleStepWrapperRunner mc = new MultipleStepWrapperRunner(li, controller);
		ctx = mc .runSteps(ctx);
		
		return ctx;
	}
	private List<AbstractStepWrapper> getChildren() throws TcXmlException {
		
		ArrayList<AbstractStepWrapper> ret = new ArrayList<AbstractStepWrapper>();
		BoundList<Step> li = step.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
				//sanitycheck(firstchild);
		li=firstchild.getStep();
		
		
		
		
		for (Step thestep : li) {
			
		AbstractStepWrapper newwrapper = StepWrapperFactory.getWrapper(thestep, controller, library) ;
			ret.add(newwrapper);
		}
		return ret;
	}
	
	
	
	
	@Override
	
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer(step.getAction()).append( ": async function  ").append("( FuncArgs ){");
		pw.println(sb.toString());
		for (AbstractStepWrapper child : getChildren()) {
			child.export(pw);
			
		}
		
		pw.println("},");
		
	}
	
	
	public String getFunctionName() {
		
	return step.getAction();	
		
	}
	
	
	public JsonObject argumentsToJson( ) {
		
	JsonObjectBuilder ret  = Json.createObjectBuilder();	
	
	Set<String> keys = schemaArgs.keySet();
	
	for (String key : keys) {
		
		JsonObjectBuilder newVal  = Json.createObjectBuilder();
		
		FunctionArgModel thearg = schemaArgs.get(key) ;
		String thename = thearg.getName();
		String thetype = thearg.getType();
		Boolean isopt = thearg.isOptional();
		
		
			newVal.add("type", thetype) ;	

			ret.add(thename, newVal);
		
	}		
		return ret.build();
	}
	
	
	public void saveShemaArgs() {
		JsonObject newval = argumentsToJson();
		final StringWriter writer = new StringWriter();
	    final JsonWriter jwriter = Json.createWriter(writer);
	    jwriter.writeObject(newval);
		
		String argument = writer.toString();
		String escapedargument = StringEscapeUtils.escapeHtml(argument);
		controller.getLog().fine("saving shema  argument for step " + getTitle());
		controller.getLog().fine(escapedargument);
		step.setArgsSchema((escapedargument));
		
	}
	
	
	
	
	public void buildFunctionArgDef() throws TcXmlException {
		
		String src = step.getArgsSchema();			
		HashMap<String, FunctionArgModel> ret = new HashMap<String, FunctionArgModel>() ;
		if(src != null) {
			
			JsonObject arg = controller.readJsonObject(src);
			Set<String> keys = arg.keySet();
			for (String key : keys) {
			 addArgument(key, src);
			
			
		}
			
			
		}
	}

	private void addArgument(String name, String src) throws TcXmlException { 
		JsonObject arg = controller.readJsonObject(src);
		FunctionArgModel  thearg = new FunctionArgModel(name);
		schemaArgs.put(name, thearg);
		
	}
		


}
