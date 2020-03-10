package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import tcxmlplugin.dnd.DragAbleWrapper;
import tcxmlplugin.dnd.MyDragListener;
import tcxmlplugin.dnd.MyDropListener;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetListener;

public class DesignPalette extends Composite {
	

	private Transfer[] types;
	private DragSource dragSourcewait;
	private DragSource dragSourcewaitForObject;

	public DesignPalette(Composite parent, int style) {
		super(parent, style);
		
		types = new Transfer[] {TextTransfer.getInstance()};
		
		
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpPalette = new Group(this, SWT.NONE);
		grpPalette.setText("Palette");
		grpPalette.setLayout(new FillLayout(SWT.VERTICAL));
		
		ExpandBar expandBar = new ExpandBar(grpPalette, SWT.NONE);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setExpanded(true);
		xpndtmNewExpanditem.setText("function");
		
		Composite functionContainer = new Composite(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setControl(functionContainer);
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		functionContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblwait = new Label(functionContainer, SWT.NONE);
		lblwait.setText("generic object action");
		
		Label lblWait = new Label(functionContainer, SWT.NONE);
		lblWait.setText("wait");
		
		dragSourcewait =MyDragListener.buildDragsource(lblWait, DragAbleWrapper.WAIT) ;
		
		
		
		Label lblWaitForObj = new Label(functionContainer, SWT.NONE);
		lblWaitForObj.setText("wait for object");
		dragSourcewaitForObject =MyDragListener.buildDragsource(lblWaitForObj, DragAbleWrapper.WAITFOROBJECT) ;
		
		ExpandItem xpndtmMiscellanous = new ExpandItem(expandBar, SWT.NONE);
		xpndtmMiscellanous.setExpanded(true);
		xpndtmMiscellanous.setText("miscellanous");
		
		Composite miscContainer = new Composite(expandBar, SWT.NONE);
		xpndtmMiscellanous.setControl(miscContainer);
	
		
		xpndtmMiscellanous.setHeight(xpndtmMiscellanous.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		miscContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		ExpandItem xpndtmFlowControl = new ExpandItem(expandBar, SWT.NONE);
		xpndtmFlowControl.setExpanded(true);
		xpndtmFlowControl.setText("Flow Control");
		
		Composite flowContainer = new Composite(expandBar, SWT.NONE);
		xpndtmFlowControl.setControl(flowContainer);
		
	
		xpndtmFlowControl.setHeight(xpndtmFlowControl.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		flowContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		
		
	
		
		
	
	}






}
