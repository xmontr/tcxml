package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import stepWrapper.AbstractStepWrapper;
import stepWrapper.ForWrapper;
import stepWrapper.If2Wrapper;
import tcxml.core.IdentificationMethod;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.IfModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.MultipleStepViewerRunner;
import util.TcxmlUtils;
import org.eclipse.wb.swt.SWTResourceManager;

public class IF2View extends StepView  implements ExpandListener{
	
	private ListInputView conditionTxt;
	

	
	private ExpandBar ifbar;
	
	//private IfModel ifmodel ;
	
	private JsonObject arg;
	private IdentificationView identview ;
	

	private TestObject theTestObject;
	
	
	
	private IfElsecontainer ifcontainer ;
	
	private IfElsecontainer elsecontainer ;

	private Group grpElse;

	private ExpandBar elsebar;

	private Group grpObject;
	
	
	

	public IF2View(Composite parent, int style)  {
		super(parent, style);
		// color for the viewer
		color=SWT.COLOR_DARK_MAGENTA ;
		
		//ifmodel = new IfModel();
		
		setLayout(new GridLayout(1, false));
		
		grpObject = new Group(this, SWT.NONE);
		grpObject.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		grpObject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpObject.setText("Object");
		
		Group grpArguments = new Group(this, SWT.NONE);
		grpArguments.setLayout(new GridLayout(2, false));
		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpArguments.setText("Arguments");
		
		Label lblCondition = new Label(grpArguments, SWT.NONE);
		lblCondition.setText("Exists");
		
		conditionTxt = new ListInputView(grpArguments, SWT.NONE);
		
		Group grpSteps = new Group(this, SWT.NONE);
		grpSteps.setLayout(new FillLayout(SWT.HORIZONTAL));
	
		grpSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSteps.setText("Steps");
		
		ifbar = new ExpandBar(grpSteps, SWT.NONE);
		
		ifbar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
		ifbar.setSpacing(10);
		
		ifbar.addExpandListener(this);
		
		grpElse = new Group(this, SWT.NONE);
		grpElse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElse.setText("else");
		grpElse.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		elsebar = new ExpandBar(grpElse, SWT.NONE);
		elsebar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		ifcontainer = new IfElsecontainer(this , ifbar);
		ifbar.addExpandListener(ifcontainer);
		
		elsecontainer = new IfElsecontainer(this,elsebar);
		elsebar.addExpandListener(elsecontainer);
		elsebar.setSpacing(10);
		
		
		
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
	public void populate(AbstractStepWrapper stepWrapper2 ) throws TcXmlException {
		
		if(! (stepWrapper2 instanceof If2Wrapper )) {
			throw new TcXmlException("if2 view can only be populated by from a if2 wrapper ", new IllegalArgumentException());
			
		}
		
		If2Wrapper if2wrapper = (If2Wrapper)stepWrapper;
		
	
		Step model = if2wrapper.getModel();
		
		
		BoundList<Step> li = model.getStep();

		
	
	identview = new IdentificationView(grpObject, SWT.NONE, controller);
	identview.populate(if2wrapper.getTestobject());	
	

conditionTxt.setArgmodel(if2wrapper.getExistCondition());
	
		// add children		
		
		//add children
Step ifparent = if2wrapper.getIfParentStep() ;

	ifcontainer.setParentStep(ifparent);				
				sanitycheck(ifparent);
		
		
		for (Step step : ifparent.getStep()) {
			ifcontainer.addStep(step);
		}
				
		ifbar.layout();
		
		//add else step
		if(if2wrapper.hasElse()) { 	//add else children
			
			Step elseparent = if2wrapper.getElseParentStep();
			elsecontainer.setParentStep(elseparent);
			for (Step step : elseparent.getStep()) {
			elsecontainer.addStep(step);
			}
			
			elsebar.layout();	
			grpElse.setVisible(true);
			
		}else { // not else present
			
			grpElse.setVisible(false);	
			
		}
		
		
	
	}
	
	@Override
	public void saveModel() throws TcXmlException {
		super.saveModel();
		/*
		 * If2Wrapper wr = (If2Wrapper) stepWrapper;
		 * 
		 * wr.saveArguments();
		 */
	identview.saveModel();	
	ifcontainer.saveModel();
	elsecontainer.saveModel();
		
	}
	
	



	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		saveModel();
		HashMap<String, ArgModel> argumentMap = stepWrapper.getArgumentMap();
		
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
		Step model = stepWrapper.getModel();
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
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(ifcontainer.getChildViewer());
		mc.runSteps(ctx);
		
	}
	

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		stepWrapper.export(pw);

		
	}
	
	
	private void sanitycheck(Step step) throws TcXmlException {
	if(!	step.getAction().equals("internal") ) {
		
		throw new TcXmlException("not expected element  in For step. internal expected but found  " + step.getAction() + " id of step is "+ step.getStepId(), new IllegalStateException());
	}
		
	}

	/*
	 * @Override public void reIndex() { for (int i = 0; i <
	 * stepViwerChildren.size(); i++) {
	 * 
	 * stepViwerChildren.get(i).getViewer().getStepWrapper().getStep().setIndex(new
	 * Integer(i).toString() );
	 * 
	 * 
	 * }
	 * 
	 * }
	 */
	
	
	

}
