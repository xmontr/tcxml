package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.composite.stepViewer.TopStepContainer;

import org.eclipse.swt.layout.FillLayout;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

public class RunLogicViewer extends AStepContainer implements TopStepContainer{
	
	
	private TcXmlController controller;


	
	
	
	public RunLogicViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style,controller);

	
		
		
	}



	
	
	
	
	public void populate(Step runlogic) throws TcXmlException {
		
		sanityCheck(runlogic);
		BoundList<Step> li = runlogic.getStep();
		for (Step step : li) {
			addStep(step);
		}
		
	bar.layout();	
		
		
	}
	
	
	public void sanityCheck (Step runlogic) throws TcXmlException {
		
		if ( !runlogic.getType().equals("runLogic")) {
			
			throw new TcXmlException("invalide runlogic step", new IllegalArgumentException());
		}
		
	}







	@Override
	public void showOnTop(StepViewer st) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void refreshSize() {
		resizeContent();
		
	
	}	
	
	
	
	
	
	
	
	
	
	
	

}
