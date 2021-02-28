package tcxml.remote;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.openqa.selenium.remote.SessionId;

import tcxml.core.TcXmlException;

public class Express {
	
	private int port;
	private HttpServer server ;
	private DriverRequestHandler requestHandler;
	private List<RecordingSessionListener> recordingsessionlisteners;
	private Optional<SessionId> seleniumSessionId;
	
	
	
	
	public Express(int listeningport,String httpListenningContext, URL forwardUrl, Optional<SessionId> seleniumSessionId) {
		this.port = listeningport;
		this.seleniumSessionId = seleniumSessionId;
		
		recordingsessionlisteners = new ArrayList<RecordingSessionListener>();
		
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
		
		requestHandler = new DriverRequestHandler(forwardUrl,httpListenningContext,this.seleniumSessionId);
		this.server = ServerBootstrap.bootstrap()
		        .setListenerPort(listeningport)
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
	
	
	
	public RemoteRecordingSession minuteListen(long minutes) throws TcXmlException {
		try {
			RemoteRecordingSession recordingSession = new RemoteRecordingSession(this.seleniumSessionId);
			recordingsessionlisteners.forEach(li ->  recordingSession.addListenner(li));
			
			requestHandler.setRecordingSession(recordingSession);
		server.start();
		
		server.awaitTermination(minutes, TimeUnit.MINUTES);
		return recordingSession ;
		} catch (IOException | InterruptedException e) {
			throw new TcXmlException("uanble to start http server", e);
			}
		
		
		
		
	}
	
	
	public void shutDown() {
		
		server.shutdown(5, TimeUnit.SECONDS);
		
		
	}
	
	public void registerRecordingListenner( RecordingSessionListener li) {
		
		this.recordingsessionlisteners.add(li);
	
	}
	
	
	public void unregisterRecordingListener(RecordingSessionListener li) {
		
		recordingsessionlisteners.remove(li);
		
	}
	
	
	
	
	
	


	
}