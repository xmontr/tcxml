package tcxmlplugin.dnd;



import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;

public class MyExpandBarDropUtil  {
	
	
	private static  Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	
	
	private ExpandBar thebar;


	public MyExpandBarDropUtil(ExpandBar thebar) {
		super();
		this.thebar = thebar;
	}
	
	
	
	public  DropTarget buildDropTarget() {
		
		DropTarget ret = new DropTarget(thebar, DND.DROP_MOVE | DND.DROP_COPY); 
		ret.setTransfer(types);
		
	ExpandBarDropTargetEffect effect = new ExpandBarDropTargetEffect(thebar) ;
		
		ret.setDropTargetEffect(effect );
		ret.addDropListener(effect);
		return ret ;
		
	}
	
	
	








	
	
	
	
	
	
	

}
