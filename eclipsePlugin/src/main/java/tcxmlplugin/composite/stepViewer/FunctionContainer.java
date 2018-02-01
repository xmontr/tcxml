package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.model.TruLibrary;

public class FunctionContainer  extends MainStepContainer {
	
	
	private TruLibrary Library;

	public TruLibrary getLibrary() {
		return Library;
	}

	public void setLibrary(TruLibrary library) {
		Library = library;
	}

	public FunctionContainer(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
