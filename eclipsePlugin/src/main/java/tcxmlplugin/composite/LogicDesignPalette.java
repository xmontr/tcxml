package tcxmlplugin.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import tcxmlplugin.dnd.DesignPaletteDragListener;
import tcxmlplugin.dnd.DragAbleWrapper;

public class LogicDesignPalette extends Composite {
	

	private Transfer[] types;
;
	private DragSource dragSourcerunblock;
	private Object dragSourcerunaction;


	public LogicDesignPalette(Composite parent, int style) {
		super(parent, style);
		
		types = new Transfer[] {TextTransfer.getInstance()};
		
		
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpPalette = new Group(this, SWT.NONE);
		grpPalette.setText("Palette");
		grpPalette.setLayout(new FillLayout(SWT.VERTICAL));
		
		ExpandBar expandBar = new ExpandBar(grpPalette, SWT.NONE);
		
		ExpandItem runlogicExpanditem = new ExpandItem(expandBar, SWT.NONE);
		runlogicExpanditem.setExpanded(true);
		runlogicExpanditem.setText("Run Logic");
		
		Composite runlogicContainer = new Composite(expandBar, SWT.NONE);
		runlogicExpanditem.setControl(runlogicContainer);

		runlogicContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblrunblock = new Label(runlogicContainer, SWT.NONE);
		lblrunblock.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblrunblock.setText("run block");
		dragSourcerunblock =DesignPaletteDragListener.buildDragsource(lblrunblock, DragAbleWrapper.RUNBLOCK) ;
		
		Label lblrunaction = new Label(runlogicContainer, SWT.NONE);
		lblrunaction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblrunaction.setText("run action");
		dragSourcerunaction =DesignPaletteDragListener.buildDragsource(lblrunaction, DragAbleWrapper.RUNACTION) ;
		
		runlogicExpanditem.setHeight(runlogicExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	
	
	}

}
