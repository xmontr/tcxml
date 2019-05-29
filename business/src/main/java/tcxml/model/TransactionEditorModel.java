package tcxml.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionEditorModel extends AbstractModel {
	
	
	
	
	private List<String> allTransactionsName = new ArrayList<String>();
	
	
	private String selectedTransaction ;
	

	public String getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(String selectedTransaction) {
		propertyChangeSupport.firePropertyChange("selectedTransaction", this.selectedTransaction,
				this.selectedTransaction = selectedTransaction);
		this.selectedTransaction = selectedTransaction;
	}

	public List<String> getAllTransactionsName() {
		return allTransactionsName;
	}

	public void setAllTransactionsName(List<String> allTransactionsName) {
		propertyChangeSupport.firePropertyChange("allTransactionsName", this.allTransactionsName,
				this.allTransactionsName = allTransactionsName);
		
	}



}
