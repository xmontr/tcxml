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

public class NoHandler extends AbstractHandler {

	public NoHandler(Map<String, String> p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
        response.setStatusCode(HttpStatus.SC_NOT_FOUND);
        response.setEntity(
                new StringEntity("handle request:" + request.getRequestLine(),
                        ContentType.TEXT_PLAIN));
	System.out.println( "no handler for  request:" + request.getRequestLine());
		
	}

}
