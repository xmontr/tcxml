package tcxml.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.openqa.selenium.remote.http.JsonHttpCommandCodec;



public class Vts {
	
	private String server ;
	
	public Vts(String server, String name, String port) {
		super();
		this.server = server;
		this.name = name;
		this.port = port;
	}


	private String name ;
	
	
	private String  port;
	
	
	public static enum CMD { HANDSHAKE, ADD , RETRIEVE }
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 * @throws TcXmlException 
	 */

	public void  handShake() throws TcXmlException {
		URL target = getTargetUrl();
		JsonObjectBuilder data = Json.createObjectBuilder();
		
		data.add("version", "1.0");
		String postdata = getCommandData(CMD.HANDSHAKE, data.build());
		JsonObject ret = postJsonData2VTS(target , postdata) ;
		JsonObject thestatus = ret.getJsonObject("status");
		JsonString error = thestatus.getJsonString("error");
		
		if(!error.getString().equals("OK")) {
			
			throw new TcXmlException(" VTS return error status", new IllegalStateException());
		}
		
		

	}

	private JsonObject postJsonData2VTS(URL target, String postdata) throws TcXmlException {
		
		HttpURLConnection con = null;
		JsonObject ret = null ;
		try {
		
	byte[] data = postdata.getBytes(StandardCharsets.UTF_8);	
	  con = (HttpURLConnection) target.openConnection();
     con.setDoOutput(true);
		con.setRequestMethod("POST");	
     con.setRequestProperty("User-Agent", "Java client");
     con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

   DataOutputStream wr = new DataOutputStream(con.getOutputStream());
         wr.write(data);
         
        //lecture response 
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(con.getInputStream()));
         
         String line;
         StringBuilder content = new StringBuilder();

         while ((line = in.readLine()) != null) {
             content.append(line);
             content.append(System.lineSeparator());
         }
         
     	Reader reader = new StringReader(content.toString());
		
    	JsonReader jr = Json.createReader(reader );
    	 ret = jr.readObject();
    	jr.close();  
     
	
		}catch (Exception e) {
			throw new TcXmlException("error in vts ", e) ;
		} 	
		finally {
			
			
			
			if(con != null) {
				
				con.disconnect();
			}
		}
	
	
	
	
	
	
	
		return ret;
	}

	private URL getTargetUrl() throws TcXmlException {
		StringBuffer sb = new StringBuffer("http://").append(this.server).append(":").append(port).append("/api");
		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			throw new TcXmlException("VTS target url failure", e);
		}
	}
	
	
	
	private String getCommandData( CMD com , JsonObject data) throws TcXmlException {
		StringBuffer ret = new StringBuffer("request=");
		JsonObjectBuilder json = Json.createObjectBuilder();
		json.add("version", "1.0");
		json.add("data", data);
		
		switch (com) {
		case HANDSHAKE:  json.add("cmd", "handshake");	break;
		case RETRIEVE:  json.add("cmd", "retrieve");	break;
		case ADD:  json.add("cmd", "add");	break;

		default: throw new TcXmlException("unsupported VTS command", new IllegalAccessException(com.toString()));
			
		}
		
		return ret.append(json.build()).toString();
		
	}
	
	

	public String getServer() {
		return server;
	}

	public String getName() {
		return name;
	}

	public String getPort() {
		return port;
	}

	public JsonObject addCells(String colnames, String values, int theoption) throws TcXmlException {
		
	 String[] arrayKeys = colnames.split(";") ;
	 String[] arrayVal = values.split(";") ;
	 
	 if( arrayKeys.length != arrayVal.length ) {
		 
		 throw new TcXmlException("illegal arguments for vtcAddCells ", new IllegalArgumentException());
	 }
	 
	 JsonObjectBuilder json = Json.createObjectBuilder();
	 
	 for( int i=0; i <arrayKeys.length;i++) {
		 json.add(arrayKeys[i], arrayVal[i]);		 
	 }	 
	
	 JsonObjectBuilder jsoncmmand = Json.createObjectBuilder(); 
	 jsoncmmand.add("new", json.build());
	 jsoncmmand.add("option", theoption);
	 
	  String comm = getCommandData(CMD.ADD, jsoncmmand.build());
	 
	JsonObject ret = postJsonData2VTS(getTargetUrl(), comm) ;	
	
	// error control 
	

	JsonObject thestatus = ret.getJsonObject("status");
	JsonValue error = thestatus.get("error");
	
	if(!error.equals(JsonValue.NULL) ) {
		
		JsonValue code = thestatus.get("code");
		
		throw new TcXmlException(" VTS addcells return error status" + code.toString(), new IllegalStateException());
	}
	
	
	
		return ret;
	}

	public JsonObject popcells() throws TcXmlException {


		 JsonObjectBuilder json = Json.createObjectBuilder();
		 json.add("columns", JsonValue.NULL);
		 
		 
		  String comm = getCommandData(CMD.RETRIEVE, json.build());
		  
			 
			JsonObject ret = postJsonData2VTS(getTargetUrl(), comm) ;	
			
			// error control 
			

			JsonObject thestatus = ret.getJsonObject("status");
			JsonValue error = thestatus.get("error");
			
			if(!error.equals(JsonValue.NULL) ) {
				
				JsonValue code = thestatus.get("code");
				
				throw new TcXmlException(" VTS popcells return error status" + code.toString(), new IllegalStateException());
			}
		 
		 
		 
		return ret;
	}

}
