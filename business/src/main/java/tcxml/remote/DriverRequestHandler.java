package tcxml.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import tcxml.remote.handler.AbstractHandler;
import tcxml.remote.handler.DefaultHandler;
import tcxml.remote.handler.NoHandler;


public class DriverRequestHandler implements HttpRequestHandler {
	
	final Map<String, ImmutableList<Function<String, AbstractHandler>>> additionalHandlers;
	
	
	
	public DriverRequestHandler() {
		
		
		   this.additionalHandlers = ImmutableMap.of(
			        "DELETE", ImmutableList.of(),
			        "GET", ImmutableList.of(
			            handler("/session/{sessionId}/log/types",
			                    params -> new  DefaultHandler(params)),
			            handler("/sessions", params ->new  DefaultHandler(params)),
			            handler("/status", params -> new  DefaultHandler(params))
			        ),
			        "POST", ImmutableList.of(
			            handler("/session", params -> new  DefaultHandler(params)),
			            handler("/session/{sessionId}/file",
			                    params -> new   DefaultHandler(params)),
			            handler("/session/{sessionId}/log",
			                    params -> new  DefaultHandler(params)),
			            handler("/session/{sessionId}/se/file",
			                    params -> new  DefaultHandler(params))
			        ));
		
		
		
	}
	
	
	
	

	private  Function<String, AbstractHandler> handler(String urlmodel, Function< Map<String,String> ,AbstractHandler>  gen) {
	    UrlTemplate urlTemplate = new UrlTemplate(urlmodel);
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
		AbstractHandler thehandler = match(request);
		thehandler.handle(request, response, context);
		


	}

}
