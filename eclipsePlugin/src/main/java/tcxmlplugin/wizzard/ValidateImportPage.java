package tcxmlplugin.wizzard;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.TcXmlPluginException;
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

	public boolean  showValidationData(String selectedDirectory, IProject currentProject, IFolder testCaseFolder) {
		
		boolean ret = false;
		compo.setCurrentProject(currentProject);
		compo.setTestCaseFolder(testCaseFolder);
		
		
		setMessage("the folowing file will be imported, click proceed to start to the import");
		try {
			compo.populate(selectedDirectory);
			ret =true;
			
			return ret;
			
		} catch (TcXmlPluginException e) {
			
			TcXmlPluginController.getInstance().error("failure when trying to import Test case", e);
			ret =false;

			setErrorMessage(e.getMessage());
			
		return ret;	
		}
		
		
	}

	public void proceedToImport() {
	compo.proceedToImport();
		
	}
	
	


	
	

}
