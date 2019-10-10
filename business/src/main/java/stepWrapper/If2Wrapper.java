package stepWrapper;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;

public class If2Wrapper extends AbstractStepWrapper {

	private TestObject theTestObject;


	public If2Wrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		
		//add object to test	
		BoundList<Step> li = step.getStep();
		 Step tostep = li.get(0);
		String referencedob = tostep.getTestObject() ;
	theTestObject = controller.getTestObjectById(referencedob, library);
		
		
		
	}

	@Override
	public String getTitle() {
		String ret = formatTitle(step.getIndex(), " " +  buildIfString() );
		return ret;
	}
	
	
	private String buildIfString() {
		// TODO Auto-generated method stub
		return "if("  + theTestObject.getAutoName()  + " exists =" +argumentMap.get("Exists").getValue() ;
	}
	

}
