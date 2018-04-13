package tcxmlplugin.wizzard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.PanelImportComposite;



public class ValidateImportPage extends WizardPage implements  IWizardPage {

	private PanelImportComposite compo;

	protected ValidateImportPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
	compo = new PanelImportComposite(parent, SWT.NONE);
	setControl(compo);
		
	}

	public void showValidationData(String selectedDirectory) {
		setMessage("the follwing file will be imported, click proceed to start to the import");
		compo.populate(selectedDirectory);
		// list file to import
		
	}

}
