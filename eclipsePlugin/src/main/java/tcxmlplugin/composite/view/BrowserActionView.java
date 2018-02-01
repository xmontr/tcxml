package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;

public class BrowserActionView extends StepView{

	public BrowserActionView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		}
	
	
	@Override
	public void populate(Step mo) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(mo);
		
		setTitle(formatTitle(mo.getIndex(), mo.getAction()));
	}

}
