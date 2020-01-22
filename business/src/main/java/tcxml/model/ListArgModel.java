package tcxml.model;

import java.util.List;

public class ListArgModel extends ArgModel{

	public List<String> getValueList() {
		return valueList;
	}

	private List<String> valueList;

	public ListArgModel(String name , List<String> values) {
		super(name);
		this.valueList = values ;
	}
	
	


}
