package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Button;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class TextInputView extends Composite implements SelectionListener{
	private DataBindingContext m_bindingContext;
	private Text text;
	private MenuItem mntmTxt;
	private MenuItem mntmJs;
	private Button displayButton;
	
	private TextInputViewModel inputtextmodel;

	

	
	
	
	
 	public TextInputViewModel getInputtextmodel() {
		return inputtextmodel;
	}
	public static class TextInputViewModel {
		
		private boolean javascript;
		private String inputData;
		
		
		private PropertyChangeSupport propertyChangeSupport;
		
		public TextInputViewModel() {
			propertyChangeSupport = new PropertyChangeSupport(this);
			inputData = new String();
			
		}
		
		public  void setJavascript(boolean isj) {
			propertyChangeSupport.firePropertyChange("javascript", this.javascript,
					this.javascript = isj);		

			
		}
		
		
		public boolean getJavascript() {
			
			return this.javascript;
		}

		public void setInputData(String inputData) {
			propertyChangeSupport.firePropertyChange("inputData", this.inputData,
					this.inputData = inputData);
			
			
			
		}

		public String getInputData() {
			return inputData;
		}
		
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

		
		
	}
	
	
	

	public TextInputView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		inputtextmodel = new TextInputViewModel();
		displayButton = new Button(this, SWT.NONE);
		displayButton.setText("TXT");
		
	
		Menu menu = new Menu(displayButton);
		
		displayButton.setMenu(menu);
		
		mntmTxt = new MenuItem(menu, SWT.NONE);
		mntmTxt.setText("TXT");
		mntmTxt.addSelectionListener(this);
		mntmJs = new MenuItem(menu, SWT.NONE);
		mntmJs.setText("JS");
		mntmJs.addSelectionListener(this);
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.widget == mntmTxt ) {
			displayButton.setText(mntmTxt.getText());
			inputtextmodel.setJavascript(false);
			
		}
		
		if(e.widget == mntmJs ) {
			displayButton.setText(mntmJs.getText());
			inputtextmodel.setJavascript(true);	
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		displayButton.setText(mntmTxt.getText());
		
	}

	public void setJavascript(boolean isj) {
		inputtextmodel.setJavascript(isj);
		if(isj == true) {
			displayButton.setText(mntmJs.getText());
		}
		else {
			
			displayButton.setText(mntmTxt.getText());
		}
		
	}

	public void setInputData(String inputData) {
		inputtextmodel.setInputData(inputData);
		
	}
	
	
	public boolean getJavascript() {
		
		return inputtextmodel.getJavascript();
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue inputDataInputtextmodelObserveValue = BeanProperties.value("inputData").observe(inputtextmodel);
		bindingContext.bindValue(observeTextTextObserveWidget, inputDataInputtextmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
