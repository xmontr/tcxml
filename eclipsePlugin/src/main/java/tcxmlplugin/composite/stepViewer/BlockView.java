package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import tcxml.model.Step;
import tcxmlplugin.composite.StepViewer;

public class BlockView  extends StepViewer implements StepContainer {
	private ExpandBar bar;
	
	private Step model;

	public BlockView(Composite parent, int style) {
		
		
		super(parent, style);
		setLayout(new FillLayout());
		this.model=new Step();
		bar = new ExpandBar(this, SWT.BORDER);
		bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void addStep(Step step) {
		
		 AbstractStepViewer tv = StepViewerFactory.getViewer(step,this);

	
			

	
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		
		
		
	}
	
	
	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
	}
	

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "title";
	}

	@Override
	public Step getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	@Override

	public void clean() {
		ExpandItem[] li = bar.getItems();
		for (ExpandItem expandItem : li) {
		Control innercontrol = expandItem.getControl();
		if( innercontrol instanceof StepContainer) {
			
			expandItem.dispose();
			((StepContainer) innercontrol).clean();
			
		}
			
			
		else {
			innercontrol.dispose();
			expandItem.dispose();
		}
			
		}
		bar.redraw();
	}
	
	public void populate(Step mo) {
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
		
		BoundList<Step> li = mo.getStep();
		for (Step step : li) {
			addStep(step);
		}
				
		
	}

}
