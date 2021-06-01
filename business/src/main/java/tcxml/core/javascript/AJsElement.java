package tcxml.core.javascript;

import java.util.Stack;

import jdk.nashorn.api.scripting.AbstractJSObject;
import tcxml.core.TcXmlController;
import tcxml.core.WebElementWrapper2;

public abstract  class AJsElement extends AbstractJSObject{
	
	
	
	protected String name;
	protected AJsElement parent;
	protected TcXmlController controller;
	
	public AJsElement(String name, AJsElement parent, TcXmlController cont) {
		super();
		this.name = name;
		this.parent = parent;
		this.controller=cont;
	}
	
	
	
	
	public String buildDomWindowPath() {
		AJsElement current = this;
		Stack<String> st = new Stack<String>();
		st.push(current.getSegment());
while(current.parent != null){
	current = current.parent;
	st.push(current.name);
	
}
	StringBuffer ret = new StringBuffer();
	String first = st.pop(); // skip window objet
	if( ! st.isEmpty()) { // add the first
		ret.append(st.pop());	
		
	}
		
	while(! st.isEmpty()) { // add the other
				ret.append(".").append(st.pop());
		
	}
	return ret.toString();	
	}




	protected abstract String getSegment();
	
	

}
