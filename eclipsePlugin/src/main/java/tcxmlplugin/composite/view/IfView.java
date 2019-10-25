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

import stepWrapper.IfWrapper;
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
import tcxmlplugin.job.MultipleStepViewerRunner;
import util.TcxmlUtils;
import org.eclipse.wb.swt.SWTResourceManager;

public class IfView extends StepView  implements StepContainer, ExpandListener{
	
	
	

	
	
	
	private TextInputView conditionTxt;
	
	private List<StepViewer> stepViwerChildren ;
	
	private String conditionString;
	
	ExpandBar ifbar;
	
	private IfModel ifmodel ;
	
	private JsonObject arg;
	
	
	private IfElsecontainer ifcontainer ;
	
	private IfElsecontainer elsecontainer ;

	 ExpandBar elsebar;

	private Group grpElse;

	public IfView(Composite parent, int style)  {
		super(parent, style );
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
		
		stepViwerChildren = new ArrayList<StepViewer>();
		
		ifbar = new ExpandBar(grpSteps, SWT.NONE);		
		ifbar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		ifbar.setSpacing(10);
		
		
		
		grpElse = new Group(this, SWT.NONE);
		grpElse.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpElse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElse.setText("else");
		grpElse.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		elsebar = new ExpandBar(grpElse, SWT.NONE);
		elsebar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		elsebar.setSpacing(10);
		
		ifcontainer = new IfElsecontainer(this , ifbar);
		ifbar.addExpandListener(ifcontainer);
		
		elsecontainer = new IfElsecontainer(this,elsebar);
		elsebar.addExpandListener(elsecontainer);
		
	}

	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		ifbar.redraw();
		ifbar.layout(true,true);
		controller.getLog().info("***************      if ********colapsed");
		
	}

	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		controller.getLog().info("***************     if  **********expanded");
		
		ifbar.layout();
		
	}

	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return ifbar;
	}

	@Override
	public void clean() {
		ExpandItem[] li = ifbar.getItems();
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
		ifbar.redraw();
		
	}

	@Override
	public void addStep(Step step) throws TcXmlException {
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller,getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(ifbar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(tv);
		 
		 ifbar.layout();
		
	}
	
	@Override
	public void populate() throws TcXmlException {

	ArgModel cond = argumentMap.get("Condition");
	//javascript flag is not set so put it manually
	cond.setIsJavascript(true);	
		
		conditionString=cond.getValue();
		

conditionTxt.SetArgModel(cond);

			
		
		// {"Init":{"value":"var i = 0","evalJavaScript":true},"Condition":{"value":"i < 1","evalJavaScript":true},"Increment":{"value":"i++","evalJavaScript":true}}
		
		
		// add if cildren
		BoundList<Step> li = model.getStep();
		//first step is block interval, skip it
		Step firstchild = li.get(0);				
				sanitycheck(firstchild);
			
		
		for (Step step : firstchild.getStep()) {
			ifcontainer.addStep(step);
		}
				
		ifbar.layout();
		
		
	
		if(li.size() > 1) { 	//add else children
			
			Step secondchild = li.get(1);
			for (Step step : secondchild.getStep()) {
			elsecontainer.addStep(step);
			}
			
			elsebar.layout();	
			grpElse.setVisible(true);
			
		}else { // not else present
			
			grpElse.setVisible(false);	
			
		}
		
		
		
		
	
	}
	
	
	
	
	

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}



	private String buildIfString() {
		// TODO Auto-generated method stub
		return "if( ("  + argumentMap.get("Condition").getValue()  + ")  ) {" ;
	}
	
	
	private String exportIfString() {
		
	StringBuffer ret = new StringBuffer();
	
	String input=argumentMap.get("Condition").getValue();
	String escapeChar ="\"";
	ret.append("if( TC.eval(").append( TcxmlUtils.formatAsJsString(input, escapeChar )  ).append(" )){");
	
	return ret.toString();
		
		
	}
	

	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		ArgModel cond = argumentMap.get("Condition");
		
	String val = controller.evaluateJsArgument(cond, ctx.getCurrentExecutionContext());
	
	boolean ok = Boolean.parseBoolean(val);
	
	controller.getLog().info("if clause condition :" + cond.getValue());
	controller.getLog().info("if value:" + ok);
	if(ok) {
		
	runChildSteps(ctx);	
		
	} else {
		controller.getLog().info("condition not matched, skip inside step and run else step if exists" );
		
		runelseSteps(ctx);
		
	}
		
		
		return ctx;
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer();
		pw.println(" // " + getTitle());
		sb.append(exportIfString()) ;
		pw.println(sb);
		List<StepViewer> list =ifcontainer.getChildViewer() ;
		for (StepViewer stepViewer : list) {
			stepViewer.export(pw);
		}		
		 
		 pw.println("}//fin if " ) ;
		 pw.println(" else { " ) ;
		 
			List<StepViewer> listelse =elsecontainer.getChildViewer() ;
			for (StepViewer stepViewer : listelse) {
				stepViewer.export(pw);
			}
		
		pw.println("}//fin else " ) ;
		
		
	}
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}
	
	
	private void runChildSteps(PlayingContext ctx) throws TcXmlException {
		
		
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(ifcontainer.getChildViewer());
		mc.runSteps(ctx);
		
	}
	
	
	private void runelseSteps(PlayingContext ctx) throws TcXmlException {
		
	List<StepViewer> elsesteps = elsecontainer.getChildViewer() ;
	
	if(elsesteps.size() >0 ) {
		
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(elsesteps);
		mc.runSteps(ctx);
		
		
	}
	
		
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(ifcontainer.getChildViewer());
		mc.runSteps(ctx);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
