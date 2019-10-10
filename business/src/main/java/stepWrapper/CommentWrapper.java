package stepWrapper;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public class CommentWrapper extends AbstractStepWrapper {

	public CommentWrapper(Step step, TcXmlController controller, TruLibrary library) throws TcXmlException {
		super(step, controller, library);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTitle() {
		StringBuffer sb =new StringBuffer();
		String txt = step.getComment();
		if(txt.length() > 10) {
			sb.append(txt.substring(0, 10));
			sb.append(" ... ");
			
			
		}else {
			sb.append(txt);
			
		}
		
		String title = sb.toString();
		String index = step.getIndex() ;
		
		return (formatTitle(index, title)) ;
	}
	
	
	
	

}
