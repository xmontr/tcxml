package tcxmlplugin.wizzard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.ImportScriptComposite;

public class ImportScriptPage  extends WizardPage {

	protected ImportScriptPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		ImportScriptComposite composite = new ImportScriptComposite(parent, SWT.NONE);
		setControl(composite);
	}
	
	
	

}
