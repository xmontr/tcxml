package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import stepWrapper.AbstractStepWrapper;

import stepWrapper.RunBlockWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepViewerRunner;

public class RunBlockView extends StepView implements StepContainer, ExpandListener {
	private ExpandBar bar;
	
	private List<StepViewer> stepViwerChildren ;

	public RunBlockView(Composite parent, int style )  {		
		super(parent, style);
		
		// color for the viewer
		color=SWT.COLOR_DARK_YELLOW ;
		
		GridLayout gridlayout = new GridLayout(1, false);
		setLayout(gridlayout);
		stepViwerChildren = new ArrayList<StepViewer>();
		
		bar = new ExpandBar(this, SWT.NONE);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		bar.setSpacing(10);
		
		bar.addExpandListener(this);
		
		
		
		// TODO Auto-generated constructor stub
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
	
	
	
	public void addStep(Step step) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller,getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(tv);
		 
		 bar.layout();
		
	}
	
	public void addStep(Step step, int index) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller,getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE,index);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(index,tv);
		 
		 bar.layout();
		
	}
	
	
	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
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
	
	
	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
		
		if(! (stepWrapper2 instanceof RunBlockWrapper )) {
			throw new TcXmlException("run block  view can only be populated by from a run block  wrapper ", new IllegalArgumentException());
			
		}
		
		Step model = stepWrapper2.getModel();
		
		
		BoundList<Step> li = model.getStep();
		for (Step step : li) {
			addStep(step);
		}
				
		bar.layout();
		
		
	}





	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(stepViwerChildren);
		
		PlayingContext ret = mc.runSteps(ctx);
		
		return ret;
		
	}


	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}





	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		bar.redraw();
		bar.layout(true,true);
		controller.getLog().info("***************      block ********colapsed");
	
		
	}
	
	
	
	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		controller.getLog().info("***************     block **********expanded");
		
		bar.layout();
		
	}


	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		stepWrapper.export(pw);
	/*	StringBuffer sb = new StringBuffer(" // ").append(getTitle());
		pw.println(sb.toString());
		for (StepViewer stepViewer : stepViwerChildren) {
			stepViewer.export(pw);*/
			
		}


	@Override
	public void saveModel() throws TcXmlException {
		for (int i = 0; i < stepViwerChildren.size(); i++) {
			
			stepViwerChildren.get(i).getViewer().saveModel();
			
			
		}
		
	}


	@Override
	public void reIndex() {
		for (int i = 0; i < stepViwerChildren.size(); i++) {
			
			stepViwerChildren.get(i).getViewer().getStepWrapper().getStep().setIndex(new Integer(i).toString() );
			
			
		}
		
	}
		
	}
