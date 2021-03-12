package stepWrapper;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringEscapeUtils;

import tcxml.core.Playable;
import tcxml.core.PlayingContext;
import tcxml.core.ProgressType;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.ListArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import util.TcxmlUtils;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;




public abstract class AbstractStepWrapper implements Playable, PropertyChangeListener{
	
	
	public Step getStep() {
		return step;
	}

	public TruLibrary getLibrary() {
		return library;
	}

	public TcXmlController getController() {
		return controller;
	}

	/***
	 * 
	 *  the step encapsiled by the wrapper 
	 */
	protected Step step ;
	
	/***
	 * 
	 *  reference the controller 
	 */
	protected TcXmlController controller;
	
	/***
	 * 
	 *  the library if the encapsuled step belongs to a library
	 * 
	 */
	protected TruLibrary library ;
	
	/***
	 * 
	 *  the map of the argument of the step
	 */
	protected HashMap<String, ArgModel> argumentMap;
	
	/***
	 * 
	 *  the map of the default argument
	 * 
	 */
	
	protected PropertyChangeSupport propertyChangeSupport;
	
	protected HashMap<String, ArgModel> defaultArgumentMap ;
	
	public AbstractStepWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super();
		this.step = step;
		this.controller = controller;
		this.library = library;
		propertyChangeSupport = new PropertyChangeSupport(this);
		
		defaultArgumentMap = buildDefaultArgumentMap();
		
		argumentMap = buildArgumentMap();
		
	step.addPropertyChangeListener(this);	
	}
	
	
	
	
	private HashMap<String, ArgModel> buildDefaultArgumentMap() throws TcXmlException {
		HashMap<String, ArgModel> ret = new HashMap<String, ArgModel>();
		
		ArrayList<ArgModel> li = getDefaultArguments();
		for (ArgModel argModel : li) {
			ret.put(argModel.getName(), argModel);
			
			
		}
		return ret;
	}

	private HashMap<String, ArgModel> buildArgumentMap() throws TcXmlException {
		String src = step.getArguments();			
		HashMap<String, ArgModel> ret = new HashMap<String, ArgModel>() ;
		if(src != null) {
			
			JsonObject arg = controller.readJsonObject(src);
			Set<String> keys = arg.keySet();
			for (String key : keys) {
			
				addArgument(ret, src, key);
			}
			
			
		}

 // add default arg
		
for (ArgModel val : getDefaultArguments()) {
	String k = val.getName();
	if( !ret.containsKey(k)) {
		ret.put(k, val);
		
	}
	

}
		
		
		
		
		
		
		
		
		
	
		return ret;
	}
	
	
	private  void addArgument(HashMap<String, ArgModel> li , String src, String name  ) throws TcXmlException {
		
		JsonObject arg = controller.readJsonObject(src);
		ListArgModel defaultli = getListArgument(name);
		if(defaultli != null ) {
			ListArgModel p = new ListArgModel(name, defaultli.getValueList());
			p.populateFromJson(arg.getJsonObject(name));
			li.put( name,p);
			
			
		} else { // single value argument
			
			
			ArgModel p = new ArgModel(name);				
			p.populateFromJson(arg.getJsonObject(name));
			li.put( name,p);
			
		}
		
		

		
		
	}
	
	
	/**
	 *  return the deafult argument that is a list with the corresponding name or null
	 * 
	 * 
	 * @param name
	 * @return
	 */

	private ListArgModel getListArgument(String name) {
		ListArgModel ret = null;;
	if( defaultArgumentMap.containsKey(name))	{
	ArgModel ar = defaultArgumentMap.get(name);
	if(ar instanceof ListArgModel) {
		ret = (ListArgModel)ar;
	}
		
		
	}
		
		return ret;
	}

	public HashMap<String, ArgModel> getArgumentMap() {
		return argumentMap;
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		if(isDisabled()) { // don't play a dsabled step
			
	controller.getLog().info("step " + getTitle() + " is disabled. don't play it.");		
			return ctx ;
		}
		controller.manageStartStopTransaction(this,ProgressType.ACTIONSTARTED,ProgressType.STEPSTARTED);
		
		controller.getLog().info("start step " + getTitle() + " with  disabled=" + isDisabled());
		PlayingContext ret = runStep(ctx);
		controller.manageStartStopTransaction(this,ProgressType.ACTIONCOMPLETED,ProgressType.STEPCOMPLETED,ProgressType.AFTERSTEPCOMPLETED,ProgressType.AFTERSTEPENDED);
		
		return ret;
	}
	
	public abstract  String getTitle() throws TcXmlException ;
	
	
	protected abstract PlayingContext runStep (PlayingContext ctx) throws TcXmlException ;
	
	
	public  Step getModel() {
		return step;
		
	}
	
	public boolean isDisabled() {
		boolean ret =false ;
		
		if(step.isDisabled() != null && step.isDisabled()) {
			
			ret= true;
		} else {
			
			ret = false;
		}
		
	
	

		
//System.out.println("xav*** step index " + step.getIndex() + " id=" + step.getStepId() + " ret="+ret + "  step.isDisabled()="+ step.isDisabled());		
	return ret;	
		
	}
	
	
	public String getLevel() {
		
		String level = step.getLevel();
		if(level == null || level.isEmpty()) {
			level ="1";
			
		}	
		return level;
		
	}
	
	
	public String getIndex() {
		
		return step.getIndex();
		
	}
	
	
	
	 protected String formatTitle (String index , String txt) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" ").append(index).append(" ").append(txt);
		
		//add tag ifstep disabled
		
		if( isDisabled()) {
			
			sb.append(" - disabled");
		}
		
		return sb.toString();
		
	}
	 
	 
	 public abstract  ArrayList<ArgModel>  getDefaultArguments() throws TcXmlException ;
	 
	 
	 public abstract void export(PrintWriter pw) throws TcXmlException ;
	 
	 
		protected String genericExport(String targetFuncName) {
			
			
			 ArgModel[] li = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
				String argjs = controller.generateJSobject(li);	
				
				
			
				String ret = TcxmlUtils.formatJavascriptFunction(
						targetFuncName,
							argjs  
						
							);
				return ret;
			}

		

		
	/**
	 * 
	 * 
	 *  save the all the arguments of the wrapper  into the wrapped step	
	 *
	 * @throws TcXmlException 
	 */
		
	public void saveArguments( ) throws TcXmlException	{

		JsonObject newval = controller.argumentsToJson(argumentMap);
		final StringWriter writer = new StringWriter();
	    final JsonWriter jwriter = Json.createWriter(writer);
	    jwriter.writeObject(newval);
		
		String argument = writer.toString();
		String escapedargument = StringEscapeUtils.escapeHtml(argument);
		controller.getLog().fine("saving argument for step " + getTitle());
		controller.getLog().fine(escapedargument);
		step.setArguments(escapedargument);
		
		
		
		
	}
	
	public void saveAction(String thenewaction) {
		step.setAction(thenewaction);
		
		
	}
	
	
	

	public String getStepId() {
		// TODO Auto-generated method stub
		return step.getStepId();
	}
	
	public void addPropertyChangeListener(
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener( listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }
		
	
		  
		  
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("index")) {  
					
					controller.getLog().fine("step id "  +  getStepId() + " change index :old=" + evt.getOldValue() + " new=" + evt.getNewValue());
					

					
				}
				
			}

			protected void fireTitleChange(String oldtitle, String newTitle) {
				propertyChangeSupport.firePropertyChange("title", oldtitle,newTitle);
				
			}
			
			
			public void setIndex( int newIndex) {
				try {
					String oldtitle = getTitle();
					step.setIndex(new Integer(newIndex).toString());
					String newtitle = getTitle();
					fireTitleChange(oldtitle, newtitle);
					
				} catch (TcXmlException e) {
					
					controller.getLog().severe("failure in generating title" +e.getMessage());
				}
				
				
				
			}
			
			
			public String marshallStep() throws TcXmlException {
				String ret = null;
				try {
					JAXBContext jaxbContext     = JAXBContext.newInstance( Step.class );
					Marshaller jaxbMarshaller   = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
					jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");				
					
					  jaxbMarshaller.setProperty(
					  "com.sun.xml.bind.characterEscapeHandler", new
					  CharacterEscapeHandler() { public void escape(char[] ch, int start, int
					  length, boolean isAttVal, Writer writer) throws IOException {
					  writer.write(ch, start, length); } });
					 
					
					
					
					
					StringWriter writer = new StringWriter();

					jaxbMarshaller.marshal(step,  writer);
					ret = writer.toString();
					return ret;
					
				} catch (Exception e) {
					throw new TcXmlException("fail to marschal step " , e);
				}
				
			}
		  
		  

}




