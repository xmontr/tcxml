package tcxmlplugin.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public class MyDropListener implements DropTargetListener {

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
		 event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;

	}

	@Override
	public void drop(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}

}
