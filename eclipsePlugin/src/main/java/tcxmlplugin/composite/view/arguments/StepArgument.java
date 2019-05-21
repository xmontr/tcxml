package tcxmlplugin.composite.view.arguments;

import java.util.HashMap;

import javax.json.JsonObject;
import javax.sound.midi.ControllerEventListener;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;

public abstract class StepArgument  extends Composite {
	
	
	protected  StepView view;
	//protected JsonObject arg;
	
	
	protected HashMap<String, ArgModel> arg;
	

	public StepArgument(Composite parent, int style) {
		super(parent, style);
		if(parent instanceof StepView) {
		this.view = (StepView)parent;
		}
	}

	public void populate(HashMap<String, ArgModel> arg ) throws TcXmlException {
this.arg = arg;

//TcXmlController controller = view.getController();
//arg = controller.readJsonObject(jsonArg);

		
		
	}

}
