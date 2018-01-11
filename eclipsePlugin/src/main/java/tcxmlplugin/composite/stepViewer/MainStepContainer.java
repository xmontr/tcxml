package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.model.Step;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public class MainStepContainer extends Composite implements StepContainer {

	private ExpandBar bar;

	public MainStepContainer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		bar = new ExpandBar(this, style);
		

	}

	public void addStep(Step step) {
		
		 AbstractTestViewer tv = StepViewerFactory.getViewer(step,this);

	
			

	
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		
		
		
	}

	public ExpandBar getBar() {
		return bar;
	}

	public void clean() {
	ExpandItem[] li = bar.getItems();
	for (ExpandItem expandItem : li) {
		expandItem.dispose();
		bar.redraw();
	}
		
	}

}
