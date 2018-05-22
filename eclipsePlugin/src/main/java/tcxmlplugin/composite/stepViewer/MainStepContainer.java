package tcxmlplugin.composite.stepViewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.ResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public class MainStepContainer extends Composite {


	private StackLayout layout;


	public MainStepContainer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
	//	FillLayout filllayout = new FillLayout();
		
		 layout = new StackLayout();
	this.setLayout(layout);	



	}







public void showAction(Control ctrl) {
	
	layout.topControl=ctrl;
	
}

}
