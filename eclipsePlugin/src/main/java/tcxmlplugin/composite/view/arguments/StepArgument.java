package tcxmlplugin.composite.view.arguments;

import javax.json.JsonObject;
import javax.sound.midi.ControllerEventListener;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;

public abstract class StepArgument  extends Composite {
	
	
	protected  StepView view;
	protected JsonObject arg;

	public StepArgument(Composite parent, int style) {
		super(parent, style);
		if(parent instanceof StepView) {
		this.view = (StepView)parent;
		}
	}

	public void populate(String jsonArg ) throws TcXmlException {


TcXmlController controller = view.getController();
arg = controller.readJsonObject(jsonArg);

		
		
	}

}
