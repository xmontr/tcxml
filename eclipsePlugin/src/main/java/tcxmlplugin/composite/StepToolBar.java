package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StepToolBar extends Composite  {
	
	
	private Label indexLabel;
	private StepViewer stepviewer;
	private Button highlightbutton;
	private Button okbutton;
	private Button nookbutton;
	private ProgressBar progressBar;

	public StepToolBar(Composite parent, int style, StepViewer step) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		this.stepviewer = step;
		
		 indexLabel = new Label(this, SWT.NONE);
		
		Button playButton = new Button(this, SWT.NONE);
		playButton.setToolTipText("play");
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {				
		
play();
				
			}
		});
		playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		
		highlightbutton = new Button(this, SWT.NONE);
		highlightbutton.setToolTipText("highlight");
		highlightbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/system-search-6.png"));
		
		okbutton = new Button(this, SWT.NONE);
		okbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-accept.png"));
		
		nookbutton = new Button(this, SWT.NONE);
		nookbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-cancel.png"));
		nookbutton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		

	}
	
	protected void play() {

		TcXmlController ct = stepviewer.getViewer().getController()	;
	Step st = stepviewer.getViewer().getModel();
	
	try {
		ct.playSingleStep(st);
	} catch (TcXmlException e1) {
		TcXmlPluginController.getInstance().error("fail to play step", e1);
		
	
	}
		
		
		
	}

	public void setIndex(String  index) {
		
		this.indexLabel.setText(index);
		
	}
	

}
