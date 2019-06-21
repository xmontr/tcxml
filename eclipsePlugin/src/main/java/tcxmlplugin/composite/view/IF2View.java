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
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kscs.util.jaxb.BoundList;

import junit.framework.Test;
import tcxml.core.IdentificationMethod;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.IfModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepRunner;

public class IF2View extends StepView  implements StepContainer, ExpandListener{
	
	private TextInputView conditionTxt;
	
	private List<StepViewer> stepViwerChildren ;
	
	private String conditionString;
	
	private ExpandBar bar;
	
	//private IfModel ifmodel ;
	
	private JsonObject arg;
	private IdentificationView identview ;

	private TestObject theTestObject;

	public IF2View(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);
		// color for the viewer
		color=SWT.COLOR_DARK_MAGENTA ;
		
		//ifmodel = new IfModel();
		
		setLayout(new GridLayout(1, false));
		
		Group grpObject = new Group(this, SWT.NONE);
		grpObject.setLayout(new FillLayout(SWT.HORIZONTAL));
		identview = new IdentificationView(grpObject, SWT.NONE, controller);
		
		GridData gd_grpObject = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpObject.heightHint = 214;
		grpObject.setLayoutData(gd_grpObject);
		grpObject.setText("Object");
		
		Group grpArguments = new Group(this, SWT.NONE);
		grpArguments.setLayout(new GridLayout(2, false));
		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpArguments.setText("Arguments");
		
		Label lblCondition = new Label(grpArguments, SWT.NONE);
		lblCondition.setText("Exists");
		
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
		BoundList<Step> li = mo.getStep();
		//add object to test	
		 Step tostep = li.get(0);
		String referencedob = tostep.getTestObject() ;
	theTestObject = controller.getTestObjectById(referencedob, getLibrary());
	identview.populate(theTestObject);	
		super.populate(mo);

conditionTxt.SetArgModel(argumentMap.get("Exists"));
	
		// add children
		
		

	
	
		
		
		//add children
		Step firstchild = li.get(1);				
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
		return "if("  + theTestObject.getAutoName()  + " exists =" +argumentMap.get("Exists").getValue() ;
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		String exist = argumentMap.get("Exists").getValue();
		PlayingContext ret = null ;
		if(exist== "true") {
			
		ret =	runifexist(ctx);
		}else {
			
		ret=	runifnotexist(ctx);
		}
		
	return ret;	
	}

	private PlayingContext runifnotexist(PlayingContext ctx) throws TcXmlException {
		throw new TcXmlException(" if exist with exixts = false is not yet implemented", new IllegalArgumentException("runifnotexist"));

	}

	private PlayingContext runifexist(PlayingContext ctx) throws TcXmlException { 
		BoundList<Step> li = model.getStep();
		//add object to test	
		 Step theTestobjectref = li.get(0);
		 String referencedob = theTestobjectref.getTestObject() ;
		 TestObject theTestObject = controller.getTestObjectById(referencedob, getLibrary());
		 
		 
		String timeout =  model.getObjectTimeout();
		long to=0L;
		
		if(timeout == "" || timeout == null) {
			
			
		}else {
			
		to = Long.parseLong(timeout)	;
		}
		 
		 
	String identmethodstr = theTestObject.getIdents().getActive();
		
		IdentificationMethod identMetho = IdentificationMethod.get(identmethodstr);
		
		switch(identMetho) {
		
		case JAVASCRIPT: 
			throw new TcXmlException("if exist with identification by javascript is not implemented", new IllegalArgumentException(identmethodstr));

		
		case XPATH : 
		
		String xpath = controller.getXpathForTestObject(theTestObject);
		long TIMEOUTWAIT = to;
		try {
	WebDriverWait w = new WebDriverWait(controller.getDriver(), TIMEOUTWAIT );
		By locator = By.xpath(xpath);
		List<WebElement> lifound = w.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0)   );
		//tcXmlController.highLightXpath(xpath);
		
		runChildSteps(ctx);
		}catch (TimeoutException e) {
			
			controller.getLog().info("timeout of " + TIMEOUTWAIT + " is reached in if exist step ");		
		
		}
		

			
			break;
		
		
		}	
		
		 
		 
		 
		 
		 
	return ctx;	 
		
		
	}
	private void runChildSteps(PlayingContext ctx) throws TcXmlException {
		MultipleStepRunner mc = new MultipleStepRunner(stepViwerChildren);
		mc.runSteps(ctx);
		
	}
	

	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer();
		pw.println(" // " + getTitle());
		sb.append(buildIfString()) ;
		List<StepViewer> list = getChildViewer() ;
		for (StepViewer stepViewer : list) {
			stepViewer.export(pw);
		}		
		 sb = new StringBuffer("}//fin if ");	
		pw.println(sb);
		
	}
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}
	
	
	

}
