package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.openqa.selenium.interactions.internal.DisplayAction;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ImportScriptComposite extends Composite{
	private Text selectedDir;

	public ImportScriptComposite(Composite parent, int style) {
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
			private String dirpath;

			@Override
			public void mouseDown(MouseEvent e) {
				
				showDirectorySelection();
				
			}

			private void showDirectorySelection() {
				
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.NONE );
				dirpath = dialog.open();
				selectedDir.setText(dirpath);
				
				
			}
		});
		btnChoose.setText("choose");
		// TODO Auto-generated constructor stub
	
		
		
	
		
		
		
		
		
		
		
		
		
		
		
	}

}
