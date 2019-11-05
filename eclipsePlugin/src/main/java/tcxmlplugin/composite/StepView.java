package tcxmlplugin.composite;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.core.FfMpegWrapper;
import tcxml.core.Playable;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.view.arguments.StepArgument;

public abstract class StepView extends Composite  implements Playable{
	
	public TcXmlController getController() {
		return controller;
	}

	private PropertyChangeSupport propertyChangeSupport;
	
	protected Step model;
	
	protected AbstractStepWrapper stepWrapper ;
	
	
	
	protected StepArgument theArgument;
	
	protected TruLibrary Library; 
	
	

	
	
	public TruLibrary getLibrary() {
		return Library;
		
		
	}

	public void setLibrary(TruLibrary library) {
		Library = library;
	}
	
	
	
	public StepArgument getTheArgument() {
		return theArgument;
	}

	protected void setTheArgument(StepArgument theArgument) {
		propertyChangeSupport.firePropertyChange("theArgument", this.theArgument ,
				this.theArgument = theArgument );
		
	}

	private String title ;

	public TcXmlController controller;
	
	protected StepViewer viewer;
	
	protected int color = SWT.COLOR_DARK_GREEN;

	protected HashMap<String, ArgModel> argumentMap;

	private long subTitleInterval;
	
	
	public int getColor() {
		return color;
	}

	public StepViewer getViewer() {
		return viewer;
	}

	public void setViewer(StepViewer viewer) {
		this.viewer = viewer;
	}

	public StepView(Composite parent, int style )  {
		super(parent, style);
	
		propertyChangeSupport = new PropertyChangeSupport(this);
		
		subTitleInterval = Long.parseLong(TcXmlPluginController.getInstance().getProperties().getProperty("RS.interstepInterval"));
		
	}

	public void setStepWrapper(AbstractStepWrapper stepWrapper) throws TcXmlException {
		this.stepWrapper = stepWrapper;
		
		this.controller=stepWrapper.getController();
		this.Library = stepWrapper.getLibrary();
		this.model = stepWrapper.getModel();
		argumentMap = controller.getArguments(model,stepWrapper.getDefaultArguments());
		setTitle(stepWrapper.getTitle());
		populate();

			
		
	}

	public AbstractStepWrapper getStepWrapper() {
		return stepWrapper;
	}

	public   String getTitle()  {
		
		return this.title;
	}
	
	

	

	
	public Step getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	
	/**
	 *   fill data in the model
	 * 
	 * 
	 * @param mo
	 * @throws TcXmlException
	 */

	public  abstract void populate() throws TcXmlException   ;
	
	
	
	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }

		  public void removePropertyChangeListener(PropertyChangeListener listener) {
		    propertyChangeSupport.removePropertyChangeListener(listener);
		  }

		public void setTitle(String title) {
			propertyChangeSupport.firePropertyChange("title", this.title ,
					this.title = title );
		
		}
	

	
	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		controller.manageStartStopTransaction(stepWrapper);
		FfMpegWrapper vr = TcXmlPluginController.getInstance().getCurrentVideoRecorder() ;
		if(vr != null &&  vr.isRecording() ) {
			
		String subtitle =	controller.getSubtitle(stepWrapper);			
		vr.addSubtitle(LocalTime.now(), subtitle,subTitleInterval);
			
		}
		PlayingContext ret = doplay(ctx);
		controller.manageStartStopTransaction(stepWrapper);
		return ret;
	}

	protected abstract  PlayingContext doplay(PlayingContext ctx) throws TcXmlException  ;
	
	public abstract void export(PrintWriter pw) throws TcXmlException  ;
	
	
	

}
