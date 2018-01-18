package tcxmlplugin.composite;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import tcxmlplugin.TcXmlPluginController;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IStatus;

public class AddTestCaseComposite extends Composite{
	private DataBindingContext m_bindingContext;
	
	
	public static class TestCaseModel {
		
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
		
		private String testCaseName;
		
		
		private IFolder targetFolder;
		
		private boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			propertyChangeSupport.firePropertyChange("valid", this.valid,
					this.valid = valid);
			
		}

		public String getTestCaseName() {
			return testCaseName;
		}

		public IFolder getTargetFolder() {
			return targetFolder;
		}

		public void setTargetFolder(IFolder targetFolder) {
			propertyChangeSupport.firePropertyChange("targetFolder", this.testCaseName,
					this.targetFolder = targetFolder);
			
		}

		public void setTestCaseName(String testCaseName) {
			propertyChangeSupport.firePropertyChange("testCaseName", this.testCaseName,
					this.testCaseName = testCaseName);
			
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }
		
	}
	
	
	
	private Text text;
	
	
	private TestCaseModel model;


	public AddTestCaseComposite(Composite parent, int style) {
		
				super(parent, style);
				model = new TestCaseModel();
		setLayout(new GridLayout(2, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNameOfThe = new Label(this, SWT.NONE);
		lblNameOfThe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNameOfThe.setText("Name of the TestCase");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		// TODO Auto-generated constructor stub
	}

	public TestCaseModel getModel() {
		return model;
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue testCaseNameModelObserveValue = PojoProperties.value("testCaseName").observe(model);
		
		UpdateValueStrategy target2model = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		Binding binding = bindingContext.bindValue(observeTextTextObserveWidget, testCaseNameModelObserveValue, target2model,null );
		
		target2model.setBeforeSetValidator(new IValidator() {
			
			@Override
			public IStatus validate(Object value) {
				String el =(String)value;
				IStatus ret=ValidationStatus.ok();
				if(el.isEmpty()){
					ret =ValidationStatus.error(" Test Case Name cannnot be empty");
				}else {
					if(TcXmlPluginController.getInstance().isAlreadyExistingTestCase(el , model.getTargetFolder())){
						ret=ValidationStatus.error("Testcase already exist in project");
					}
				}
				
				model.setValid(ret.isOK());
				return ret;
			}
		});
		
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
		
		
		
		return bindingContext;
	}
	
	
	public void setTargetFolder ( IFolder target){
		
		model.setTargetFolder(target);
		
	}
	
	
	public IFolder getTargetFolder() {
		
		return model.getTargetFolder();
	}
	
	
	public String getNewTestCaseName() {
		return model.getTestCaseName();
		
		
		
	}
	
	
	
}
