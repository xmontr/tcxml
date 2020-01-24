package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.JsMultipleStepRunner;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepViewerRunner;
import tcxml.model.ArgModel;
import tcxml.model.ForModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import com.kscs.util.jaxb.BoundList;

import net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.ForWildcardLowerBoundType;
import stepWrapper.AbstractStepWrapper;
import stepWrapper.ForWrapper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

	public ForView(Composite parent, int style){
		super(parent, style);
		
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


	
	
	

	
	
	private String buildPlayingCommand() {
		ForWrapper forwrapper = (ForWrapper)stepWrapper ;
		StringBuffer sb = new StringBuffer();
		sb.append(forwrapper.buildLoopString()).append("{ctx = mc.runSteps(ctx);};");
		
		return sb.toString();
		
	}
	
	
	private String buildPlayingCommand2() {
		ForWrapper forwrapper = (ForWrapper)stepWrapper ;
		StringBuffer sb = new StringBuffer();
		sb.append(forwrapper.buildLoopString()).append("{jsm();};");
		
		return sb.toString();
		
	}
	
	
	
	

	public PlayingContext doplay2(PlayingContext ctx) throws TcXmlException {
		if(stepWrapper.isDisabled() )  {
			controller.getLog().info("step" + getTitle() + " is disabled - don't play it");
			return ctx ;
		}
		
		
	MultipleStepViewerRunner mc = new MultipleStepViewerRunner(stepViwerChildren);
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
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		
		saveModel();
		if(stepWrapper.isDisabled() )  {
			controller.getLog().info("step" + getTitle() + " is disabled - don't play it");
			return ctx ;
		}
		
		
	MultipleStepViewerRunner mc = new MultipleStepViewerRunner(stepViwerChildren);
	JsMultipleStepRunner jsm = new JsMultipleStepRunner(mc, ctx);
	
	ScriptEngine engine = controller.getJSengine();
	   ScriptContext context = ctx.getJsContext();
		// store the global variables 
	   context.setAttribute("jsm", jsm, ScriptContext.GLOBAL_SCOPE);
	  
	  try {
		engine.eval(buildPlayingCommand2(),context);
	} catch (ScriptException e) {
	
		throw new TcXmlException(" failure in For loop", e);
	}
	  
	 catch (RuntimeException e) {
		
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

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}
	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
		
		if(! (stepWrapper2 instanceof ForWrapper )) {
			throw new TcXmlException("For view can only be populated by from a for wrapper ", new IllegalArgumentException());
			
		}
		
		ForWrapper forwrapper = (ForWrapper)stepWrapper2 ;
		
		HashMap<String, ArgModel> argumentMap = stepWrapper2.getArgumentMap();	
		
		
initText.SetArgModel(argumentMap.get("Init"));
conditionTxt.SetArgModel(argumentMap.get("Condition"));
incrementTxt.SetArgModel(argumentMap.get("Increment"));

initString = initText.getArgModel().getValue();
incrementString = incrementTxt.getArgModel().getValue();
conditionString= conditionTxt.getArgModel().getValue();
			
		
		// {"Init":{"value":"var i = 0","evalJavaScript":true},"Condition":{"value":"i < 1","evalJavaScript":true},"Increment":{"value":"i++","evalJavaScript":true}}
		
	
		
		// add children
		BoundList<Step> li = forwrapper.getSteps();	
		
		for (Step step : li) {
			addStep(step);
		}
				
		bar.layout();
		
	
	}
	
	@Override
	public void saveModel() throws TcXmlException {
		
		HashMap<String, ArgModel> argval = new HashMap<String, ArgModel>();
		
		argval.put("Init", initText.getArgModel());
		argval.put("Condition", conditionTxt.getArgModel());
		argval.put("Increment", incrementTxt.getArgModel());
		
		
		stepWrapper.saveArguments(argval);
		
	}
	



	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		ForWrapper forwrapper = (ForWrapper)stepWrapper ;
		forwrapper.export(pw);
		

	
	
		
	}
	
	
	
}
