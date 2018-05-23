package tcxmlplugin.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class PlayingJob extends Job{
	
	
	public PlayingContext getCtx() {
		return ctx;
	}

	private StepViewer stepviewer;
	private PlayingContext ctx;
	

	public PlayingJob(StepViewer viewer,PlayingContext ctx) {
		super("playing " + viewer.getViewer().getTitle());	
	this.stepviewer = viewer;
	this.ctx = ctx ;
	
	addJobChangeListener(stepviewer.getInteractivePlayingJobListener());
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IStatus ret = Status.OK_STATUS;
try {	
	
ctx = stepviewer.play(ctx);


ret = Status.OK_STATUS;
	} catch (TcXmlException e1) {
		ret=Status.CANCEL_STATUS;
		TcXmlPluginController.getInstance().error("fail to play step", e1);
		
	
	}

return ret;		
	
}

}