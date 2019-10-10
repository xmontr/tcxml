package tcxmlplugin.jfaceprovider;

import java.util.HashMap;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.internal.themes.ThemesExtension;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;

public class CustomLabelProvider extends LabelProvider{
	
	
	private TcXmlController controller ;
	private HashMap<Step, TruLibrary> stepcache;
	
	
	public CustomLabelProvider(TcXmlController controller) {
		super();
		this.controller = controller;
		stepcache = controller.listAllStep();
		
	}


	@Override
	public String getText(Object element) {
		
		StringBuffer ret = new StringBuffer() ;
		String title = " title error";
		
		if(element instanceof Step) {
			Step thestep = (Step)element;
			
			TruLibrary lib = stepcache.get(thestep);
				AbstractStepWrapper sw;
			try {
				sw = StepWrapperFactory.getWrapper(thestep, controller, lib);
				 title = sw.getTitle();
			} catch (TcXmlException e) {
				TcXmlPluginController.getInstance().error("failure in step wrapper", e);
			}
			
			
			ret.append(title);
			
			return ret.toString();
			
		}
		
		
		return super.getText(element);
	}

}
