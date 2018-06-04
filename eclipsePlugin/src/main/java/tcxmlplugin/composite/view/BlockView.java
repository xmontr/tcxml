package tcxmlplugin.composite.view;

import java.util.ArrayList;
import java.util.List;

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

import com.kscs.util.jaxb.BoundList;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;

public class BlockView  extends StepView implements StepContainer, ExpandListener {
	private ExpandBar bar;
	
	private List<StepViewer> stepViwerChildren ;

	public BlockView(Composite parent, int style, TcXmlController controller) {
		
		
		super(parent, style,controller);
		
		GridLayout gridlayout = new GridLayout(1, false);
		setLayout(gridlayout);
		stepViwerChildren = new ArrayList<StepViewer>();
		
		bar = new ExpandBar(this, SWT.V_SCROLL);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_GRAY) );
		bar.setSpacing(10);
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void addStep(Step step) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller);
		 
		 if(tv.getViewer() instanceof StepContainer) {
			 
			 StepContainer childcont = (StepContainer)tv.getViewer();
			 childcont.getBar().addExpandListener(this);
			 
		 }

	
			

	
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y );
		xpndtmNewExpanditem.setControl(tv);
		
		
		 stepViwerChildren.add(tv);
		 
		 bar.layout();
		
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
				
		bar.layout();
		
		
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


	@Override
	public String buildTitle(Step mo) {
		String ret = formatTitle(model.getIndex(), "Group " +  model.getAction());
		return ret;
	}


	@Override
	public void itemCollapsed(ExpandEvent e) {
		bar.layout();
		
		TcXmlPluginController.getInstance().info("**************************colapsed");
	
		
	}
	
	
	
	@Override
	public void itemExpanded(ExpandEvent e) {
		TcXmlPluginController.getInstance().info("**************************expanded");
		bar.layout();
		
	}




}
