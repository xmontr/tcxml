package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class VtcConnectArgModel extends AbstractModel{
	
	
	private ArgModel server;
	
	private ArgModel port;
	
	
	private ArgModel vtsName ;
	
	private ArgModel variable;
	
	
	public VtcConnectArgModel() {
		
		server = new ArgModel("serverName");
		server.setValue("");
		port =  new ArgModel("port");
		port.setValue("");
		vtsName =  new ArgModel("vtsName");
		vtsName.setValue("");
		variable =  new ArgModel("Variable");
		variable.setValue("");
		
		
	}


	public void setServer(ArgModel server) {
		this.server = server;
	}


	public void setPort(ArgModel port) {
		this.port = port;
	}


	public void setVtsName(ArgModel vtsName) {
		this.vtsName = vtsName;
	}


	public void setVariable(ArgModel variable) {
		this.variable = variable;
	}


	public ArgModel getServer() {
		return server;
	}


	public ArgModel getPort() {
		return port;
	}


	public ArgModel getVtsName() {
		return vtsName;
	}


	public ArgModel getVariable() {
		return variable;
	}



	

	
	

}
