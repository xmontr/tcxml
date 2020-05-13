package tcxml.mapping;

import javax.xml.bind.annotation.adapters.XmlAdapter;



public class CdataAdapter extends XmlAdapter<String, String> {
	
	
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }

	
	

}
