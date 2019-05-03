package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxmlplugin.model.TransactionEditorModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class TransactionViewer extends Composite {
	private DataBindingContext m_bindingContext;
	
	
	private TcXmlController controller;
	private Text nametext;
	
	
	
	
	private TransactionEditorModel editorModel ;
	private List list;

	public TransactionViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller = controller;
		setLayout(new GridLayout(2, false));
		
		editorModel= new TransactionEditorModel();
		
		Group grpTransactions = new Group(this, SWT.NONE);
		grpTransactions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTransactions.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		grpTransactions.setText("transactions");
		
		list = new List(grpTransactions, SWT.BORDER);
		
		Group grpDetails = new Group(this, SWT.NONE);
		grpDetails.setLayout(new GridLayout(3, false));
		grpDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpDetails.setText("details");
		new Label(grpDetails, SWT.NONE);
		
		Label lblGenneral = new Label(grpDetails, SWT.NONE);
		lblGenneral.setText("Genneral");
		new Label(grpDetails, SWT.NONE);
		new Label(grpDetails, SWT.NONE);
		
		Label label = new Label(grpDetails, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblName = new Label(grpDetails, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("name");
		
		nametext = new Text(grpDetails, SWT.BORDER);
		nametext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpDetails, SWT.NONE);
		new Label(grpDetails, SWT.NONE);
		
		Label lblStart = new Label(grpDetails, SWT.NONE);
		lblStart.setText("start");
		new Label(grpDetails, SWT.NONE);
		new Label(grpDetails, SWT.NONE);
		
		Label label_1 = new Label(grpDetails, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblStep = new Label(grpDetails, SWT.NONE);
		lblStep.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStep.setText("step");
		
		Combo startStepCombo = new Combo(grpDetails, SWT.NONE);
		startStepCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpDetails, SWT.NONE);
		new Label(grpDetails, SWT.NONE);
		
		Label lblEnd = new Label(grpDetails, SWT.NONE);
		lblEnd.setText("end");
		new Label(grpDetails, SWT.NONE);
		new Label(grpDetails, SWT.NONE);
		
		Label label_2 = new Label(grpDetails, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblStep_1 = new Label(grpDetails, SWT.NONE);
		lblStep_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStep_1.setText("step");
		
		Combo endStepcombo = new Combo(grpDetails, SWT.NONE);
		endStepcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpDetails, SWT.NONE);
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList alltransactionsControllerObserveList = PojoProperties.list("alltransactions").observe(controller);
		bindingContext.bindList(itemsListObserveWidget, alltransactionsControllerObserveList, null, null);
		//
		return bindingContext;
	}
}
