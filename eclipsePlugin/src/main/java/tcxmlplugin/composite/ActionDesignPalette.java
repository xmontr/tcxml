package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import tcxmlplugin.dnd.DragAbleWrapper;
import tcxmlplugin.dnd.DesignPaletteDragListener;
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
import org.eclipse.wb.swt.SWTResourceManager;

public class ActionDesignPalette extends Composite {
	

	private Transfer[] types;
	private DragSource dragSourcewait;
	private DragSource dragSourcewaitForObject;
	private DragSource dragSourceCallfFunction;
	private DragSource dragSourceObjectAction;
	private Object dragSourceBrowserAction;
	private DragSource dragSourceGenApi;
	private DragSource dragSourceComment;

	public ActionDesignPalette(Composite parent, int style) {
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

		functionContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblobject = new Label(functionContainer, SWT.NONE);
		lblobject.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblobject.setText("object action");
		dragSourceObjectAction =DesignPaletteDragListener.buildDragsource(lblobject, DragAbleWrapper.OBJECTACTION) ;
		
		Label lblbrowser = new Label(functionContainer, SWT.NONE);
		lblbrowser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblbrowser.setText("browser action");
		dragSourceBrowserAction =DesignPaletteDragListener.buildDragsource(lblbrowser, DragAbleWrapper.BROWSERACTION) ;
		
		Label lblWait = new Label(functionContainer, SWT.NONE);
		lblWait.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblWait.setText("wait");
		
		dragSourcewait =DesignPaletteDragListener.buildDragsource(lblWait, DragAbleWrapper.WAIT) ;
		
		
		
		Label lblWaitForObj = new Label(functionContainer, SWT.NONE);
		lblWaitForObj.setText("wait for object");
		dragSourcewaitForObject =DesignPaletteDragListener.buildDragsource(lblWaitForObj, DragAbleWrapper.WAITFOROBJECT) ;
		
		Label lblcallfunct = new Label(functionContainer, SWT.NONE);
		lblcallfunct.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblcallfunct.setText("call function");
		dragSourceCallfFunction =DesignPaletteDragListener.buildDragsource(lblcallfunct, DragAbleWrapper.CALLFUNCTION) ;
		
		Label lblGenApi = new Label(functionContainer, SWT.NONE);
		lblGenApi.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblGenApi.setText("API");
		dragSourceGenApi =DesignPaletteDragListener.buildDragsource(lblGenApi, DragAbleWrapper.CALLGENERICAPI) ;
		
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		ExpandItem xpndtmMiscellanous = new ExpandItem(expandBar, SWT.NONE);
		xpndtmMiscellanous.setExpanded(true);
		xpndtmMiscellanous.setText("miscellanous");
		Composite miscContainer = new Composite(expandBar, SWT.NONE);
		xpndtmMiscellanous.setControl(miscContainer);
		miscContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblComment = new Label(miscContainer, SWT.NONE);
		lblComment.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblComment.setText("comment");
		dragSourceComment =DesignPaletteDragListener.buildDragsource(lblComment, DragAbleWrapper.COMMENT) ;
		
		xpndtmMiscellanous.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		ExpandItem xpndtmFlowControl = new ExpandItem(expandBar, SWT.NONE);
		xpndtmFlowControl.setExpanded(true);
		xpndtmFlowControl.setText("Flow Control");
		
		Composite flowContainer = new Composite(expandBar, SWT.NONE);
		xpndtmFlowControl.setControl(flowContainer);
		
	
		
		flowContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblFor = new Label(flowContainer, SWT.NONE);
		lblFor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFor.setText("For");
		DragSource dragSourcefor = DesignPaletteDragListener.buildDragsource(lblFor, DragAbleWrapper.FOR) ;
		
		Label lblIf = new Label(flowContainer, SWT.NONE);
		lblIf.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblIf.setText("If");
		DragSource dragSourcefIF = DesignPaletteDragListener.buildDragsource(lblIf, DragAbleWrapper.IF) ;
		
		Label lblifexist = new Label(flowContainer, SWT.NONE);
		lblifexist.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblifexist.setText("if Exist");
		DragSource dragSourcefIFExist = DesignPaletteDragListener.buildDragsource(lblifexist, DragAbleWrapper.IFEXIST) ;
		
		xpndtmFlowControl.setHeight(xpndtmFlowControl.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		
		
	
		
		
	
	}






}
