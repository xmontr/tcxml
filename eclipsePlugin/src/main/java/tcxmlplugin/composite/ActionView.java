package tcxmlplugin.composite;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import tcxml.core.PlayingContext;
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
import tcxmlplugin.job.MultipleStepViewerRunner;


/**
 * 
 * 
 * view of an action
 * @author xav
 *
 */


public class ActionView extends AStepContainer implements TopStepContainer{
	
	
	
	
	private String actionName;


	public ActionView(String name,Composite parent, int style, TcXmlController controller) {
		super(parent, style,controller);

	actionName=name;

	
	
	}
	
	
	public void buildAction(Step step) {

		List<Step> list = step.getStep();
		for (Step step2 : list) { // add the step
			try {
				addStep(step2);
			} catch (TcXmlException e) {
				TcXmlPluginController.getInstance().error("fail to build  action", e);
			}
				
			
		}
		bar.layout();
	
	}




	public String getActionName() {
		return actionName;
	}


	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		
		MultipleStepViewerRunner mc = new MultipleStepViewerRunner(stepViwerChildren);
		
		PlayingContext ret = mc.runSteps(ctx);
		
		return ret;
		
		
	}



	
	@Override
	public void refreshSize() {
		resizeContent();
	}


	public void eexport(PrintWriter pw) throws TcXmlException {
		
		StringBuffer sb = new StringBuffer("// code for action  ").append(actionName);
		pw.println(sb.toString());
		 sb = new StringBuffer(" async function  ").append( getActionName()).append("(){");
		pw.println(sb.toString());
		for (StepViewer stepViewer : stepViwerChildren) {
			stepViewer.export(pw);
			
		}
		
		pw.println("}");
		
	}


	@Override
	public TruLibrary getLibrary() {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
	
	

}
