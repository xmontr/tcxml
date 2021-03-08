package tcxml.remote.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Repeatable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;

import tcxml.core.TcXmlController;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.remote.DriverRequestHandler;
import tcxml.remote.RemoteRecordingSession;

public abstract class AbstractHandler implements HttpRequestHandler{
	
	
	protected Map<String, String> param;
	private  final HttpProcessor outhttpproc ;
	private  final HttpRequestExecutor httpexecutor ;

	protected Logger log;
	protected String requestMethod;
	protected String currentRequest;
	protected Optional<SessionId> seleniumSessionId;
	
	
	
	
	
	protected AbstractHandler(Map<String, String> p,Optional<SessionId> seleniumSessionId ) {
	
		this.seleniumSessionId = seleniumSessionId;
		this.param =p;
        // Set up HTTP protocol processor for outgoing connections
         outhttpproc = new ImmutableHttpProcessor(
                new RequestContent(),
                new RequestTargetHost(),
                new RequestConnControl(),
                new RequestUserAgent("Test/1.1"),
                new RequestExpectContinue(false));
         
         // Set up outgoing request executor
         
        
         
          httpexecutor = new HttpRequestExecutor(); 
          
          
      	log = Logger.getLogger(getClass().getName());
      	log.setLevel(Level.ALL);
	}
	
	public abstract void processRequest(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) ;
	
	public abstract void processResponse(JsonObject thejsonCommand, RemoteRecordingSession recordingSession) ;
	
	protected void dumpParams() {
		
	
	Set<String> listkey = param.keySet();
	for (String key : listkey) {
		log.info("handler parameter:" + key + " value:" + param.get(key) );
		
	}
			
		}
	
	
	protected void storeStepInsession(Step theStep, By by, RemoteRecordingSession theRecordindSession) {
		Optional<RemoteRecordingSession> rs = Optional.ofNullable(theRecordindSession);
		Optional<String> targetSession = Optional.ofNullable(param.get("sessionId"));
		
		if(!targetSession.isPresent()) {
			throw new IllegalStateException("no target session for storing step");
			
		}
		
		if(rs.isPresent()) {
			
		if(!targetSession.get().equals(rs.get().getSessionId()))	{
		
			throw new IllegalStateException("missmatch session for storing step,found:"+targetSession.get() +" expected was:"+rs.get().getSessionId());
			
		}else {// store the step in the session
			
			rs.get().addStep(theStep,by);	
			
		}
			
			
			
		}		
		
	}
	
	
	protected void storeStepInsession(Step theStep, RemoteRecordingSession theRecordindSession) {
		Optional<RemoteRecordingSession> rs = Optional.ofNullable(theRecordindSession);
		Optional<String> targetSession = Optional.ofNullable(param.get("sessionId"));
		
		if(!targetSession.isPresent()) {
			throw new IllegalStateException("no target session for storing step");
			
		}
		
		if(rs.isPresent()) {
			
		if(!targetSession.get().equals(rs.get().getSessionId()))	{
		
			throw new IllegalStateException("missmatch session for storing step,found:"+targetSession.get() +" expected was:"+rs.get().getSessionId());
			
		}else {// store the step in the session
			
			rs.get().addStep(theStep);	
			
		}
			
			
			
		}		
		
	}
	
	
	
	
	
	
	
	protected void storeSelectorInsession(By theSelector,String elemntid, RemoteRecordingSession theRecordindSession) {
		Optional<RemoteRecordingSession> rs = Optional.ofNullable(theRecordindSession);
		Optional<String> targetSession = Optional.ofNullable(param.get("sessionId"));
		
		if(!targetSession.isPresent()) {
			throw new IllegalStateException("no target session for storing selector");
			
		}
		
		if(rs.isPresent()) {
			
		if(!targetSession.get().equals(rs.get().getSessionId()))	{
		
			throw new IllegalStateException("missmatch session for storing selector,found:"+targetSession.get() +" expected was:"+rs.get().getSessionId());
			
		}else {// store the step in the session
			
			rs.get().addKnownElements(theSelector, elemntid);
			
		}
			
			
			
		}		
		
	}
	
	
	
	
	protected boolean commandSuccess(JsonObject thejsonCommand) {
		boolean ret = false;
		
		if(thejsonCommand == null) {
			
			throw new IllegalStateException("json command cannot be null");
		}
		
		if( thejsonCommand.get("status") == null ) {
			
			throw new IllegalStateException(" json response has no status :"+thejsonCommand);
			
		}
		
		int status = thejsonCommand.getInt("status");
		if(status == 0) {
			ret = true;}
			
		return ret ;
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	/***
	 * 
	 * 
	 * 
	 * @param entity
	 * @return true is the content type of the entity is set to application/json
	 */
	protected boolean isJsonCommand( HttpEntity entity) {
		
		boolean ret = false;
        ContentType contentype = ContentType.parse(entity.getContentType().getValue());
        
        ContentType expectedContentType = ContentType.APPLICATION_JSON.withCharset(contentype.getCharset());
        
        if(contentype.toString().equalsIgnoreCase(expectedContentType.toString())) {
		
		ret = true;	
		
	}else {
		log.info("unexpected response content type :" + contentype + "-------  expected was " +expectedContentType.toString());
		
	}
      return ret;  
	}
	
	
	
	protected JsonObject readJson(String contentstring) {
		Reader reader = new StringReader(contentstring);
		JsonReader jr = Json.createReader(reader );
		JsonObject ret = jr.readObject();
		jr.close();
		return ret;
		
	}
	
	
	
	
	
	@Override
	public synchronized void  handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		Instant startHandle = Instant.now();
		 JsonObject jsonRequestCommand =null;
		 JsonObject jsonResponseCommand=null ;
		RemoteRecordingSession recordingSession = (RemoteRecordingSession) context.getAttribute(DriverRequestHandler.RECORDINGSESSION);
		
		//store the method
		requestMethod = request.getRequestLine().getMethod().toLowerCase();
		
		// re write the session id with the pre-exsiting one if necessary
		Optional<SessionId> possibleseleniumsession = (Optional<SessionId>) context.getAttribute(DriverRequestHandler.SELENIUMSESSIONID);
		if(possibleseleniumsession.isPresent()) {
					
			
			request =rewriteUrlRequest(request);
			
		}
		
		//store the uri
		
		
		currentRequest = request.getRequestLine().getUri();
//       // final DefaultBHttpClientConnection conn = (DefaultBHttpClientConnection) context.getAttribute(DriverRequestHandler.HTTP_OUT_CONN);
		 final HttpClientConnection conn = (HttpClientConnection) context.getAttribute(DriverRequestHandler.HTTP_OUT_CONN);
		
      HttpHost target =   (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        



        context.setAttribute(HttpCoreContext.HTTP_CONNECTION, conn);
        context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST,target);
        
        log.info(">> Request URI: " + request.getRequestLine().getUri());
        
        //dump header
        
      Header[] allheader = request.getAllHeaders();
      StringBuffer headerlist = new StringBuffer();
      for (Header header : allheader) {
    	  headerlist.append(header.getName()).append(":").append(header.getValue()).append("\n");
		
	}
      log.info("header request:" +  headerlist.toString());
      //dump body
      
      Instant startProcessingRequest =null;
      Instant stopProcessingRequest = null;
      
       if ( request instanceof BasicHttpEntityEnclosingRequest   ) {
    	   
    	   
   	   
    HttpEntity entity = ((BasicHttpEntityEnclosingRequest) request).getEntity() ;
    String thestringRequestcontent = EntityUtils.toString(entity) ;
if(isJsonCommand(entity)) {
	
jsonRequestCommand = readJson(thestringRequestcontent);	

}

if(jsonRequestCommand != null) {
	log.info("found command:" +jsonRequestCommand.toString() ); 	
	

	
	
} else {
	
	log.info("no json :");
	log.info(thestringRequestcontent);
	
}
			


 startProcessingRequest = Instant.now();
	 processRequest(jsonRequestCommand,recordingSession);
	 stopProcessingRequest = Instant.now();
	 
	


		// copy the request xav extract loacl variable for debug 
	
		StringEntity newentityRequest = new StringEntity(thestringRequestcontent,ContentType.create("application/json", Consts.UTF_8));
	
		
		
		((BasicHttpEntityEnclosingRequest) request).setEntity(newentityRequest);
    	   
    	   
       } else { // no content in the body of the request no need to copy the entity 
    	   
    	log.info(" management of non BasicHttpEntityEnclosingRequest  "); 
    	startProcessingRequest = Instant.now();
    	 processRequest(jsonRequestCommand,recordingSession);
    	 stopProcessingRequest = Instant.now();
    	 
       } 
       
       
       
        
        // Remove hop-by-hop headers
        request.removeHeaders(HTTP.TARGET_HOST);
        request.removeHeaders(HTTP.CONTENT_LEN);
        request.removeHeaders(HTTP.TRANSFER_ENCODING);
        request.removeHeaders(HTTP.CONN_DIRECTIVE);
        request.removeHeaders("Keep-Alive");
        request.removeHeaders("Proxy-Authenticate");
        request.removeHeaders("TE");
        request.removeHeaders("Trailers");
        request.removeHeaders("Upgrade");
        
        
        Instant startPreProcess = Instant.now();
        this.httpexecutor.preProcess(request, this.outhttpproc, context);
        Instant stopPreProcess = Instant.now();
        Instant startExecution = Instant.now();
      
       final HttpResponse targetResponse = this.httpexecutor.execute(request, conn, context);
        Instant stopExecution = Instant.now();
        Instant startPostProcess = Instant.now();
        this.httpexecutor.postProcess(response, this.outhttpproc, context);
        Instant stopPostProcess = Instant.now();
       
        
        
        
       
        
        Header[] allheaderresp = targetResponse.getAllHeaders();
        
        StringBuffer headerreslist = new StringBuffer();
        for (Header header : allheaderresp) {
        	headerreslist.append(header.getName()).append(":").append(header.getValue()).append("\n");
        	
  		
  	}
        log.info("header response:" +  headerreslist.toString());
      
      
        
     // Remove hop-by-hop headers
        targetResponse.removeHeaders(HTTP.CONTENT_LEN);
        targetResponse.removeHeaders(HTTP.TRANSFER_ENCODING);
        targetResponse.removeHeaders(HTTP.CONN_DIRECTIVE);
        targetResponse.removeHeaders("Keep-Alive");
        targetResponse.removeHeaders("TE");
        targetResponse.removeHeaders("Trailers");
        targetResponse.removeHeaders("Upgrade");
        response.setHeaders(targetResponse.getAllHeaders());
        response.setStatusLine(targetResponse.getStatusLine());
        log.info("proxyfied response status line is :" + targetResponse.getStatusLine());
        
        ///// dump the response 
        
        
        
        if(targetResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
        	
        	log.severe("XXXX http 500 XXXXXXXXXXXXXXXX");
        	recordingSession.onError(new IllegalStateException(targetResponse.getStatusLine().toString()));
        }
        
      String stringResponseContent = EntityUtils.toString(targetResponse.getEntity());
        
        
        
        response.setEntity(new StringEntity(stringResponseContent,Consts.UTF_8));

        log.info("<< Response: " + response.getStatusLine());
        
        log.info("body:" + stringResponseContent);
        
        
        // process the resp

        
        if(isJsonCommand(targetResponse.getEntity())) {     	
        jsonResponseCommand = readJson(stringResponseContent);
        	
        	
        }  	
   

        if(jsonResponseCommand != null) {
        	log.info("found response  command:" +jsonResponseCommand.toString() ); 	
        	
        } else {
        	
        	log.info("no json :");
        	log.info(stringResponseContent);
        	
        }
        
        //RemoteRecordingSession recordingSession = (RemoteRecordingSession) context.getAttribute(DriverRequestHandler.RECORDINGSESSION);
       Instant startProcessResponse = Instant.now();
   	 processResponse(jsonResponseCommand,recordingSession); 
   	 Instant stopProcessResponse = Instant.now();
        
        
Instant stopHandle = Instant.now();

// statitiques

log.info("total request handle processing time is " + Duration.between(startHandle, stopHandle)); 
log.info("------------request pre processing time is " + Duration.between(startPreProcess, stopPreProcess));
log.info("------------request post processing time is " + Duration.between(startPostProcess, stopPostProcess));
log.info("------------request execution  time is " + Duration.between(startExecution, stopExecution));
log.info("------------request  processing  time is " + Duration.between(startProcessingRequest, stopProcessingRequest));
log.info("------------response processing  time is " + Duration.between(startProcessResponse, stopProcessResponse));
      		
	}
	
	private HttpRequest rewriteUrlRequest(HttpRequest request) {
		String oldsession =param.get("sessionId")!=null ? param.get("sessionId"):"12345";
		HttpRequest ret = null;
		String newUri = request.getRequestLine().getUri().replaceAll(oldsession, seleniumSessionId.get().toString());
		if ( request instanceof BasicHttpEntityEnclosingRequest   ) {
		ret=	new BasicHttpEntityEnclosingRequest(request.getRequestLine().getMethod(),newUri,request.getRequestLine().getProtocolVersion());
		HttpEntity entity = ((BasicHttpEntityEnclosingRequest) request).getEntity() ;
		((BasicHttpEntityEnclosingRequest) ret).setEntity(entity);
		}else {
			
		ret=new BasicHttpRequest(request.getRequestLine().getMethod(),newUri,request.getRequestLine().getProtocolVersion())	;
			
		}
		ret.setHeaders(request.getAllHeaders());		
		log.info("rewriting session id " + oldsession + " for url " + request.getRequestLine().getUri()  + " with already running selenium session " + seleniumSessionId.get().toString());
		return ret;
	}

	protected String argsTojson(HashMap<String, String> argmap) {
		
	JsonObjectBuilder ret  = Json.createObjectBuilder();	
	
	Set<String> keys = argmap.keySet();
	
	for (String key : keys) {
		
		JsonObjectBuilder newVal  = Json.createObjectBuilder();		
		String thename = key;
		String theval = argmap.get(key) ;

		if(theval != null && ! theval.isEmpty() ) {
			newVal.add("value", theval) ;	

			ret.add(thename, newVal);
			
		}
		}
	
	final StringWriter writer = new StringWriter();
    final JsonWriter jwriter = Json.createWriter(writer);
    jwriter.writeObject(ret.build());
	
	String argument = writer.toString();
	String escapedargument = StringEscapeUtils.escapeHtml(argument);
	
		return escapedargument;
	}
	
	
	
	
	
	
	
		
	}
	


