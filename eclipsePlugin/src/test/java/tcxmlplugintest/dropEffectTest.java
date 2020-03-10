package tcxmlplugintest;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.wb.swt.SWTResourceManager;

import tcxmlplugin.dnd.DragAbleWrapper;
import tcxmlplugin.dnd.MyDragListener;
import tcxmlplugin.dnd.MyExpandBarDropUtil;

public class dropEffectTest extends Composite{

	private DragSource waitdragasource;
	private DragSource genapidragasource;
	private ExpandBar expandBar;


	public dropEffectTest(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Composite paletteParent = new Composite(this, SWT.NONE);
		paletteParent.setLayout(new FillLayout(SWT.VERTICAL));
		paletteParent.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		
		Label waitlbl = new Label(paletteParent, SWT.NONE);
		waitlbl.setText("wait");
		
		waitdragasource = MyDragListener.buildDragsource(waitlbl, DragAbleWrapper.WAIT) ;
		
		
		Label genapilbl = new Label(paletteParent, SWT.NONE);
		genapilbl.setText("generic api");
		
		genapidragasource = MyDragListener.buildDragsource(genapilbl, DragAbleWrapper.CALLGENERICAPI) ;
		
		Composite expandbarParent = new Composite(this, SWT.NONE);
		expandbarParent.setLayout(new FillLayout());
		expandbarParent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		expandBar = new ExpandBar(expandbarParent, SWT.NONE);
		expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		ExpandItem xpndtmExp = new ExpandItem(expandBar, SWT.NONE);
		xpndtmExp.setText("step - 1");
		Composite composite1 = new Composite(expandBar, getStyle());
		composite1.setLayout(new FillLayout());
		
		xpndtmExp.setControl(composite1);
		
		Label label_1 = new Label(composite1, SWT.NONE);
		label_1.setText("New Label");
		
		ExpandItem xpndtmExp_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmExp_1.setText("step - 2");
		Composite composite2 = new Composite(expandBar, getStyle());
		composite2.setLayout(new FillLayout());
		xpndtmExp_1.setControl(composite2);
		
		Label label = new Label(composite2, SWT.NONE);
		label.setText("New Label");
		
		ExpandItem xpndtmExp_2 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmExp_2.setExpanded(true);
		xpndtmExp_2.setText("step - 3");
		Composite composite3 = new Composite(expandBar, getStyle());
		composite3.setLayout(new FillLayout());
		xpndtmExp_2.setControl(composite3);
		
		Label lblNewLabel = new Label(expandBar, SWT.NONE);
		xpndtmExp_2.setControl(lblNewLabel);
		lblNewLabel.setText("New Label");
		xpndtmExp_2.setHeight(xpndtmExp_2.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		Label lblNewLabel_1 = new Label(composite3, SWT.NONE);
		lblNewLabel_1.setText("New Label");

		//DropTarget expandbardroptarget = new MyExpandBarDropUtil(expandBar).buildDropTarget();
		
	}


public static void main(String[] args) {
	
	Display display =Display.getDefault();
	Shell shell = new Shell(display);
	shell.setSize(450, 300);
	shell.setLayout(new FillLayout());

	 dropEffectTest lafenetre = new dropEffectTest(shell, SWT.NONE);
	

	shell.open();
	

	// run the event loop as long as the window is open
	while (!shell.isDisposed()) {
	    // read the next OS event queue and transfer it to a SWT event
	    if (!display.readAndDispatch())
	     {
	    // if there are currently no other OS event to process
	    // sleep until the next OS event is available
	        display.sleep();
	     }
	}

	// disposes all associated windows and their components
	display.dispose();
	
	
}
	
	
	
	
	
}
