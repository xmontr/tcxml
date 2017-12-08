package tcxmlplugin.wizzard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import tcxmlplugin.composite.NewProjectTcxmlComposite;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ProjectTcxmlFormPage  extends WizardPage  implements PropertyChangeListener {
	
	
	private NewProjectTcxmlComposite  composite;
	private String projectName;

	protected ProjectTcxmlFormPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		composite = new NewProjectTcxmlComposite(parent,SWT.NONE);
		setControl(composite);
		composite.getModel().addPropertyChangeListener("projectName", this);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		projectName=(String)evt.getNewValue();
		getContainer().updateButtons();
		
	}
	
	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return composite.isFormCompleted();
	}
	
	public String getProjectName() {
		return projectName;
	}

}
