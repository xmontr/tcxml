package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepRunner;
import tcxmlplugin.model.ForModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import com.kscs.util.jaxb.BoundList;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;

public class ForView extends StepView  implements StepContainer, ExpandListener {
	private TextInputView incrementTxt;
	private TextInputView initText;
	private TextInputView conditionTxt;
	
	private List<StepViewer> stepViwerChildren ;
	
	
	private ForModel formodel ;
	private ExpandBar bar;
	private JsonObject arg;
	private String initString;
	private String conditionString;
	private String incrementString;

	public ForView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		
		formodel = new ForModel();
		
		
		// color for the viewer
		color=SWT.COLOR_DARK_MAGENTA ;
		
		setLayout(new GridLayout(1, false));
		
		Group grpArguments = new Group(this, SWT.NONE);
		grpArguments.setLayout(new GridLayout(2, false));
		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpArguments.setText("Arguments");
		
		Label lblInit = new Label(grpArguments, SWT.NONE);
		lblInit.setText("Init");
		
		initText = new TextInputView(grpArguments, SWT.NONE);
		
		Label lblCondition = new Label(grpArguments, SWT.NONE);
		lblCondition.setText("Condition");
		
		conditionTxt = new TextInputView(grpArguments, SWT.NONE);
		
		Label lblIncrement = new Label(grpArguments, SWT.NONE);
		lblIncrement.setText("Increment");
		
		incrementTxt = new TextInputView(grpArguments, SWT.NONE);
		
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
	public String buildTitle() {
		String ret = formatTitle(model.getIndex(), " " +  buildLoopString() );
		return ret;
	}
	
	
	
	private String buildLoopString() {
	return "for(" + initString +";" + conditionString + ";" + incrementString + ")" ;	
		
	}
	
	
	private String buildPlayingCommand() {
		StringBuffer sb = new StringBuffer();
		sb.append(buildLoopString()).append("{ctx = mc.runSteps(ctx);};");
		
		return sb.toString();
		
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
	MultipleStepRunner mc = new MultipleStepRunner(stepViwerChildren);
	ScriptEngine engine = controller.getJSengine();
	   ScriptContext context = ctx.getJsContext();
		// store the global variables 
	   context.setAttribute("mc", mc, ScriptContext.GLOBAL_SCOPE);
	   context.setAttribute("ctx", ctx, ScriptContext.GLOBAL_SCOPE);
	  try {
		engine.eval(buildPlayingCommand(),context);
	} catch (ScriptException e) {
	
		throw new TcXmlException(" failure in For loop", e);
	} 
	   
	   
	   
	
		return ctx;
	}

	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		bar.redraw();
		bar.layout(true,true);
		controller.getLog().info("***************      for ********colapsed");
		
	}

	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		controller.getLog().info("***************     for  **********expanded");
		
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
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller);
		 
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

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}
	
	public void populate(Step mo) throws TcXmlException {
		// add argument of the for loop
		arg = controller.readJsonObject(mo.getArguments());
		
 initString = readArgValue("Init");
 conditionString = readArgValue("Condition");
 incrementString = readArgValue("Increment");
 
 formodel.setCondition(conditionString);
 formodel.setIncrement(incrementString);
 formodel.setInit(initString);
 
 
 conditionTxt.setJavascript(true);
 conditionTxt.setInputData(conditionString);
 incrementTxt.setJavascript(true);
 incrementTxt.setInputData(incrementString);
 
 initText.setJavascript(true);
 initText.setInputData(initString);
			
		
		// {"Init":{"value":"var i = 0","evalJavaScript":true},"Condition":{"value":"i < 1","evalJavaScript":true},"Increment":{"value":"i++","evalJavaScript":true}}
		
	
		super.populate(mo);
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
	

	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}

	private String readArgValue(String name) {
		JsonObject Obj = arg.getJsonObject(name);
		String val = Obj.getJsonString("value").getString();
		return val;
		
	}

	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		pw.println(getTitle());
		
	}
	
	
	
}
