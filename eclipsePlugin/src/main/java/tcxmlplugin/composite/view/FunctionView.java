package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.ForWrapper;
import stepWrapper.FunctionWrapper;
import tcxml.model.Function;
import tcxml.model.FunctionArgModel;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.dnd.MyExpandBarDropUtil;
import tcxmlplugin.job.MultipleStepViewerRunner;
import tcxmlplugin.job.PlayingJob;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import tcxmlplugin.composite.FunctionArgumentEditor;

public class FunctionView extends StepView implements StepContainer, ExpandListener {
	private ExpandBar bar;

	

	private Function function;

	private List<StepViewer> stepViwerChildren;



	private DropTarget expandbardroptarget;
	private FunctionArgumentEditor functionArgumentEditor;

	

	public String getLibName() {
		return Library.getStep().getAction();
	}

	/*
	 * public void setLibName(String libName) { this.libName = libName; }
	 */



	public FunctionView(Composite parent, int style)  {
		super(parent, style);
		
		// color for the viewer
		color=SWT.COLOR_DARK_BLUE ;
		
		GridLayout gridlayout = new GridLayout(1, false);
		setLayout(gridlayout);
		stepViwerChildren = new ArrayList<StepViewer>();
		
		Group grpFunctionArguments = new Group(this, SWT.NONE);
		grpFunctionArguments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpFunctionArguments.setText("Function arguments");
		grpFunctionArguments.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		functionArgumentEditor = new FunctionArgumentEditor(grpFunctionArguments, SWT.NONE);
		bar = new ExpandBar(this, SWT.NONE);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		bar.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		bar.setSpacing(10);
		bar.addExpandListener(this);
		
		expandbardroptarget = new MyExpandBarDropUtil(this).buildDropTarget();

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

		StepViewer tv = StepViewerFactory.getViewer(step, this, controller,getLibrary());
		
		
		 


		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());

		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);

		stepViwerChildren.add(tv);

	}
	
	
	public void addStep(Step step, int index) throws TcXmlException {

		StepViewer tv = StepViewerFactory.getViewer(step, this, controller,getLibrary());
		
		
		 


		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE,index);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());

		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);

		stepViwerChildren.add(index,tv);

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
			if (innercontrol instanceof StepContainer) {

				expandItem.dispose();
				((StepContainer) innercontrol).clean();

			}

			else {
				innercontrol.dispose();
				expandItem.dispose();
			}

		}
		bar.redraw();
		stepViwerChildren.clear();
	}

	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
		
		
		if(! (stepWrapper2 instanceof FunctionWrapper )) {
			throw new TcXmlException("Function  view can only be populated by from a function  wrapper ", new IllegalArgumentException());
			
		}
		
		
		Step model = stepWrapper2.getModel();
		
		sanityCheck(model);
	
		
		/*
		 * function = new Function(); function.setName(model.getAction());
		 * function.setId(model.getStepId());
		 * function.setArgumentSchema(model.getArgsSchema());
		 * function.setIndex(model.getIndex()); function.setLevel(model.getLevel());
		 */

		
		//shemaarg of the function
		HashMap<String, FunctionArgModel> sargs = ((FunctionWrapper)stepWrapper).getSchemaArgs();
		functionArgumentEditor.edit(sargs);
		
		
		
		// first step child is internal - skipit
		
	
		BoundList<Step> li = model.getStep().get(0).getStep();
		for (Step step : li) {
			addStep(step);
		}

		
		bar.layout(true,true);
	}

	/*
	 * public Function getFunction() { return function; }
	 */

	private void sanityCheck(Step mo) throws TcXmlException {
		Step firstchild = mo.getStep().get(0);
		if (!(firstchild.getType().equals("block") && firstchild.getAction().equals("internal"))) {

			throw new TcXmlException("bad structure for function step - expected block internal not found",
					new IllegalStateException());

		}

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

			
			
	
controller.getLog().info("***************     function  **********collpased ");

		

		
		
	
		
	}
	
	
	
	@Override
	public void itemExpanded(ExpandEvent e) {

		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);

		controller.getLog().info("***************     function **********expanded");
		

		
	}

	@Override
	
		public void export(PrintWriter pw) throws TcXmlException {
		
		stepWrapper.export(pw);
		
		

			
		}
	
	
	public String getFunctionName() {
		String name = ((FunctionWrapper)		stepWrapper).getFunctionName();
		
return name;
	}

	@Override
	public void saveModel() throws TcXmlException {
		super.saveModel(); // save argument
		
		//save schema args
		FunctionWrapper fwrapper = (FunctionWrapper)		stepWrapper;
		fwrapper.setSchemaArgs(functionArgumentEditor.getModel());
		fwrapper.saveShemaArgs();
		//save children
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
		
	@Override
	public void dispose() {
		
		super.dispose();
		
	expandbardroptarget.dispose();	
	}

}
