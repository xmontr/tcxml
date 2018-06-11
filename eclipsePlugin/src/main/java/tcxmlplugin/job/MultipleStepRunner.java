package tcxmlplugin.job;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class MultipleStepRunner {
	
	
	
	
	
	private List<StepViewer>  steps ;

	public MultipleStepRunner(List<StepViewer> steps) {
		super();
		this.steps = steps;
	}
	
	

	
	
	public PlayingContext runSteps( PlayingContext ctx) throws TcXmlException {
		
		PlayingContext temp = ctx;

		for (Iterator iterator = steps.iterator(); iterator.hasNext();) {
			StepViewer stepViewer = (StepViewer) iterator.next();

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
			}

			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return temp;
		
		
		
		
		
		
	}
	
	

}
