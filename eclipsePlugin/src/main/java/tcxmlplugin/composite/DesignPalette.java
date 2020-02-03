package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

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

public class DesignPalette extends Composite implements DragSourceListener{
	

	private Transfer[] types;
	private DragSource dragSourcewait;

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
		
		dragSourcewait = new DragSource(lblWait, DND.DROP_MOVE | DND.DROP_COPY);	
		dragSourcewait.setTransfer(types);
		dragSourcewait.addDragListener(this);
		
		
		
		Label lblWaitForObj = new Label(functionContainer, SWT.NONE);
		lblWaitForObj.setText("wait for object");
		
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
		
		
		
		Label lblDroptesttarget = new Label(flowContainer, SWT.NONE);
		lblDroptesttarget.setText("droptesttarget");
		DropTarget dropTarget = new DropTarget(flowContainer, DND.DROP_MOVE | DND.DROP_COPY);
		
		DropTargetListener mydroplistener = new MyDropListener();	
		dropTarget.setTransfer(types);
		
		Label lblSecondLabel = new Label(flowContainer, SWT.NONE);
		lblSecondLabel.setText("second label");
		dropTarget.addDropListener(mydroplistener );
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		event.doit=true;
		event.detail=DND.DROP_MOVE ;
		
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
	     if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
	    	 	if(event.getSource().equals(dragSourcewait)){
	    	 		event.data = "WAIT";
	    	 		
	    	 	}
	    	 
	    	  	          
	    	  	     }
		
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		// TODO Auto-generated method stub
		
	}
}
