package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class DesignPalette extends Composite{
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	public DesignPalette(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpPalette = new Group(this, SWT.NONE);
		grpPalette.setText("Palette");
		grpPalette.setLayout(new FillLayout(SWT.VERTICAL));
		
		ExpandBar expandBar = new ExpandBar(grpPalette, SWT.NONE);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setExpanded(true);
		xpndtmNewExpanditem.setText("function");
		
		Composite functionContainer = new Composite(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setControl(functionContainer);
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		functionContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblFunction = formToolkit.createLabel(functionContainer, "function1", SWT.NONE);
		
		Label lblFunction_1 = formToolkit.createLabel(functionContainer, "function2", SWT.NONE);
		
		ExpandItem xpndtmMiscellanous = new ExpandItem(expandBar, SWT.NONE);
		xpndtmMiscellanous.setExpanded(true);
		xpndtmMiscellanous.setText("miscellanous");
		
		Composite miscContainer = new Composite(expandBar, SWT.NONE);
		xpndtmMiscellanous.setControl(miscContainer);
		formToolkit.adapt(miscContainer);
		formToolkit.paintBordersFor(miscContainer);
		xpndtmMiscellanous.setHeight(xpndtmMiscellanous.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		miscContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblMisc = formToolkit.createLabel(miscContainer, "misc 1", SWT.NONE);
		
		ExpandItem xpndtmFlowControl = new ExpandItem(expandBar, SWT.NONE);
		xpndtmFlowControl.setExpanded(true);
		xpndtmFlowControl.setText("Flow Control");
		
		Composite flowContainer = new Composite(expandBar, SWT.NONE);
		xpndtmFlowControl.setControl(flowContainer);
		formToolkit.adapt(flowContainer);
		formToolkit.paintBordersFor(flowContainer);
		xpndtmFlowControl.setHeight(xpndtmFlowControl.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		flowContainer.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblFlow = formToolkit.createLabel(flowContainer, "flow 1 ", SWT.NONE);
		// TODO Auto-generated constructor stub
	}
}
