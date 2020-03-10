package tcxmlplugin.dnd;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

import tcxmlplugin.TcXmlPluginController;

public class MyBarDropListener implements DropTargetListener {

	private ExpandBarDropTargetEffect theEffect;

	public MyBarDropListener(ExpandBarDropTargetEffect effect) {
		this.theEffect  = effect ;
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragLeave(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOperationChanged(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetEvent event) {
		//TcXmlPluginController.getInstance().getTcviewer().getc
		theEffect.saveTemporaryItem((String)event.data);

	}

	@Override
	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

}
