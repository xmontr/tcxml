package tcxmlplugin.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;

import model.Function;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.composite.stepViewer.TitleListener;
import tcxmlplugin.composite.stepViewer.TopStepContainer;
import tcxmlplugin.composite.view.FunctionView;

public class LibraryView extends AStepContainer implements TopStepContainer{
	
	
	public String getLibraryName() {
		return libraryName;
	}





	private TruLibrary Library;

	public TruLibrary getLibrary() {
		return Library;
	}

	public void setLibrary(TruLibrary library) {
		Library = library;
	}
	
	



	private String libraryName;

	
	
	public LibraryView(String name,Composite parent, int style, TcXmlController controller) {
		super(parent, style,controller);
		this.libraryName =name ;
	
	
	
	}
	
	

	public void buildLibrary(TruLibrary truLibrary) {

		;
		List<Step> list = truLibrary.getStep().getStep();
		for (Step step2 : list) { // add the step
			try {
				addStep(step2);
			} catch (TcXmlException e) {
				// TODO Auto-generated catch block
				TcXmlPluginController.getInstance().error("fail to show selected library", e);
			}
				
			
		}
		
	bar.layout();
		
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

@Override
public void showOnTop(StepViewer st) {
	Point p = st.getLocation();
	Scrollable sc = (Scrollable)bar;
	ScrollBar vb = sc.getVerticalBar();
	
	vb.setSelection(p.y);
	
}



}
	


