package tcxmlplugin.composite;

import java.util.LinkedList;
import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.Transaction;
import tcxml.model.TruLibrary;
import tcxmlplugin.jfaceprovider.CustomLabelProvider;
import tcxmlplugin.jfaceprovider.StepTreeContentProvider;

public class SingleTransactionViewer extends Composite{
	private Text nametext;
	private TcXmlController controller ;
	private ComboViewer endStepcombo;
	private TreeViewer startStepTree;
	private CustomLabelProvider labelProvider;
	
	
	
	
	
	

	public SingleTransactionViewer(Composite parent, int style,TcXmlController controller) {
		super(parent, style);
		this.controller = controller;
		labelProvider = new CustomLabelProvider(controller);
		buildGUI();
		
	}
	
	
	
	public void buildGUI() {		
	
		setLayout(new GridLayout(2, false));		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("name");		
		nametext = new Text(this, SWT.BORDER);
		nametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));								
										
										Label lblStart = new Label(this, SWT.NONE);
										lblStart.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
										lblStart.setText("start");
				new Label(this, SWT.NONE);

				
				Label seplabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				seplabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
			
				
				Label lblStep_1 = new Label(this, SWT.NONE);
				lblStep_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblStep_1.setText("step");				
				startStepTree = new TreeViewer(this, SWT.NONE);
				startStepTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				startStepTree.setContentProvider(new StepTreeContentProvider(controller.getScriptstep()));
				
				startStepTree.setLabelProvider(labelProvider);
				Label lblEnd = new Label(this, SWT.NONE);
				lblEnd.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
				lblEnd.setText("end");
				new Label(this, SWT.NONE);
	
				Label seplabel_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				seplabel_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
			Label lblStep = new Label(this, SWT.NONE);
						lblStep.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
						lblStep.setText("step");				
				endStepcombo = new ComboViewer(this, SWT.NONE);
				endStepcombo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				endStepcombo.setContentProvider(ArrayContentProvider.getInstance());
				endStepcombo.setLabelProvider(labelProvider);
		
	// populate the  start/end combo
			 LinkedList<Step> listep = new LinkedList<Step>(controller.listAllStep().keySet());
			startStepTree.setInput(listep);
			endStepcombo.setInput(listep);
			
				
		
	}
	
	
	public void populate(Transaction tr, TruLibrary lib) throws TcXmlException {
		nametext.setText(tr.getName());
	Step startstep = controller.getStartEndStepForTransactionInLibrary(tr, lib, "start");
	Step endstep = controller.getStartEndStepForTransactionInScript(tr, "end");
		
		
	}
	
	
	public void populate(Transaction tr) throws TcXmlException {
		nametext.setText(tr.getName());
	Step startstep = controller.getStartEndStepForTransactionInScript(tr, "start");
	Step endstep = controller.getStartEndStepForTransactionInScript(tr, "end");
		
		
		
		
	}

}
