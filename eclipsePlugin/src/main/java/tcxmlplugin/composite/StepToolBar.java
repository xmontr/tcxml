package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxmlplugin.composite.stepViewer.AbstractStepViewer;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StepToolBar extends Composite  {
	
	
	private Label indexLabel;
	private AbstractStepViewer step;

	public StepToolBar(Composite parent, int style, AbstractStepViewer step) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		this.step = step;
		
		 indexLabel = new Label(this, SWT.NONE);
		
		Button playButton = new Button(this, SWT.NONE);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				try {
					TcXmlController.getInstance().playSingleStep(step.getViewer().getModel());
				} catch (TcXmlException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		new Label(this, SWT.NONE);
		
		Button okbutton = new Button(this, SWT.NONE);
		okbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-accept.png"));
		
		Button nookbutton = new Button(this, SWT.NONE);
		nookbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-cancel.png"));
		nookbutton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		ProgressBar progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		

	}
	
	public void setIndex(String  index) {
		
		this.indexLabel.setText(index);
		
	}
	

}
