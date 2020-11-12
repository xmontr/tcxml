package tcxml.remote.handler;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

public class DefaultHandler extends AbstractHandler{

	public DefaultHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        response.setStatusCode(HttpStatus.SC_OK);
        response.setEntity(
                new StringEntity("handle request:" + request.getRequestLine(),
                        ContentType.TEXT_PLAIN));
        
        dumpParams();
	System.out.println( "handle request:" + request.getRequestLine());
		
	}

}
