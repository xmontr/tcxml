package tcxml.model;

public class VtcConnectArgModel extends AbstractModel{
	
	
	private ArgModel server;
	
	private ArgModel port;
	
	
	private ArgModel vtsName ;
	
	private ArgModel variable;
	
	
	public VtcConnectArgModel() {
		
		server = new ArgModel("serverName");
		port =  new ArgModel("port");
		vtsName =  new ArgModel("vtsName");
		variable =  new ArgModel("Variable");
		
		
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
