package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.model.Step;
import tcxmlplugin.composite.StepViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

public class CallFunctionView  extends StepViewer{

	public CallFunctionView(Composite parent, int style, TcXmlController controller) {
		super(parent, style,controller);
		
		this.setLayout(new GridLayout(2, false));
		
		Label LibraryNameLabel = new Label(this, SWT.NONE);
		LibraryNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		LibraryNameLabel.setText("Library Name:");
		
		Combo libcombo = new Combo(this, SWT.NONE);
		libcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label functionnameLabel = new Label(this, SWT.NONE);
		functionnameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		functionnameLabel.setText("Function name:");
		
		Combo combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}





	@Override
	public void populate(Step mo) {
		super.populate(mo);
		setTitle("Call Function " + model.getLibName() + "." + model.getFuncName());
		
	}

}
