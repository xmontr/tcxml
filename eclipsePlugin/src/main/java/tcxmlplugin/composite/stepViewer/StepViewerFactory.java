package tcxmlplugin.composite.stepViewer;

import java.awt.Container;

import org.eclipse.swt.SWT;

import tcxml.model.Step;

public class StepViewerFactory {

	public static AbstractTestViewer getViewer(Step step, StepContainer stepContainer) {
		
		AbstractTestViewer tv = null;
		
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

	private static AbstractTestViewer getBlockViewer(Step step, StepContainer stepContainer) {
		// TODO Auto-generated method stub
		return null;
	}

	private static AbstractTestViewer getDefaultViewer(Step step, StepContainer stepContainer) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(stepContainer.getBar(), SWT.NONE);
		stepviewer.populate(step);
		return stepviewer;
	}
	
	

	private static AbstractTestViewer getCallFunctionViewer(Step step, StepContainer stepContainer) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(stepContainer.getBar(), SWT.NONE);
		stepviewer.populate(step);
		return stepviewer;
	}

}
