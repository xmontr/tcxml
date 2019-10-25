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

public class IF2View extends StepView  implements StepContainer, ExpandListener{
	
	private TextInputView conditionTxt;
	
	private List<StepViewer> stepViwerChildren ;
	
	private String conditionString;
	
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
		
		conditionTxt = new TextInputView(grpArguments, SWT.NONE);
		
		Group grpSteps = new Group(this, SWT.NONE);
		grpSteps.setLayout(new FillLayout(SWT.HORIZONTAL));
	
		grpSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSteps.setText("Steps");
		
		ifbar = new ExpandBar(grpSteps, SWT.NONE);
		stepViwerChildren = new ArrayList<StepViewer>();
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
		BoundList<Step> li = model.getStep();
		//add object to test	
		 Step tostep = li.get(0);
		String referencedob = tostep.getTestObject() ;
	theTestObject = controller.getTestObjectById(referencedob, getLibrary());
	identview = new IdentificationView(grpObject, SWT.NONE, controller);
	identview.populate(theTestObject);	
	

conditionTxt.SetArgModel(argumentMap.get("Exists"));
	
		// add children		
		
		//add children
		Step firstchild = li.get(1);				
				sanitycheck(firstchild);
		
		
		for (Step step : firstchild.getStep()) {
			ifcontainer.addStep(step);
		}
				
		ifbar.layout();
		
		//add else step
		if(li.size() > 2) { 	//add else children
			
			Step secondchild = li.get(2);
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
		return "if("  + theTestObject.getAutoName()  + " exists =" +argumentMap.get("Exists").getValue() ;
	}
	
	
	
	
	private String exportIfString() throws TcXmlException {
		StringBuffer ret = new StringBuffer();
		
		//get testobject
		BoundList<Step> li = model.getStep();
		//add object to test	
		 Step theTestobjectref = li.get(0);
		 String referencedob = theTestobjectref.getTestObject() ;
		 TestObject theTestObject = controller.getTestObjectById(referencedob, getLibrary());
		 
		 
	
		
		
	//HashMap<String, ArgModel> amap = controller.getArguments(model);
	 ArgModel[] lia = argumentMap.values().toArray(new  ArgModel[argumentMap.size()]);
		String argjs = controller.generateJSobject(lia);
	
	
	
	
		
		String func = "await TC.exist";
		String txt = TcxmlUtils.formatJavascriptFunction(
					func,
					argjs,
					controller.generateJsTestObject(theTestObject) 
					);
		
		String txt2 = txt.substring(0,txt.length() -2 ); // remove semicolumn 
		
		ret.append("if(" ).append(txt2).append(" ) {\n");
		
		return ret.toString();
	}

	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		
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
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(ifcontainer.getChildViewer());
		mc.runSteps(ctx);
		
	}
	

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer();
		pw.println(" // " + getTitle());
		sb.append( exportIfString() ) ;
		pw.println(sb);
		List<StepViewer> list = ifcontainer.getChildViewer() ;
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
	
	
	

}
