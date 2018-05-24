package tcxmlplugin.composite.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;

public class BlockView  extends StepView implements StepContainer {
	private ExpandBar bar;
	
	private List<StepViewer> stepViwerChildren ;

	public BlockView(Composite parent, int style, TcXmlController controller) {
		
		
		super(parent, style,controller);
		setLayout(new FillLayout());
		stepViwerChildren = new ArrayList<StepViewer>();
		bar = new ExpandBar(this, SWT.BORDER);
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
		
		 stepViwerChildren.add(tv);
		
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
		super.populate(mo);
		BoundList<Step> li = mo.getStep();
		for (Step step : li) {
			addStep(step);
		}
				
	
		setTitle(formatTitle(model.getIndex(), "Group " +  model.getAction()));
		
	}





	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		throw new TcXmlException("not implemented", new IllegalAccessException());
		
	}


	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}




}
