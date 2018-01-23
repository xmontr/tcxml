package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxmlplugin.composite.StepViewer;

public class CallFunctionViewer extends AbstractStepViewer {

	public CallFunctionViewer(Composite parent, int style, TcXmlController controller ) {
		super(parent, style, new CallFunctionView(parent , style,controller));

}
	
}
