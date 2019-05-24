package tcxmlplugin.composite.stepViewer;

import java.util.List;

import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public interface StepContainer {
	
	
	public ExpandBar getBar();
	
	 void clean() ;
	 
	 void addStep(Step step) throws TcXmlException ;
	 
	 
	 public List<StepViewer>  getChildViewer();

}
