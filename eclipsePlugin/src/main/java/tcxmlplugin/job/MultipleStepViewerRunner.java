package tcxmlplugin.job;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import stepWrapper.AbstractStepWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class MultipleStepViewerRunner {
	
	
	
	
	
	private List<StepViewer>  stepviewers ;
	


	public MultipleStepViewerRunner(List<StepViewer> steps) {
		super();
		this.stepviewers = steps;
	
	}
	

	
	

	
	
	public PlayingContext runSteps( PlayingContext ctx) throws TcXmlException {
		
		PlayingContext temp = ctx;
		long interva = Long.parseLong(TcXmlPluginController.getInstance().getProperties().getProperty("RS.interstepInterval"));

		for (Iterator iterator = stepviewers.iterator(); iterator.hasNext();) {
			StepViewer stepViewer = (StepViewer) iterator.next();
			
			stepViewer.getDisplay().syncExec(new VisibilityEnsurer(stepViewer));

			PlayingJob j = stepViewer.getplayInteractiveJob(temp);
			j.schedule();

			try {
				j.join();
				temp = j.getCtx();

				IStatus lastExecStatus = j.getResult();

				if (lastExecStatus != Status.OK_STATUS) {

					throw new TcXmlException("error in child step", new IllegalStateException());
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new TcXmlException("interrupted", e);
			}

			try {
				stepViewer.getController().getLog().fine("sleeping between step (ms) = " + interva);
				Thread.currentThread().sleep(interva);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new TcXmlException("interrupted", e);
			}

		}

		return temp;
		
		
		
		
		
		
	}
	
	

}
