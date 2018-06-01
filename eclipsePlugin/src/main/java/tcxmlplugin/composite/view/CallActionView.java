package tcxmlplugin.composite.view;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

public class CallActionView extends StepView {

	public CallActionView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		this.controller = controller;
		setLayout(new GridLayout(2, false));
		
		Label lblAction = new Label(this, SWT.NONE);
		lblAction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAction.setText("action");
		
		Combo combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}







	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		// TODO Auto-generated method stub
		return null;
	}

}
