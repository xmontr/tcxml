package tcxml.remote;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import tcxml.remote.handler.AbstractHandler;
import tcxml.remote.handler.DefaultHandler;
import tcxml.remote.handler.ExecuteScriptHandler;
import tcxml.remote.handler.NavigateToHandler;
import tcxml.remote.handler.NoHandler;


public class DriverRequestHandler implements HttpRequestHandler {
	
	
	public static final String HTTP_OUT_CONN = "http.proxy.out-conn";
	public static final String RECORDINGSESSION= "recordingsession";
	
	final Map<String, ImmutableList<Function<String, AbstractHandler>>> additionalHandlers;
	private  HttpContext context;
	
	private final int bufsize = 8 * 1024;
	
	final DefaultBHttpClientConnection outconn  = new DefaultBHttpClientConnection(bufsize);
	
	private final ConnectionReuseStrategy connStrategy;
	
	private HttpHost targetHost;
	
	private String httpListeningContext;

	private Logger log;

	private RemoteRecordingSession recordingSession;
	
	
	
	public DriverRequestHandler(URL forwardUrl, String httpListenningContext) {
     	log = Logger.getLogger(getClass().getName());
      	log.setLevel(Level.ALL);
		
		this.connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		
		this.httpListeningContext = httpListenningContext;
		   this.additionalHandlers = ImmutableMap.of(
			        "DELETE", ImmutableList.of(),
			        "GET", ImmutableList.of(
			            handler("/session/{sessionId}/log/types",
			                    params -> new  DefaultHandler(params)),
			            handler("/sessions", params ->new  DefaultHandler(params)),
			            handler("/status", params -> new  DefaultHandler(params))
			        ),
			        "POST", ImmutableList.of(
			            handler("/session", params -> new  SessionHandler(params)),
			            handler("/session/{sessionId}/file",
			                    params -> new   DefaultHandler(params)),
			            handler("/session/{sessionId}/log",
			                    params -> new  DefaultHandler(params)),
			            handler("/session/{sessionId}/se/file",
			                    params -> new  DefaultHandler(params)),
			            handler("/session/{sessionId}/execute",
			                    params -> new  ExecuteScriptHandler(params)),
			            handler("/session/{sessionId}/url",
			                    params -> new  NavigateToHandler(params))
			        ));
		
		   this.targetHost = new HttpHost(forwardUrl.getHost(), forwardUrl.getPort(), forwardUrl.getProtocol());
		

	}
	
	
	
	

	private  Function<String, AbstractHandler> handler(String urlmodel, Function< Map<String,String> ,AbstractHandler>  gen) {
	    UrlTemplate urlTemplate = new UrlTemplate(this.httpListeningContext + urlmodel);
	    return path -> {
	      UrlTemplate.Match match = urlTemplate.match(path);
	      if (match == null) {
	        return null;
	      }
	      return gen.apply(match.getParameters());
	    };
	}


public AbstractHandler match(HttpRequest request) {
String path = request.getRequestLine().getUri();

String method = request.getRequestLine().getMethod().toUpperCase();
Optional<AbstractHandler> additionalHandler = additionalHandlers.get(method )
.stream()
.map(bundle -> bundle.apply(path))
.filter(Objects::nonNull)
.findFirst();

if (additionalHandler.isPresent()) {
    return additionalHandler.get();
  }

Map<String, String> p = new HashMap<String, String>();
p.put("path", path);

return new NoHandler(p);	
}


	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		
        if (!this.outconn.isOpen() ||this.outconn.isStale()) {
            final Socket outsocket = new Socket(this.targetHost.getHostName(),this.targetHost.getPort() );
            this.outconn.bind(outsocket);
            log.info("Outgoing connection to " + outsocket.getInetAddress() + ":" +outsocket.getPort() );
        }
        
        final boolean keepalive = this.connStrategy.keepAlive(response, context);
     //   context.setAttribute(HTTP_CONN_KEEPALIVE, new Boolean(keepalive));
		
		
		//put target host and out going connection in the http context 
		  context.setAttribute(HTTP_OUT_CONN, this.outconn);		 
		  context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST, this.targetHost);		
		  context.setAttribute(RECORDINGSESSION, this.recordingSession);
		
		AbstractHandler thehandler = match(request);		
		thehandler.handle(request, response, context);
		

		


	}





	public void setRecordingSession(RemoteRecordingSession recordingSession) {
		this.recordingSession = recordingSession;
		
	}

}
