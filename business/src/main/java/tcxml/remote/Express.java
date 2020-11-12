package tcxml.remote;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import tcxml.core.TcXmlException;

public class Express {
	
	private int port;
	private HttpServer server ;
	
	
	public Express(int port) {
		this.port = port;
		
		HttpProcessor httpProcessor= HttpProcessorBuilder.create()
		        .add(new ResponseDate())
		        .add(new ResponseServer("TCXMLServer-HTTP/1.1"))
		        .add(new ResponseContent())
		        .add(new ResponseConnControl())
		        .build();
		SocketConfig socketConfig= SocketConfig.custom()
		        .setSoTimeout(15000)
		        .setTcpNoDelay(true)
		        .build();
		
		HttpRequestHandler requestHandler = new DriverRequestHandler();
		this.server = ServerBootstrap.bootstrap()
		        .setListenerPort(port)
		        .setHttpProcessor(httpProcessor)
		        .setSocketConfig(socketConfig)
		        .setExceptionLogger(new StdErrorExceptionLogger())
		        .registerHandler("*", requestHandler )
		        .create();
		
		
		
		
		
		
		
		
	}
	
	public void listen() throws TcXmlException {
		try {
			server.start();
			server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			
		} catch (IOException | InterruptedException e) {
		throw new TcXmlException("uanble to start http server", e);
		}
		

		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		        server.shutdown(5, TimeUnit.SECONDS);
		    }
		});
		
		
	}
	
	
	
	public void minuteListen(long minutes) throws TcXmlException {
		try {
		server.start();
		server.awaitTermination(minutes, TimeUnit.MINUTES);
		} catch (IOException | InterruptedException e) {
			throw new TcXmlException("uanble to start http server", e);
			}
		
		
		
		
	}
	
	
	public void shutDown() {
		
		
		
		
	}
	
	
	
	
	
	
	/*
	  private <H extends HttpHandler> Function<String, HttpHandler> handler(
		      String template,
		      Function<Map<String, String>, H> handlerGenerator) {
		    UrlTemplate urlTemplate = new UrlTemplate(template);
		    return path -> {
		      UrlTemplate.Match match = urlTemplate.match(path);
		      if (match == null) {
		        return null;
		      }
		      return handlerGenerator.apply(match.getParameters());
		    };



*/

	
}