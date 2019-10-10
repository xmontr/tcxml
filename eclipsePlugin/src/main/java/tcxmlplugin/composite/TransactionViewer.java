package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Transaction;
import tcxml.model.TransactionEditorModel;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.BeanProperties;

public class TransactionViewer extends Composite implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;
	
	
	private TcXmlController controller;
	private HashMap<Transaction, TruLibrary> trlibmap;
	
	
	
	
	private TransactionEditorModel editorModel ;
	private List list;
	private SingleTransactionViewer singleTransactionViewer;

	public TransactionViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);
		this.controller = controller;
		editorModel= new TransactionEditorModel();
		
		buildGUI();
		
		m_bindingContext = initDataBindings();
	}
	
	
	
	
	
	private void buildGUI() {
		
		setLayout(new GridLayout(2, false));	
		Group grpTransactions = new Group(this, SWT.NONE);
		grpTransactions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTransactions.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		grpTransactions.setText("transactions");		
		list = new List(grpTransactions, SWT.BORDER | SWT.SINGLE );	
		
		Group grpDetails = new Group(this, SWT.NONE);
		grpDetails.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpDetails.setText("details");
		
		singleTransactionViewer = new SingleTransactionViewer(grpDetails, SWT.NONE,controller);
	
	

		
	}





	public void populate(HashMap<Transaction, TruLibrary> hashMap) throws TcXmlException{
		
		java.util.List<String> allTransactionsName = new ArrayList<String>();
		
	for (Iterator iterator = hashMap.keySet().iterator(); iterator.hasNext();) {
		Transaction transaction = (Transaction) iterator.next();
		
		allTransactionsName.add(transaction.getName())	;		
	}
	
	editorModel.setAllTransactionsName(allTransactionsName);
		
  this.trlibmap=hashMap ;
  
  editorModel.removePropertyChangeListener(this);
  editorModel.addPropertyChangeListener("selectedTransaction", this);
  
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList allTransactionsNameEditorModelObserveList = BeanProperties.list("allTransactionsName").observe(editorModel);
		bindingContext.bindList(itemsListObserveWidget, allTransactionsNameEditorModelObserveList, null, null);
		//
		IObservableValue observeSelectionListObserveWidget = WidgetProperties.selection().observe(list);
		IObservableValue selectedTransactionEditorModelObserveValue = BeanProperties.value("selectedTransaction").observe(editorModel);
		bindingContext.bindValue(observeSelectionListObserveWidget, selectedTransactionEditorModelObserveValue, null, null);
		//
		return bindingContext;
	}





	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if( evt.getPropertyName() == "selectedTransaction") { // new transaction is selected
			
			String newtraname = (String) evt.getNewValue();
			try {
				showTransactionByName(newtraname);
			} catch (TcXmlException e) {
				TcXmlPluginController.getInstance().error("failure in displaying transaction", e);
			}
			
			
			
			
		}
		
	}





	private void showTransactionByName(String newtraname) throws TcXmlException {
		HashMap<Transaction, TruLibrary> alltr = controller.getAlltransactions();
		boolean transactionexist = false ;
		Transaction theselectedtransaction = null ;
		Set<Transaction> transactions = alltr.keySet(); 
		for (Transaction transaction : transactions) {
			if(transaction.getName().equals(newtraname) ) {
				transactionexist = true;
				theselectedtransaction = transaction ;
				
				
			}
			
		}
		
		
		
		if( transactionexist) {
			TruLibrary thetransactionlibrary = alltr.get(theselectedtransaction) ;
			if(thetransactionlibrary == null) {
				singleTransactionViewer.populate(theselectedtransaction);
				
			} else {
				singleTransactionViewer.populate(theselectedtransaction, thetransactionlibrary);
				
				
			}
			
			
			
			
			
		} else {
			
			TcXmlPluginController.getInstance().error("unknown transaction " + newtraname, new IllegalStateException());
		}
		
	}
}
