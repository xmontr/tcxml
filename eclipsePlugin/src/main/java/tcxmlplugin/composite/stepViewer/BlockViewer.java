package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import tcxml.model.Step;

public class BlockViewer extends AbstractTestViewer implements StepContainer {

	private ExpandBar bar;

	public BlockViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		bar = new ExpandBar(this, style);
	}

	@Override
	public String getTitle() {
		String ret = " Group " + model.getAction();
		return ret;
	}
	
	
	
	
	@Override
	public void populate(Step mo) {
		// TODO Auto-generated method stub
		super.populate(mo);
		BoundList<Step> li = mo.getStep();
		for (Step step : li) {
			addStep(step);
		}
				
		
	}
	
	
	
	public void addStep(Step step) {
		
		 AbstractTestViewer tv = StepViewerFactory.getViewer(step,this);

	
			

	
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		
		
		
	}

	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
	}

}
