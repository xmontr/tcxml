package tcxmlplugin.composite.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;

public class IfElsecontainer implements StepContainer, ExpandListener {
	
	private ArrayList<StepViewer> stepViwerChildren = new ArrayList<StepViewer>();
	
	public StepView ifview ;
	
	private ExpandBar thebar;
	
	
	public IfElsecontainer( StepView ifview , ExpandBar bar ) {
		
		this.ifview = ifview ;
		this.thebar = bar ;
	}
	

	public StepView getIfview() {
		return ifview;
	}


	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		thebar.redraw();
		thebar.layout(true,true);
		this.ifview.controller.getLog().info("***************      if container ********colapsed");
		
	}

	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		this.ifview.controller.getLog().info("***************     if container  **********expanded");
		
		thebar.layout();
		
	}

	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return thebar;
	}

	@Override
	public void clean() {
		ExpandItem[] li = thebar.getItems();
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
		thebar.redraw();
		
	}
	
	@Override
	public void remove(Step step) throws TcXmlException {
	for (StepViewer stepViewer : stepViwerChildren) {
		
		String currentid = stepViewer.getViewer().getStepWrapper().getStepId()	;
		if(currentid.equals(step.getStepId())) {
			stepViewer.dispose();
			stepViwerChildren.remove(stepViewer);
			reIndex();
			
		}
		
		
	}
		
	}
	

	@Override
	public void addStep(Step step) throws TcXmlException {
		
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, this.ifview.controller,this.ifview.getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(thebar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(tv);
		 
		 thebar.layout();
		
	}
	
	
	
	@Override
	public void addStep(Step step, int index) throws TcXmlException {
		
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, this.ifview.controller,this.ifview.getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(thebar, SWT.NONE,index);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(index,tv);
		 
		 thebar.layout();
		
	}
	
	
	
	

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}


	@Override
	public TruLibrary getLibrary() {
		// TODO Auto-generated method stub
		return ifview.getLibrary();
	}


	@Override
	public TcXmlController getController() {
		// TODO Auto-generated method stub
		return ifview.getController();
	}


	@Override
	public void reIndex() {
		for (int i = 0; i < stepViwerChildren.size(); i++) {
			
			stepViwerChildren.get(i).getViewer().getStepWrapper().getStep().setIndex(new Integer(i).toString() );
			
			
		}
		
	}
	
	
}