package tcxml.core;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;

public class TargetTestObject {
	
	
	
	private WebElement domObject;
	private int position;
	private HashMap<String, String> predicats;
	private String tagname;
	private String autoName;
	private String fallBackName;
	private String xpath;
	private WebDriver driver;
	private ArrayList<String> roles;

	public TargetTestObject  ( WebElement  domObject,By by,WebDriver driver) { 

		this.domObject = domObject ;
		this.driver = driver;
		this.predicats = new HashMap<String, String>();
this.roles= new ArrayList<String>();
		this.position =1 ;
		this.tagname = this.domObject.getTagName().toLowerCase();
		this.autoName="";
		this.fallBackName="";
		
		this.computePredicats();
		this.computePosition();
		this.computeXpath();
		this.computeName();


		}
	
	
	private void computePredicats  ( ) { // determine the relvant predicats for the testobject
		
		this.addPredicat("name") ;	
		this.addPredicat("type") ;
	this.addPredicat("value") ;
     this.addTextPredicat();
		this.addPredicat("alt") ;
		this.addPredicat("title") ;

		

	}
	
	
	private void addPredicat    ( String  predname  ) {// add the predicat if value exist
		String predval = this.domObject.getAttribute(predname);
		if( predval != null && !predval.isEmpty() ) {
					
				String thePredname="@" + predname;
				
				this.predicats.put(thePredname, predval);
			
		}

			
			}
	
	private void addTextPredicat  (  ) {// add the text()  predicat if text() value exist
		String  predval = this.getTextForObject();
		if( predval != null && !predval.isEmpty() && predval.trim().length() != 0 ) {
					
				String thePredname= "text()";
				
				this.predicats.put(thePredname, predval);
			}
			
			}
	
	
	
	private String getTextForObject  (  ) {  // get the text() for the dome object
		String ret = this.domObject.getText();
			
return ret;


}
	
	
	private void computePosition  ( ) { // determine the position according a basic xpath based on the tagname and the predicats
		String  xpath = this.getXpathWithoutPosition();
		

		final ByXPath xp2 = new ByXPath(xpath);	
		List<WebElement> elements = driver.findElements(xp2);
Iterator<WebElement> it = elements.iterator();
while (it.hasNext()) {
	WebElement webElement = (WebElement) it.next();
	if( webElement == this.domObject) {
		break;
		
		
	}
	this.position = this.position +1 ;
	
	
}

	

}
	
	private String generatePredicat () { // generate the string fpr the xpath predicat
		String ret="";
			int 	index=0;
				if (this.predicats.size() == 0  ) return ret;
				
			String[] keys = this.predicats.keySet().toArray(new String[this.predicats.size()]) ;
			
			
			
				ret = " [ " + keys[0] + " = \"" +    this.predicats.get(keys[0]) +"\"" ;		
				for( index=1; index < keys.length ; index++ ) {
					ret = ret + " and " +  keys[index] + " = \"" +    this.predicats.get(keys[index]) +"\"" ;	
				}
				ret = ret + " ] " ;
			return ret;
			}


	
	private String getXpathWithoutPosition ( ) { // generate the xpath for finding the element without the position
		String  ret =  "//" + this.tagname +this.generatePredicat();	
		return ret;	
		}
	
	
	
	private void computeXpath ( ) { // compute the full xpath with position
		String  ret = this.getXpathWithoutPosition();
		ret += "[" + this.position + "]" ; 
		this.xpath = ret ;	
		}
	
	
	private void computeName  ( ) { // determine the autoname and fallback name for domObject
		
		switch (this.tagname) {
			
		case "select" :	this.computeSelectName();break;
		case "input":this.computeInputName();break;		
		case "a":this.computeLinkName();break;
		
		case "img":this.computeImageName();	break;
		case "h1":this.computeHeadingName();break;
		case "h2":this.computeHeadingName();break;
		case "h3":this.computeHeadingName();break;
		case "h4":this.computeHeadingName();break;
		case "h5":this.computeHeadingName();break;
		case "h6":this.computeHeadingName();break;
		case "span":this.computeSpanName();break;
		//default: console.log(" WARNING - in computeName, no specific management for tag :" + this.tagname );
 
			

		}
		//build the fallback name
		this.fallBackName=this.tagname + "(" +this.position  + ")";
		
	}
	
	
	
	private void computeSelectName  ( ) { // determine the autoname and fallback name for an select
		String name =this.getPredicat("@name");
if(name != null && name != "" ){
	this.autoName=name;
}
roles.add("element");
roles.add("listbox");
}
	
	
	
	
	private void computeImageName  ( ) { // determine the autoname and fallback name for an image
		String alt =this.getPredicat("@alt");
		if(alt != null &&  !alt.isEmpty() ){
			this.autoName=alt;
		}
		roles.add("element");
		}
	
	
	private void computeInputName  ( ) { // determine the autoname and fallback name for an input
		String  title =this.getPredicat("@title");
	if(title != null && !title.isEmpty() ){
		this.autoName=title;
	} else {
				String value =this.getPredicat("@value");
	if(value != null && !value.isEmpty() ){
		this.autoName=value;	
	}else {
		String name =this.getPredicat("@name");
		if(name !=null && !name.isEmpty()) {
			this.autoName = name;
		}
	}
		
		
	}
	roles.add("element");
	String type =this.getPredicat("type");
	if(type !=null && !type.equalsIgnoreCase("text")) {
		roles.add("textbox");
	}
	
	
		}
	

	
	private void computeSpanName  ( ) { // determine the autoname and fallback name for an heading h1 h2 h3 ... h6
		
		String  txt =this.getPredicat("text()");
if(txt != null && !txt.isEmpty() ){
	this.autoName=txt;
}
roles.add("element");
	}


	private String getPredicat(String string) {
		// TODO Auto-generated method stub
		return this.predicats.get(string);
	}
	

	private void computeHeadingName  ( ) { // determine the autoname and fallback name for an heading h1 h2 h3 ... h6
		
		String  txt =this.getPredicat("text()");
if(txt != null && !txt.isEmpty() ){
	this.autoName=this.splitLongText(txt , 40 ) + " heading" ;
}
roles.add("element");
roles.add("heading");
	}
	
	
	private void computeLinkName  ( ) { // determine the autoname and fallback name for a link
		String txt =this.getPredicat("@title");
if(txt != null && !txt.isEmpty() ){
this.autoName=txt;
}
roles.add("element");
roles.add("link");
}
	
	
	
	public ArrayList<String> getRoles() {
		return roles;
	}


	public String getAutoName() {
		return autoName;
	}


	public String getFallBackName() {
		return fallBackName;
	}


	private String splitLongText ( String txt,  int maxlength ) { // generate the xpath for finding the element without the position
		String ret = "";
		if(txt.length()  > maxlength) {
	ret += txt.substring(0, maxlength);
	ret +=" ... ";
	
	
}else {
	ret +=txt;
	
}
return ret;	
}
	
	
	

}
	
	


