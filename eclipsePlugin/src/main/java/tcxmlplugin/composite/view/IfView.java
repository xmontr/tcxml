package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.IfModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepRunner;

public class IfView extends StepView  implements StepContainer, ExpandListener{
	
	private TextInputView conditionTxt;
	
	private List<StepViewer> stepViwerChildren ;
	
	private String conditionString;
	
	private ExpandBar bar;
	
	private IfModel ifmodel ;
	
	private JsonObject arg;

	public IfView(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);
		// color for the viewer
		color=SWT.COLOR_DARK_MAGENTA ;
		
		ifmodel = new IfModel();
		
		setLayout(new GridLayout(1, false));
		
		Group grpArguments = new Group(this, SWT.NONE);
		grpArguments.setLayout(new GridLayout(2, false));
		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpArguments.setText("Arguments");
		
		Label lblCondition = new Label(grpArguments, SWT.NONE);
		lblCondition.setText("Condition");
		
		conditionTxt = new TextInputView(grpArguments, SWT.NONE);
		
		Group grpSteps = new Group(this, SWT.NONE);
		grpSteps.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSteps.setText("Steps");
		
		bar = new ExpandBar(grpSteps, SWT.NONE);
		stepViwerChildren = new ArrayList<StepViewer>();
		bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		bar.setSpacing(10);
		
		bar.addExpandListener(this);
		
	}

	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		bar.redraw();
		bar.layout(true,true);
		controller.getLog().info("***************      if ********colapsed");
		
	}

	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		controller.getLog().info("***************     if  **********expanded");
		
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
	
	
	public void populate(Step mo) throws TcXmlException {
		super.populate(mo);
	ArgModel cond = argumentMap.get("Condition");
	//javascript flag is not set so put it manually
	cond.setIsJavascript(true);
		
		

		
		
		conditionString=cond.getValue();
		
		
		

conditionTxt.SetArgModel(cond);

			
		
		// {"Init":{"value":"var i = 0","evalJavaScript":true},"Condition":{"value":"i < 1","evalJavaScript":true},"Increment":{"value":"i++","evalJavaScript":true}}
		
	
		
		// add cildren
		BoundList<Step> li = mo.getStep();
		//firdt dtep is block interval, skip it
		Step firstchild = li.get(0);				
				sanitycheck(firstchild);
		li=firstchild.getStep();
		
		
		
		
		for (Step step : li) {
			addStep(step);
		}
				
		bar.layout();
		
	
	}
	
	
	
	
	

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}

	@Override
	public String buildTitle() throws TcXmlException {
		String ret = formatTitle(model.getIndex(), " " +  buildIfString() );
		return ret;
	}

	private String buildIfString() {
		// TODO Auto-generated method stub
		return "if( ("  + argumentMap.get("Condition").getValue()  + ")  == true)" ;
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		ArgModel cond = argumentMap.get("Condition");
		
	String val = controller.evaluateJsArgument(cond, ctx.getCurrentExecutionContext());
	
	boolean ok = Boolean.parseBoolean(val);
	
	controller.getLog().info("if clause condition :" + cond.getValue());
	controller.getLog().info("if value:" + ok);
	if(ok) {
		
	runChildSteps(ctx);	
		
	} else {
		controller.getLog().info("condition not matched, skip inside step" );
		
	}
		
		
		return ctx;
	}

	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		// TODO Auto-generated method stub
		
	}
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}
	
	
	private void runChildSteps(PlayingContext ctx) throws TcXmlException {
		MultipleStepRunner mc = new MultipleStepRunner(stepViwerChildren);
		mc.runSteps(ctx);
		
	}
	
	

}
