package tcxmlplugin.composite.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;

public class FunctionView extends  StepView implements StepContainer {
	private ExpandBar bar;
	
	

	public FunctionView(Composite parent, int style, TcXmlController controller) {
		
		
		super(parent, style,controller);
		setLayout(new FillLayout());
		
		bar = new ExpandBar(this, SWT.BORDER & SWT.V_SCROLL);
		bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_GRAY) );
		bar.setSpacing(10);
		
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void addStep(Step step) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller);

	
			

	
		
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
	
	public void populate(Step mo) throws TcXmlException {
		
		setTitle("Function " + mo.getAction());
		//first step child is internal - skipit
		sanityCheck(mo);
		super.populate(mo);
		BoundList<Step> li = mo.getStep().get(0).getStep();
		for (Step step : li) {
			addStep(step);
		}
				
		
	}


	private void sanityCheck(Step mo) throws TcXmlException {
	Step firstchild = mo.getStep().get(0);
	if( ! (firstchild.getType().equals("block") && firstchild.getAction().equals("internal") ) ) {
		
		throw  new TcXmlException("bad structure for function step - expected block internal not found", new IllegalStateException());
		
	}
		
	}

}
