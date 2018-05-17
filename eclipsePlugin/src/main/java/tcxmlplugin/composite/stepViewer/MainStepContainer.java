package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public class MainStepContainer extends Composite implements StepContainer {

	private ExpandBar bar;
	private TcXmlController controller;
	
	
	protected List<StepViewer> stepViwerChildren ;

	public MainStepContainer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
	//	FillLayout filllayout = new FillLayout();
		
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
		tv.addPropertyChangeListener("title", new TitleListener(xpndtmNewExpanditem , tv));

		
		
		
		
		
	}

	public ExpandBar getBar() {
		return bar;
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


}
