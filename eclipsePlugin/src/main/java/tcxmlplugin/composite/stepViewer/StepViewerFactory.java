package tcxmlplugin.composite.stepViewer;

import java.awt.Container;

import org.eclipse.swt.SWT;

import tcxml.model.Step;

public class StepViewerFactory {

	public static AbstractStepViewer getViewer(Step step, StepContainer stepContainer) {
		
		AbstractStepViewer tv = null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			tv = getCallFunctionViewer(step,stepContainer)	;
			break;

		default:
			tv = getDefaultViewer(step,stepContainer);
			break;
			
		case "block":
			tv = getBlockViewer(step,stepContainer);
		}
		
		
		
		
		
		
		

		return tv;
	}

	private static AbstractStepViewer getBlockViewer(Step step, StepContainer stepContainer) {
		BlockViewer bw = new BlockViewer(stepContainer.getBar(), SWT.NONE);
		bw.populate(step);
		return bw;
	}

	private static AbstractStepViewer getDefaultViewer(Step step, StepContainer stepContainer) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(stepContainer.getBar(), SWT.NONE);
	stepviewer.populate(step);
		return stepviewer;
	}
	
	

	private static AbstractStepViewer getCallFunctionViewer(Step step, StepContainer stepContainer) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(stepContainer.getBar(), SWT.NONE);
	stepviewer.populate(step);
		return stepviewer;
	}

}
