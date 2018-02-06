package tcxmlplugin.composite.view.arguments;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

public class DefaultArgument extends StepArgument {

	public DefaultArgument(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Label lblNotImplemented = new Label(this, SWT.NONE);
		lblNotImplemented.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNotImplemented.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblNotImplemented.setText("not implemented");
		// TODO Auto-generated constructor stub
	}
}
