package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class TcLogModel extends AbstractModel{
	
	
	
	private ArgModel text ;
	
	
	public TcLogModel() {
		
		text = new ArgModel("text");
		text.setValue("");
	}


	public ArgModel getText() {
		return text;
	}


	public void setText(ArgModel text) {
		this.text = text;
	}



	

}
