package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.view.TestObjectView;
import tcxmlplugin.job.PlayingJob;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StepToolBar extends Composite implements IJobChangeListener   {
	
	private PlayingJob currrentJob;
	private StepViewer stepviewer;
	private Button okbutton;
	private Button nookbutton;
	private ProgressBar progressBar;


	private Button playButton;
	private Label label;
	
	private boolean playable;
	
	


	public StepToolBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		
		
		playButton = new Button(this, SWT.NONE);
		playButton.setToolTipText("play");
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
		
try {
	play();
} catch (TcXmlException e1) {
TcXmlPluginController.getInstance().error("fail to play step ", e1);
}
				
			}
		});
		playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		
		okbutton = new Button(this, SWT.NONE);
		okbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-accept.png"));
		okbutton.setVisible(false);
		
		nookbutton = new Button(this, SWT.NONE);
		nookbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-cancel.png"));
		nookbutton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		nookbutton.setVisible(false);
		

		progressBar = new ProgressBar(this , SWT.INDETERMINATE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		progressBar.setVisible(false);
		
		label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		

	}
	
	

	
	
	

	
	
	public void setStepviewer(StepViewer stepviewer) {
		this.stepviewer = stepviewer;
		
		
	}









	protected void play() throws TcXmlException {
		
				
		currrentJob = stepviewer.getplayInteractiveJob( new PlayingContext(stepviewer.getController() ));
		currrentJob.schedule();
	
	}
	
	




	@Override
	public void aboutToRun(IJobChangeEvent event) {
		
		
		
		
		
	getDisplay().asyncExec(new Runnable() {
		
		@Override
		public void run() {
		
			
		if(playable)	{
			progressBar.setVisible(true);
			
		}
			
		hideStatus();
			
		}

		private void hideStatus() {
			nookbutton.setVisible(false);
			nookbutton.setToolTipText("");
			okbutton.setVisible(false);
			
		}
	});
		


	
		
	}



	@Override
	public void awake(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void done(IJobChangeEvent event) {
		
		
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if(playable) {
					progressBar.setVisible(false);						
				}
				
				showStatus((PlayingJob)event.getJob());
				
			}
		});
		
	
		TcXmlPluginController.getInstance().info("playing job" + event.getJob().getName() + "  ended with status" + event.getResult());
		
		
	}



	private void showStatus(PlayingJob job) {
		
		if(job.getResult() == Status.OK_STATUS) {
		okbutton.setVisible(true);	
		nookbutton.setVisible(false);
			
		}else {
			
			okbutton.setVisible(false);	
			nookbutton.setVisible(true);
			nookbutton.setToolTipText(job.getErrorMessage());
			
		}
		
	}









	@Override
	public void running(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void scheduled(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sleeping(IJobChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPlayable( boolean isPlayable) {
		this.playable = isPlayable ;
		playButton.setVisible(isPlayable);
		
	}
	
	
}
