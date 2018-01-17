package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import tcxml.model.Step;
import tcxmlplugin.composite.BasicView;
import tcxmlplugin.composite.BlockView;
import tcxmlplugin.composite.StepViewer;

public class BlockViewer extends AbstractStepViewer  {
	




	public BlockViewer(Composite parent, int style) {
		super(parent, style,new BlockView(parent, style));

		
		
	}	

	@Override
	public String getTitle() {
		String ret = " Group " + viewer.getModel().getAction();
		return ret;
	}
	
	
	
	

	
	
	
	public void addStep(Step step) {
		
		((StepContainer)viewer).addStep(step);


		
		
	}






		
	}


