package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;

import tcxml.model.Step;

public abstract class AbstractTestViewer extends Composite{
	
	protected Step model;

	public AbstractTestViewer(Composite parent, int style) {
		super(parent, style);
		this.model = new Step();
	}
	
	
	public Step getModel() {
		return model;
	}
	
	
	
	
	public void setModel(Step model) {
		this.model = model;
	}
	
	
	
	
	public void populate( Step mo) {
		
		
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
		
		
	}
	
	public abstract String getTitle() ;
	
	

}
