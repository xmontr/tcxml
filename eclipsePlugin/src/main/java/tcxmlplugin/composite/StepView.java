package tcxmlplugin.composite;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

public abstract class StepView extends Composite  {
	
	public TcXmlController getController() {
		return controller;
	}

	private PropertyChangeSupport propertyChangeSupport;
	
	protected Step model;
	
	private String title ;

	protected TcXmlController controller;
	
	
	public StepView(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller=controller;
		this.model = new Step();
		propertyChangeSupport = new PropertyChangeSupport(this);
		
	}

	public   String getTitle()  {
		
		return this.title;
	}
	
	
	
	
	
	
	public Step getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public  void populate(Step mo) throws TcXmlException   {
		
		
		model.setAction(mo.getAction());
		model.setActionName(mo.getActionName());
		model.setActiveStep(mo.getActiveStep());
		model.setArguments(mo.getArguments());
		model.setAutoEndEventFF(mo.getAutoEndEventFF());
		model.setCatch(mo.getCatch());
		model.setCategoryName(mo.getCategoryName());
		model.setEndEvent(mo.getEndEvent());
		model.setFuncName(mo.getFuncName());
		model.setIndex(mo.getIndex());
		model.setLevel(mo.getLevel());
		model.setLibName(mo.getLibName());
		model.setMethodName(mo.getMethodName());
		model.setName(mo.getName());
		model.setNewAttribute(mo.getNewAttribute());
		model.setObjectTimeout(mo.getObjectTimeout());
		model.setOverwriteUI(mo.getOverwriteUI());
		model.setTestObject(mo.getTestObject());
		model.setType(mo.getType());
		model.setObjectTimeout(mo.getObjectTimeout());
		model.setRecDuration(mo.getRecDuration());
		model.setSection(mo.getSection());
		model.setSnapshotId(mo.getSnapshotId());
		model.setStepId(mo.getStepId());
		model.setTestObject(mo.getTestObject());
		model.getStep().addAll(mo.getStep());
		
		
		
	
	};
	
	
	
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
	
	protected String formatTitle (String index , String txt) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" ").append(index).append(" ").append(txt);
		return sb.toString();
		
	}

}
