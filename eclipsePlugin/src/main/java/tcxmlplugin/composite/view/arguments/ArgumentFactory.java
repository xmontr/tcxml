package tcxmlplugin.composite.view.arguments;

import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.BrowserActionView;

public class ArgumentFactory {
	
	
	
	public static StepArgument getArgument(String newAction, StepView view) {
		StepArgument ret = null;	
		
		switch(newAction) {
		
		case "Navigate":ret = getNavigateArgument(view); break;
		
		default: ret=getDefaultArgument(view); break;
		
		}
		
		
	
	
	
	
	
	
	return ret;
		
		
		
	}

	private static StepArgument getDefaultArgument(StepView view) {
		DefaultArgument ret = new DefaultArgument(view, view.getStyle());
		return ret;
	}

	private static StepArgument getNavigateArgument(StepView view) {
		StepArgument ret = new NavigateArgs(view, view.getStyle());
		ret.populate(view.getModel());
		
		return ret;
	}

}
