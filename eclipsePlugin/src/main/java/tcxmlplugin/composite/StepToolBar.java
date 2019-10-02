package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

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
import org.eclipse.swt.widgets.Combo;

public class StepToolBar extends Composite implements IJobChangeListener   {
	
	private PlayingJob currrentJob;
	private StepViewer stepviewer;
	private Button okbutton;
	private Button nookbutton;
	private ProgressBar progressBar;


	private Button playButton;
	private Label label;
	
	private boolean playable;
	private boolean isRunning;
	private Button btnDisabled;
	private Combo comboLevel;
	private Label levellabel;
	
	


	public StepToolBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		isRunning = false ;
		playButton = new Button(this, SWT.NONE);

		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
if(isRunning == false) {
	try {
		play();
	} catch (TcXmlException e1) {
	TcXmlPluginController.getInstance().error("fail to play step ", e1);
	}
	
}else {// job already launched
	
	try {
		stop();
	} catch (TcXmlException e1) {
	TcXmlPluginController.getInstance().error("fail to stop step ", e1);
	}
				
			}


		}
			
		});
		
		playButton.setToolTipText("play");
		playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
		
		okbutton = new Button(this, SWT.NONE);
		okbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-accept.png"));
		okbutton.setVisible(false);
		
		nookbutton = new Button(this, SWT.NONE);
		nookbutton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/dialog-cancel.png"));
		nookbutton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		nookbutton.setVisible(false);
		
		btnDisabled = new Button(this, SWT.CHECK);
		btnDisabled.setText("Disabled");
		
		levellabel = new Label(this, SWT.NONE);
		levellabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		levellabel.setText("level");
		
		comboLevel = new Combo(this, SWT.NONE);
		comboLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		progressBar = new ProgressBar(this , SWT.INDETERMINATE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		progressBar.setVisible(false);
		
		label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));


		

	}
	
	

	
	
	

	
	
	public void setStepviewer(StepViewer stepviewer) {
		this.stepviewer = stepviewer;
		
		
	}




	private void stop() throws TcXmlException {
		throw new TcXmlException(" stop step not implemented yet", new IllegalAccessException());
		
	}




	protected void play() throws TcXmlException {
		
		updatePlayingButton();
		
		
		
		PlayingContext context = new PlayingContext(stepviewer.getController() );		
		currrentJob = stepviewer.getplayInteractiveJob(  context);
		currrentJob.schedule();
	
	}
	
	




	@Override
	public void aboutToRun(IJobChangeEvent event) {
		
		
		
		
		
	getDisplay().asyncExec(new Runnable() {
		
		@Override
		public void run() {
		
			
		
			progressBar.setVisible(true);
			
		
			
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
		
		updatePlayingButton();
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
					progressBar.setVisible(false);						
				
				
				showStatus((PlayingJob)event.getJob());
				
			}
		});
		
	
		TcXmlPluginController.getInstance().info("playing " + event.getJob().getName() + "  ended with status" + event.getResult());
		
		
	}



	private void updatePlayingButton() {
		getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
			if(isRunning == true) {
				playButton.setToolTipText("stop");
				playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-stop-2.png"));
				
				
			}else {
				playButton.setToolTipText("play");
				playButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/media-playback-start-2.png"));
				
				
			}
				
			}
		});
		
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
		btnDisabled.setVisible(isPlayable);
		comboLevel.setVisible(isPlayable);
		levellabel.setVisible(isPlayable);
		
	}









	public void populate(Step mo) {		
		btnDisabled.setSelection(mo.isDisabled() != null?mo.isDisabled():false);
		comboLevel.setItems("1","2","3","43");
		String level = mo.getLevel();
		if(level == null || level.isEmpty()) {
			level ="1";
			
		}
		comboLevel.select(Integer.parseInt(  level));
	}
	
	
}
