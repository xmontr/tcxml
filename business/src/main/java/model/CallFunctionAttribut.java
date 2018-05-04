package model;

public class CallFunctionAttribut {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isJs() {
		return isJs;
	}
	public void setJs(boolean isJs) {
		this.isJs = isJs;
	}
	private String name;
	private String value;
	private boolean isJs;
	public CallFunctionAttribut(String name, String value, boolean isJs) {
		super();
		this.name = name;
		this.value = value;
		this.isJs = isJs;
	}
	

}
