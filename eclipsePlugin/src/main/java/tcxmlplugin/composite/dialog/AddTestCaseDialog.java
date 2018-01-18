package tcxmlplugin.composite.dialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import tcxmlplugin.composite.AddTestCaseComposite;

public class AddTestCaseDialog  extends Dialog implements PropertyChangeListener {
	
	private IFolder targetFolder;
	private AddTestCaseComposite composite;




	public AddTestCaseDialog(Shell parentShell, IFolder selectedFolder) {
		super(parentShell);
		
		targetFolder = selectedFolder;
	
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		composite = new AddTestCaseComposite(parent, SWT.NONE);
		composite.setTargetFolder(targetFolder);
		
		composite.getModel().addPropertyChangeListener("valid", this);
		
		return composite;
	}
	
	
	
	
	  @Override
	  protected void createButtonsForButtonBar(Composite parent) {
	    createButton(parent, IDialogConstants.OK_ID, "Add Test Case", true);
	    createButton(parent, IDialogConstants.CANCEL_ID,
	        IDialogConstants.CANCEL_LABEL, false);
	    getButton(IDialogConstants.OK_ID).setEnabled(false);
	  }
	  
	  
	  
	  
	  
	  
	  public String getNewTestCaseName() {
		  
		  return composite.getNewTestCaseName();
	  }


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if((boolean) evt.getNewValue()  ){
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}else{
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
		}

}
