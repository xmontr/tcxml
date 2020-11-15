package tcxml.remote.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
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

import tcxml.remote.DriverRequestHandler;

public abstract class AbstractHandler implements HttpRequestHandler{
	
	
	protected Map<String, String> param;
	private  final HttpProcessor outhttpproc ;
	private  final HttpRequestExecutor httpexecutor ;
	
	
	
	protected AbstractHandler(Map<String, String> p) {
		
		this.param =p;
        // Set up HTTP protocol processor for outgoing connections
         outhttpproc = new ImmutableHttpProcessor(
                new RequestContent(),
                new RequestTargetHost(),
                new RequestConnControl(),
                new RequestUserAgent("Test/1.1"),
                new RequestExpectContinue(true));
         
         // Set up outgoing request executor
          httpexecutor = new HttpRequestExecutor();
	}
	
	
	
	protected void dumpParams() {
		
	
	Set<String> listkey = param.keySet();
	for (String key : listkey) {
		System.out.println("handler parameter:" + key + " value:" + param.get(key) );
		
	}
			
		}
	
	
	
	
	
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		
        final DefaultBHttpClientConnection conn = (DefaultBHttpClientConnection) context.getAttribute(
                DriverRequestHandler.HTTP_OUT_CONN);
      HttpHost target =   (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        



        context.setAttribute(HttpCoreContext.HTTP_CONNECTION, conn);
        context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST,target);
        
        System.out.println(">> Request URI: " + request.getRequestLine().getUri());
        
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
        
        this.httpexecutor.preProcess(request, this.outhttpproc, context);
        final HttpResponse targetResponse = this.httpexecutor.execute(request, conn, context);
        this.httpexecutor.postProcess(response, this.outhttpproc, context);
        
        
     // Remove hop-by-hop headers
        targetResponse.removeHeaders(HTTP.CONTENT_LEN);
        targetResponse.removeHeaders(HTTP.TRANSFER_ENCODING);
        targetResponse.removeHeaders(HTTP.CONN_DIRECTIVE);
        targetResponse.removeHeaders("Keep-Alive");
        targetResponse.removeHeaders("TE");
        targetResponse.removeHeaders("Trailers");
        targetResponse.removeHeaders("Upgrade");

        response.setStatusLine(targetResponse.getStatusLine());
        response.setHeaders(targetResponse.getAllHeaders());
        response.setEntity(targetResponse.getEntity());

        System.out.println("<< Response: " + response.getStatusLine());
        
        
		
		
		
		


		
		
		
		
		
	}
		
	}
	


