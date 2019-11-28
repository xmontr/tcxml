package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.composite.stepViewer.TitleListener;
import tcxmlplugin.composite.stepViewer.TopStepContainer;
import tcxmlplugin.job.MultipleStepViewerRunner;
import tcxmlplugin.job.PlayingJob;

import org.eclipse.swt.layout.FillLayout;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import stepWrapper.RunBlockWrapper;
import stepWrapper.RunLogicWrapper;

public class RunLogicViewer extends AStepContainer implements TopStepContainer{
	
	
	


	
	
	








	private StepViewer initviewer;
	private StepViewer actionviewer;
	private StepViewer endviewer;







	public RunLogicViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style,controller);

	
		
		
	}



	@Override
	public TruLibrary getLibrary() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public void populate(RunLogicWrapper runlogic) throws TcXmlException {
		
		
	
		
		initviewer = addViewer( runlogic.getInitRunblock() );
		 actionviewer = addViewer( runlogic.getActionRunblock() );
		 endviewer = addViewer( runlogic.getEndRunblock() );
		
		
		
		
		
		/*
		 * BoundList<Step> li = runlogic.getStep().getStep(); for (Step step : li) {
		 * addStep(step); }
		 */
		
	bar.layout();	
		
		
	}
	
	
	
	
	private StepViewer addViewer(RunBlockWrapper initRunblock) throws TcXmlException {
		 StepViewer tv = StepViewerFactory.getViewer(initRunblock.getModel(),this, controller,getLibrary());		 
		 stepViwerChildren.add(tv);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);		
		

		xpndtmNewExpanditem.setExpanded(false);
		
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y  );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		tv.addPropertyChangeListener("title", new TitleListener(xpndtmNewExpanditem , tv));
		return tv;
	}



	public void sanityCheck (Step runlogic) throws TcXmlException {
		
		if ( !runlogic.getType().equals("runLogic")) {
			
			throw new TcXmlException("invalide runlogic step", new IllegalArgumentException());
		}
		
	}



	public void  play() throws TcXmlException {
		
		
		// play init 
		PlayingContext context = new PlayingContext(controller );		
		PlayingJob initJob = initviewer.getplayInteractiveJob(  context);		
		PlayingJob actionjob = actionviewer.getplayInteractiveJob(context);
		PlayingJob endjob = endviewer.getplayInteractiveJob(context);
		try {
			
		initJob.schedule();	
		waitForSuccess(initJob);
		actionjob.schedule();
		waitForSuccess(actionjob);
		
		
		
		
		
		
		
		}catch (IllegalStateException  e) {
			throw new TcXmlException("failure in playing script", e) ;
		}
		finally {
			endjob.schedule();
		}
		
	
	}


private void waitForSuccess(PlayingJob j)  {
	try {
		j.join();
		PlayingContext temp = j.getCtx();

		IStatus lastExecStatus = j.getResult();

		if (lastExecStatus != Status.OK_STATUS) {

			throw new IllegalStateException("error in child step");
		}

	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new IllegalStateException("interrupted", e);
	}
	
	
}

	
	
	@Override
	public void refreshSize() {
		resizeContent();
		
	
	}







	@Override
	public void setCurrentStepExpanded(StepViewer st) {
		// TODO Auto-generated method stub
		
	}	
	
	
	
	
	
	
	
	
	
	
	

}
