package tcxmlplugin.composite.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandItem;

import tcxmlplugin.composite.stepViewer.StepViewer;

public class StepArgumentListener implements PropertyChangeListener {
	
	private ExpandItem item ;
	private StepViewer viewer;
	

	public StepArgumentListener(ExpandItem xpndtmNewExpanditem, StepViewer tv) {
	item = xpndtmNewExpanditem;
	viewer = tv;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		item.setHeight(viewer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.getParent().layout();
		

	}

}
