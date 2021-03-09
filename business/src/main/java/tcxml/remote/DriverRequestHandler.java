package tcxml.remote;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.openqa.selenium.remote.SessionId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import tcxml.remote.handler.AbstractHandler;
import tcxml.remote.handler.DefaultHandler;
import tcxml.remote.handler.ElementClickHandler;
import tcxml.remote.handler.ExecuteScriptHandler;
import tcxml.remote.handler.FindElementsHandler;
import tcxml.remote.handler.NavigateToHandler;
import tcxml.remote.handler.NoHandler;
import tcxml.remote.handler.SessionHandler;
import tcxml.remote.handler.WaitOnHandler;


public class DriverRequestHandler implements HttpRequestHandler {
	
	
	public static final String HTTP_OUT_CONN = "http.proxy.out-conn";
	public static final String RECORDINGSESSION= "recordingsession";
	public static final String SELENIUMSESSIONID = "seleniumsessionid";
	
	final Map<String, ImmutableList<Function<String, AbstractHandler>>> additionalHandlers;
	private  HttpContext context;
	
	// private final int bufsize = 16 * 1024;
	
	 //final DefaultBHttpClientConnection outconn  = new DefaultBHttpClientConnection(bufsize);
	
	
	 HttpClientConnection outconn;
	
	private final ConnectionReuseStrategy connStrategy;
	
	private HttpHost targetHost;
	
	private String httpListeningContext;

	private Logger log;

	private RemoteRecordingSession recordingSession;
	private Optional<SessionId> seleniumSessionId;
	private BasicHttpClientConnectionManager connManager;
	
	
	
	public DriverRequestHandler(URL forwardUrl, String httpListenningContext, Optional<SessionId> seleniumSessionId) {
     	log = Logger.getLogger(getClass().getName());
      	log.setLevel(Level.ALL);
      	this.seleniumSessionId = seleniumSessionId;
		
		this.connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		
		
		
		this.httpListeningContext = httpListenningContext;
		   this.additionalHandlers = ImmutableMap.of(
			        "DELETE", ImmutableList.of(),
			        "GET", ImmutableList.of(
			            handler("/session/{sessionId}/log/types",
			                    params -> new  DefaultHandler(params,seleniumSessionId)),
			            handler("/sessions", params ->new  DefaultHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}", params ->new  SessionHandler(params,seleniumSessionId)), // non w3c
			            handler("/status", params -> new  DefaultHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/element/{elementId}/enabled", params -> new  WaitOnHandler(params,seleniumSessionId))
			        ),
			        "POST", ImmutableList.of(
			            handler("/session", params -> new  SessionHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/file",
			                    params -> new   DefaultHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/log",
			                    params -> new  DefaultHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/se/file",
			                    params -> new  DefaultHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/execute",
			                    params -> new  ExecuteScriptHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/url",
			                    params -> new  NavigateToHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/elements",
			                    params -> new  FindElementsHandler(params,seleniumSessionId)),
			            handler("/session/{sessionId}/element/{elementId}/click",
			                    params -> new  ElementClickHandler(params,seleniumSessionId))
			        ));
		
		   this.targetHost = new HttpHost(forwardUrl.getHost(), forwardUrl.getPort(), forwardUrl.getProtocol());
		
		   connManager = new BasicHttpClientConnectionManager();
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

return new NoHandler(p,seleniumSessionId);	
}


	@Override
	public synchronized void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		
        if (this.outconn == null || (!this.outconn.isOpen() ||this.outconn.isStale() ) ) {
        	
        	///new connection version
        	
        	connManager.setConnectionConfig(ConnectionConfig.custom().setBufferSize(16 * 1024).build());
        	HttpRoute route = new HttpRoute(this.targetHost);
        	connManager.setSocketConfig(SocketConfig.custom().
        		    setSoTimeout(30000).build());
        	
        	ConnectionRequest connRequest = connManager.requestConnection(route, null);
        	
        	try {
				this.outconn=connRequest.get(3000, TimeUnit.MILLISECONDS);
				connManager.connect(this.outconn, route, 1000, context);
				connManager.routeComplete(outconn, route, context);
				
				
				log.info("outgoing connection to  " + this.targetHost.getHostName() + ":" + this.targetHost.getPort());
			} catch (ConnectionPoolTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	
        //old version        	
     //       final Socket outsocket = new Socket(this.targetHost.getHostName(),this.targetHost.getPort() );
          
    //        this.outconn.bind(outsocket);
    //        this.outconn.setSocketTimeout(30000);
         //   log.info("Outgoing connection to " + outsocket.getInetAddress() + ":" +outsocket.getPort()  + " timeout is "  + this.outconn.getSocketTimeout() + " ms ");
        }
        
        final boolean keepalive = this.connStrategy.keepAlive(response, context);
     //   context.setAttribute(HTTP_CONN_KEEPALIVE, new Boolean(keepalive));
		
		
		//put target host and out going connection in the http context 
		  context.setAttribute(HTTP_OUT_CONN, this.outconn);		 
		  context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST, this.targetHost);		
		  context.setAttribute(RECORDINGSESSION, this.recordingSession);
		  context.setAttribute(SELENIUMSESSIONID, seleniumSessionId);
		
		AbstractHandler thehandler = match(request);		
		thehandler.handle(request, response, context);
		

		


	}





	public void setRecordingSession(RemoteRecordingSession recordingSession) {
		this.recordingSession = recordingSession;
		
	}

}
