package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;

import org.eclipse.swt.layout.FillLayout;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

public class RunLogicViewer extends Composite implements StepContainer{
	
	
	private TcXmlController controller;
	private ExpandBar bar;

	public RunLogicViewer(Composite parent, int style) {
		super(parent, style);

		
	}
	
	
	public RunLogicViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		bar = new ExpandBar(this, SWT.NONE);
		this.controller=controller;
		
		
	}


	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
	}


	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addStep(Step step) throws TcXmlException {
		StepViewer tv = StepViewerFactory.getViewer(step, this, controller);

		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());

		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);

		
		
	}


	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	public void populate(Step runlogic) throws TcXmlException {
		
		sanityCheck(runlogic);
		BoundList<Step> li = runlogic.getStep();
		for (Step step : li) {
			addStep(step);
		}
		
		
		
		
	}
	
	
	public void sanityCheck (Step runlogic) throws TcXmlException {
		
		if ( !runlogic.getType().equals("runLogic")) {
			
			throw new TcXmlException("invalide runlogic step", new IllegalArgumentException());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
