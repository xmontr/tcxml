package tcxmlplugin.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.internal.ide.dialogs.CreateLinkedResourceGroup;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.composite.stepViewer.TitleListener;

public abstract class AStepContainer extends Composite   implements StepContainer, ExpandListener {
	
	protected TcXmlController controller;
	
	
	protected List<StepViewer> stepViwerChildren ;
	
	protected ExpandBar bar;


	private Composite content;


	protected ScrolledComposite scroller;
	
	
	
	public void buildGUI(Composite parent,int style) {
		GridLayout gridlayout = new GridLayout(1, false);
	this.setLayout(gridlayout);
	scroller = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
	scroller.setExpandVertical(true);
	scroller.setExpandHorizontal(true);
	scroller.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	scroller.setAlwaysShowScrollBars(true);
	content = new Composite(scroller, SWT.NONE);	
	//content.setBackground( getDisplay().getSystemColor( SWT.COLOR_RED) );	
	content.setLayout(new GridLayout(1, false));
	bar = new ExpandBar(content, SWT.NONE);
	bar.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE) );
	bar.setSpacing(10);
	bar.addExpandListener(this);
	bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	scroller.setContent(content);
	content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	content.layout();
	
	
	
		
	scroller.addListener( SWT.Resize, event -> {
		  int width = scroller.getClientArea().width;
		  int height = scroller.getClientArea().height;
		  scroller.setMinWidth(width);
		  scroller.setMinHeight(height);		 
		 		 scroller.layout();
		} );
	
	scroller.setBackground( getDisplay().getSystemColor( SWT.COLOR_DARK_BLUE) );
	layout(true,true);

	
		
	}
	
	
	

	
	
	protected void resizeContent() {
		 int width = scroller.getClientArea().width;
		 getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
				Point newsize = content.computeSize(SWT.DEFAULT,SWT.DEFAULT);
			scroller.setMinSize(newsize);	
content.setSize(newsize);
	content.layout(true,true);	
	scroller.layout(true, true);
	
			}
		});
		 


		
		
	}
	
	
	public int getBarHeight() {
		ExpandItem[] li = bar.getItems();	
		int w = 0;
		for (ExpandItem expandItem : li) {
			w+= expandItem.getHeight() ;	
			
		}
		
		
		controller.getLog().info(" hauteur bar: " +w );
		
		return w;
		
		
		
	}
	
	
	public AStepContainer(Composite parent, int style) {
		super(parent, style);
	buildGUI(parent,style);
		
	}
	

	public AStepContainer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller= controller ;
	buildGUI(parent,style);
	
	
	
	stepViwerChildren = new ArrayList<StepViewer>();
	}
	
	public void addStep(Step step) throws TcXmlException {
		
		 StepViewer tv = StepViewerFactory.getViewer(step,this, controller);		 
		 stepViwerChildren.add(tv);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);		
		

		xpndtmNewExpanditem.setExpanded(false);
		
		xpndtmNewExpanditem.setText(tv.getTitle());
		
		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y  );
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);
		tv.addPropertyChangeListener("title", new TitleListener(xpndtmNewExpanditem , tv));

		
		
		
		
		
	}
	
	
	@Override
	public void itemCollapsed(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
sv.refreshSizeExpanditem();
		
controller.getLog().info("**********     ASTEPCONTAINER " + this.getClass()  +"***************colapsed");
	
		
	}
	
	
	
	@Override
	public void itemExpanded(ExpandEvent e) {
		ExpandItem ex = (ExpandItem)e.item;
		StepViewer sv = (StepViewer)ex.getControl();
sv.refreshSizeExpanditem();
	
controller.getLog().info("**********    ASTEPCONTAINER " + this.getClass()  +"***************expanded");

		
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
	
	
	
	protected Point getControlLocation(
			Control control) {
		int x = 0;
		int y = 0;
		Control content = this;
		Control currentControl = control;
		for (;;) {
			if (currentControl == content)
				break;
			Point location = currentControl.getLocation();
			x += location.x;
			y += location.y;
			currentControl = currentControl.getParent();
		}
		return new Point(x, y);
	}
	
	
	
}