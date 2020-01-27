package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.CallActionWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.ListArgModel;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.ActionView;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.TcViewer;

public class CallActionView extends StepView {

	private ListInputView actionInput;

	public CallActionView(Composite parent, int style) {
		super(parent, style);
		// color for the viewer
		color = SWT.COLOR_DARK_CYAN;

		setLayout(new GridLayout(2, false));

		Label lblAction = new Label(this, SWT.NONE);
		lblAction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAction.setText("action");

		actionInput = new ListInputView(this, getStyle());
		actionInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {

		if (!(stepWrapper2 instanceof CallActionWrapper)) {
			throw new TcXmlException("call action view can only be populated by from a cal action  wrapper ",
					new IllegalArgumentException());

		}

		ListArgModel ar = ((CallActionWrapper) stepWrapper2).getCalledAction();
		actionInput.setArgmodel(ar);

	}
	@Override
	public void saveModel() throws TcXmlException {

		CallActionWrapper wr = (CallActionWrapper) stepWrapper;
		wr.saveArguments();

	}

	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {

		saveModel();

		TcViewer tcviewer = TcXmlPluginController.getInstance().getTcviewer();
		
		String actionName = actionInput.getValueSelected();
		
	ActionView action	= tcviewer.getActionsViewer().getActionView(actionName);
	
	PlayingContext ret = action.play(ctx);
		
		return ret;
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {

		saveModel();

		CallActionWrapper wr = (CallActionWrapper) stepWrapper;
		wr.export(pw);

	}
}
