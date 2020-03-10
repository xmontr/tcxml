package tcxmlplugin.dnd;



import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;

import tcxmlplugin.composite.stepViewer.StepContainer;

public class MyExpandBarDropUtil  {
	
	
	private static  Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	
	
	


	private StepContainer thecontainer;


	public MyExpandBarDropUtil(StepContainer container) {
		super();
		this.thecontainer = container;
	}
	
	
	
	public  DropTarget buildDropTarget() {
		
		DropTarget ret = new DropTarget(thecontainer.getBar(), DND.DROP_MOVE | DND.DROP_COPY); 
		ret.setTransfer(types);
		
	ExpandBarDropTargetEffect effect = new ExpandBarDropTargetEffect(thecontainer) ;
		
		ret.setDropTargetEffect(effect );
		ret.addDropListener(new MyBarDropListener(effect) ); 
		return ret ;
		
	}
	
	
	








	
	
	
	
	
	
	

}
