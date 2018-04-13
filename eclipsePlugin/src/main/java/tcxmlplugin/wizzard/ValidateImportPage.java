package tcxmlplugin.wizzard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.PanelImportComposite;



public class ValidateImportPage extends WizardPage implements  IWizardPage {

	protected ValidateImportPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
	PanelImportComposite compo = new PanelImportComposite(parent, SWT.NONE);
	setControl(compo);
		
	}

	public void showValidationData(String selectedDirectory) {
		// list file to import
		
	}

}
