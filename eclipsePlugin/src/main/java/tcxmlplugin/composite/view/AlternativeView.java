package tcxmlplugin.composite.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.AlternativeStepWrapper;
import stepWrapper.WaitWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.AlternativeModel;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.dnd.ExpandBarDragListener;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;

import com.kscs.util.jaxb.BoundList;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class AlternativeView extends StepView implements StepContainer , ExpandListener {
	private DataBindingContext m_bindingContext;

	private Step alternativeStep;
	
	private ExpandBar expandBar;
	
	private List<StepViewer> stepViwerChildren ;
	private Text textActiveStep;
	
	private AlternativeModel alternativemodel ;


	public AlternativeView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblActiveStep = new Label(this, SWT.NONE);
		lblActiveStep.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblActiveStep.setText("Active Step ");
		
		textActiveStep = new Text(this, SWT.BORDER);
		textActiveStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		expandBar = new ExpandBar(this, SWT.NONE);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		stepViwerChildren = new ArrayList<StepViewer>();

		alternativemodel = new AlternativeModel();
		m_bindingContext = initDataBindings();
	}

	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
		if(! (stepWrapper2 instanceof AlternativeStepWrapper )) {
			throw new TcXmlException("Alternative view can only be populated by from a alternative step wrapper", new IllegalArgumentException());
			
		}
		
		AlternativeStepWrapper alternativestepwrapper = (AlternativeStepWrapper )stepWrapper2 ;
		
		alternativeStep = alternativestepwrapper.getModel();
		textActiveStep.setText(alternativestepwrapper.getActiveStep());
		BoundList<Step> allsteps = alternativestepwrapper.getAllSteps();
		for (Step step : allsteps) {
			addStep(step);
			
		}

		
		
	}
	
	
	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		expandBar.redraw();
		expandBar.layout(true,true);
		controller.getLog().info("***************      block ********colapsed");
	
		
	}
	
	
	
	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;		
		StepViewer sv = (StepViewer)ex.getControl();
		sv.refreshSizeExpanditem(sv);
		controller.getLog().info("***************     block **********expanded");
		
		expandBar.layout();
		
	}
	



	@Override
	protected PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		saveModel(); 
		PlayingContext ret = getAlternativeView().play(ctx);
		return ret;
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		getAlternativeView().export(pw);
		
	}

	@Override
	public void saveModel() throws TcXmlException {
		super.saveModel();
	((AlternativeStepWrapper )stepWrapper).setActiveStep(alternativemodel.getActiveStep());
		
		// save all alternative steps
	for (StepViewer stepViewer : stepViwerChildren) {
		stepViewer.getViewer().saveModel();
		
	}
		
	}
	
	
	public StepViewer getAlternativeView()  throws TcXmlException {
		
	String stepindex = ((AlternativeStepWrapper )stepWrapper).getActiveStep();
	int index = Integer.parseInt(stepindex);
	
	if (index < 0 || index >= stepViwerChildren.size()) {
		
		throw new TcXmlException("incorrect acitve step for alternative view ",  new IllegalArgumentException(stepindex)) ;
	}
	
	StepViewer theview = stepViwerChildren.get(index);
return theview ;
		
		
	}
	
	

	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return expandBar ;
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStep(Step step) throws TcXmlException {
		
		if(alternativeStep == null) {
			throw new TcXmlException("no parent step for stepContainer", new IllegalStateException());
			
		}
	if(	! alternativeStep.getStep().contains(step) ){
		alternativeStep.getStep().add(step);	
			
		}
		
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller,getLibrary());
		 
		 tv.setDragSource(ExpandBarDragListener.buildDragsource(tv));
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(tv);
		 
		 expandBar.layout();
		
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
	public void addStep(Step step, int index) throws TcXmlException {
		
		if(alternativeStep == null) {
			throw new TcXmlException("no parent step for stepContainer", new IllegalStateException());
			
		}
	if(	! alternativeStep.getStep().contains(step) ){
		alternativeStep.getStep().add(index,step);	
			
		}
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller,getLibrary());
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE, index);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		
		 stepViwerChildren.add(index,tv);
		 
		 expandBar.layout();
		
	}

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}

	@Override
	public void reIndex() {
		// TODO Auto-generated method stub
		
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextActiveStepObserveWidget = WidgetProperties.text(SWT.Modify).observe(textActiveStep);
		IObservableValue activeStepAlternativemodelObserveValue = BeanProperties.value("activeStep").observe(alternativemodel);
		bindingContext.bindValue(observeTextTextActiveStepObserveWidget, activeStepAlternativemodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
