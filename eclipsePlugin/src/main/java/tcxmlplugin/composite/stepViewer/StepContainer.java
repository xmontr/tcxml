package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.TcXmlException;
import tcxml.model.Step;

public interface StepContainer {
	
	
	public ExpandBar getBar();
	
	 void clean() ;
	 
	 void addStep(Step step) throws TcXmlException ;

}
