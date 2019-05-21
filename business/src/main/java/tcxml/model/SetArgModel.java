package tcxml.model;

public class SetArgModel extends AbstractModel{
	
	
	private ArgModel path ;
	
	
	
	public SetArgModel() {
		
		path = new ArgModel("Path");
		
	}



	public ArgModel getPath() {
		return path;
	}



	public void setPath(ArgModel path) {
		this.path = path;
	}

}
