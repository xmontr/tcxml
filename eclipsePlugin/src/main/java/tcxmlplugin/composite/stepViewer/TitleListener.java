package tcxmlplugin.composite.stepViewer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.widgets.ExpandItem;

public class TitleListener implements PropertyChangeListener {

	private ExpandItem item;
	private StepViewer viewer;

	public TitleListener(ExpandItem xpndtmNewExpanditem, StepViewer tv) {
		this.item = xpndtmNewExpanditem;
		this.viewer = tv ;
		
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("title")   ) {
			System.out.println("changement titre" + viewer.getTitle()  + evt.getPropertyName());
			
			item.setText(viewer.getTitle());
		}

	}

}
