package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.model.Step;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public class StepContainer extends Composite {

	private ExpandBar bar;

	public StepContainer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		bar = new ExpandBar(this, style);
		

	}

	public void addStep(Step step2) {
	
			

	
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);
		DefaultStepViewer stepviewer = new DefaultStepViewer(bar, SWT.NONE);
		stepviewer.populate(step2);
		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(stepviewer.getTitle());
		
		xpndtmNewExpanditem.setHeight(stepviewer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(stepviewer);
		
		
		
	}

	public void clean() {
	ExpandItem[] li = bar.getItems();
	for (ExpandItem expandItem : li) {
		expandItem.dispose();
	}
		
	}

}
