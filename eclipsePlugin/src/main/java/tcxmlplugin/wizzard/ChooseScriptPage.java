package tcxmlplugin.wizzard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.ChoosecriptComposite;

public class ChooseScriptPage  extends WizardPage implements PropertyChangeListener, IWizardPage {
	
	
	private String selectedDirectory;
	
	

	protected ChooseScriptPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		ChoosecriptComposite composite = new ChoosecriptComposite(parent, SWT.NONE);
		setControl(composite);
		composite.addPropertyChangeListener("dirPath", this);
		
		setPageComplete(false);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("dirPath")) {
			
			selectedDirectory = (String) evt.getNewValue();
			
			setPageComplete(true);
		}
		
	}

	public String getSelectedDirectory() {
		return selectedDirectory;
	}
	
	
	

}
