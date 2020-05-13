package tcxmlplugin.composite.stepViewer;

import java.util.List;

import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;

public interface StepContainer {
	
	
	public ExpandBar getBar();
	
	public  void clean() ;
	 
	 public void addStep(Step step) throws TcXmlException ;
	 
	 public void remove(Step step) throws TcXmlException ;
	 
	 
	 public void addStep(Step step, int index) throws TcXmlException ;
	 
	 public TruLibrary getLibrary() ;
	 
	 public TcXmlController getController() ;
	 
	 
	 public List<StepViewer>  getChildViewer();
	 
	 
	 public void reIndex();

}
