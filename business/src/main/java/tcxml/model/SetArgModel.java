package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class SetArgModel extends AbstractModel{
	
	
	private ArgModel path ;
	
	
	
	public SetArgModel() {
		
		path = new ArgModel("Path");
		path.setValue("");
		
	}



	public ArgModel getPath() {
		return path;
	}



	public void setPath(ArgModel path) {
		this.path = path;
	}





}
