package tcxmlplugin.composite.stepViewer;

import java.util.Iterator;

import org.eclipse.swt.widgets.Composite;

import model.Function;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.view.FunctionView;

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

	public StepViewer getFunction(String libName, String funcName) throws TcXmlException {
		StepViewer ret = null;
		Iterator<StepViewer> it = stepViwerChildren.iterator();
		while (it.hasNext()) {
			StepViewer stepViewer = (StepViewer) it.next();
			if (stepViewer.getViewer()  instanceof FunctionView ) {
				FunctionView fv = (FunctionView) stepViewer.getViewer();
				Function fct = fv.getFunction();
				if( fct.getName().equals(funcName) ) {
					
					ret = stepViewer; break;
				}
				
				
				
				
			}
			
		}
		if(ret == null) {
			throw new TcXmlException("fail to find function-"+  funcName +  " in current library" , new IllegalArgumentException());
			
		}
		return ret;
	}
	
	
	

}
