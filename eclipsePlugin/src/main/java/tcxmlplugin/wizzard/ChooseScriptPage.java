package tcxmlplugin.wizzard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.ImportScriptComposite;

public class ChooseScriptPage  extends WizardPage implements PropertyChangeListener {

	protected ChooseScriptPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		ImportScriptComposite composite = new ImportScriptComposite(parent, SWT.NONE);
		setControl(composite);
		composite.addPropertyChangeListener("dirPath", this);
		
		setPageComplete(false);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("dirPath")) {
			
			setPageComplete(true);
		}
		
	}
	
	
	

}
