package tcxmlplugin.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.composite.stepViewer.TitleListener;

public abstract class AStepContainer extends Composite   implements StepContainer {
	
	protected TcXmlController controller;
	
	
	protected List<StepViewer> stepViwerChildren ;
	
	protected ExpandBar bar;
	
	
	
	

	public AStepContainer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		GridLayout gridlayout = new GridLayout(1, false);
	this.setLayout(gridlayout);	
	this.controller= controller ;
	
	stepViwerChildren = new ArrayList<StepViewer>();
	bar = new ExpandBar(this, SWT.V_SCROLL);
	bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
	bar.setSpacing(10);
	
	}
	
	public void addStep(Step step) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller);
		 stepViwerChildren.add(tv);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);		
		

		xpndtmNewExpanditem.setExpanded(false);
		
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		tv.addPropertyChangeListener("title", new TitleListener(xpndtmNewExpanditem , tv));

		
		
		
		
		
	}
	
	
	public void clean() {
	ExpandItem[] li = bar.getItems();
	for (ExpandItem expandItem : li) {
	Control innercontrol = expandItem.getControl();
	if( innercontrol instanceof StepContainer) {
		
		((StepContainer) innercontrol).clean();
		expandItem.dispose();
	}
	
		
		
	else {
		innercontrol.dispose();
		expandItem.dispose();
	}
		
	}
	bar.redraw();
	stepViwerChildren.clear();
}
	
	
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
	}	
	

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}
	
	
	
	
}
