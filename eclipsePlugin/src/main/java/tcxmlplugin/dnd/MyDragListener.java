package tcxmlplugin.dnd;



import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;

public class MyDragListener implements DragSourceListener{
	
	
	private static  Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	
	
	
	
	public static DragSource buildDragsource(Control ctrl , DragAbleWrapper type) {
		
		DragSource ret = new DragSource(ctrl , DND.DROP_MOVE | DND.DROP_COPY);	
		ret.setTransfer(types);		
		ret.addDragListener( new MyDragListener(type));		
	return ret;	
	}
	
	
	
	
	private DragAbleWrapper dragable;
	
	

	public MyDragListener(DragAbleWrapper dragable) {
		super();
		this.dragable = dragable;
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
		switch (dragable) {		
		case WAIT:event.data = "WAIT";break;
		case CALLFUNCTION:event.data = "CALLFUNCTION";break;
		case CALLGENERICAPI :event.data = "GENAPI";break;
		case WAITFOROBJECT : event.data="WAITFOROBJECT";break;
		default:event.data = "NODATA";
			break;
		}
		
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	

}