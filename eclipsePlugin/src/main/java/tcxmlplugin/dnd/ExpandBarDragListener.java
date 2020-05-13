package tcxmlplugin.dnd;

import javax.xml.bind.JAXBContext;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepViewer;

public class ExpandBarDragListener implements DragSourceListener {
	
	private static  Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	private StepViewer theViewer;
	
	
	public ExpandBarDragListener(StepViewer ctrl) {
		this.theViewer = ctrl ; 
	}

	public static DragSource buildDragsource(StepViewer ctrl ) {
		
		DragSource ret = new DragSource( ctrl.getStepToolBar() , DND.DROP_MOVE | DND.DROP_COPY);	
		ret.setTransfer(types);		
		ret.addDragListener( new ExpandBarDragListener(ctrl));		
	return ret;	
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		event.doit=true;
		event.detail=DND.DROP_MOVE ;
		
		
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		event.doit=true;
		event.detail=DND.DROP_MOVE ;
	
		
		try {
			event.data =	theViewer.getViewer().getStepWrapper().marshallStep();
		} catch (TcXmlException e) {
			event.data = "NODATA";
			TcXmlPluginController.getInstance().error("fail to start drag operation", e);
		}


		
	
		
		
		
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		
		
	}

}
