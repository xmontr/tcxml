package tcxmlplugin.composite.view.arguments;

import java.util.HashMap;
import java.util.List;

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
	
	// value coming from the persitent model
	protected HashMap<String, ArgModel> arg;
	
	
	

	public StepArgument(Composite parent, int style,HashMap<String, ArgModel> arg  ) {
		super(parent, style);
		if(parent instanceof StepView) {
		this.view = (StepView)parent;
		this.arg = arg;
		
		}
	}



}
