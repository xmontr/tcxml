package tcxmlplugin.composite.stepViewer;

import java.awt.Container;

import org.eclipse.swt.SWT;

import tcxml.core.TcXmlController;
import tcxml.model.Step;

public class StepViewerFactory {

	public static AbstractStepViewer getViewer(Step step, StepContainer stepContainer, TcXmlController controller) {
		
		AbstractStepViewer tv = null;
		
		String typeOfStep = step.getType();
		switch (typeOfStep) {
		case "callFunction":
			tv = getCallFunctionViewer(step,stepContainer,controller)	;
			break;

		default:
			tv = getDefaultViewer(step,stepContainer,controller);
			break;
			
		case "block":
			tv = getBlockViewer(step,stepContainer,controller);
		}
		
		
		
		
		
		
		

		return tv;
	}

	private static AbstractStepViewer getBlockViewer(Step step, StepContainer stepContainer, TcXmlController controller) {
		BlockViewer bw = new BlockViewer(stepContainer.getBar(), SWT.NONE,controller);
		bw.populate(step);
		return bw;
	}

	private static AbstractStepViewer getDefaultViewer(Step step, StepContainer stepContainer, TcXmlController controller) {
		DefaultStepViewer stepviewer = new DefaultStepViewer(stepContainer.getBar(), SWT.NONE,controller);
	stepviewer.populate(step);
		return stepviewer;
	}
	
	

	private static AbstractStepViewer getCallFunctionViewer(Step step, StepContainer stepContainer, TcXmlController controller) {
		CallFunctionViewer stepviewer = new CallFunctionViewer(stepContainer.getBar(), SWT.NONE,controller);
	stepviewer.populate(step);
		return stepviewer;
	}

}
