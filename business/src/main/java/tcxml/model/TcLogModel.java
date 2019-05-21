package tcxml.model;



public class TcLogModel extends AbstractModel{
	
	
	
	private ArgModel text ;
	
	
	public TcLogModel() {
		
		text = new ArgModel("text");
	}


	public ArgModel getText() {
		return text;
	}


	public void setText(ArgModel text) {
		this.text = text;
	}
	

}
