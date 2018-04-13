package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.openqa.selenium.interactions.internal.DisplayAction;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ChoosecriptComposite extends Composite{
	
	
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	


	private String dirPath ;


	public String getDirPath() {
		return dirPath;
	}



	public void setDirPath(String dirPath) {
		propertyChangeSupport.firePropertyChange("dirPath", this.dirPath,this.dirPath = dirPath);
		
	}

	private Text selectedDir;
	
	
	

	public ChoosecriptComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblSelectScriptFolder = new Label(this, SWT.NONE);
		lblSelectScriptFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSelectScriptFolder.setText("folder selection");
		
		selectedDir = new Text(this, SWT.BORDER);
		selectedDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Button btnChoose = new Button(this, SWT.NONE);
		btnChoose.addMouseListener(new MouseAdapter() {
		

			@Override
			public void mouseDown(MouseEvent e) {
				
				showDirectorySelection();
				
			}

	
		});
		btnChoose.setText("choose");
		// TODO Auto-generated constructor stub
	
		
		
	
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	private void showDirectorySelection() {
		
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.NONE );
		String dirpath = dialog.open();
		
		setDirPath(dirpath);
		selectedDir.setText(dirpath);
		
		
		
	}
	
	
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }
	

}
