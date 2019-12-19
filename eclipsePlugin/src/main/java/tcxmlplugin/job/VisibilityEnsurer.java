package tcxmlplugin.job;

import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class VisibilityEnsurer implements Runnable {
	
	
	public VisibilityEnsurer(StepViewer theviewer) {
		super();
		this.theviewer = theviewer;
	}

	private StepViewer theviewer;

	@Override
	public void run() {
		if(theviewer != null) {
		TcXmlPluginController.getInstance().getTcviewer().ensureVisibility(theviewer);
		}
		
	}

}
