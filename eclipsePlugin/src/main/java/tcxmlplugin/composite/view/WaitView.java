package tcxmlplugin.composite.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;

public class WaitView extends StepView  {
	private DataBindingContext m_bindingContext;
	
	
	private WaitViewModel waitmodel ;
	
	
	public static class WaitViewModel {
		
		
		private String interval ;
		
		
		public String getInterval() {
			return interval;
		}



		public void setInterval(String interval) {
			
			propertyChangeSupport.firePropertyChange("interval", this.interval,
					this.interval = interval);
		}



		public String getUnit() {
			return unit;
		}



		public void setUnit(String unit) {
			propertyChangeSupport.firePropertyChange("unit", this.unit,
					this.unit = unit);
			
		}



		public boolean getThinkTime() {
			return thinkTime;
		}



		public void setThinkTime(boolean thinkTime) {
			
			propertyChangeSupport.firePropertyChange("thinkTime", this.thinkTime,
					this.thinkTime = thinkTime);
		}



		private String unit;
		
		
		private boolean thinkTime ;
		
		
		
		
		private PropertyChangeSupport propertyChangeSupport;
		
		
		
		public WaitViewModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);	
			
			
			
		}
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }		
		
		
	}



	
	private Text intervaltext;
	private Text UnitText;
	

	public WaitView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		this.setLayout(new GridLayout(2, false));
		
		Label intervalLabel = new Label(this, SWT.NONE);
		intervalLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		intervalLabel.setText("Interval");
		
		intervaltext = new Text(this, SWT.BORDER);
		intervaltext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label UnitLabel = new Label(this, SWT.NONE);
		UnitLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		UnitLabel.setText("Unit");
		
		UnitText = new Text(this, SWT.BORDER);
		UnitText.setText("");
		UnitText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		waitmodel = new WaitViewModel();
		m_bindingContext = initDataBindings();
	
	}
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
		
super.populate(mo);

String json = mo.getArguments();
String rootKey="Interval";
JsonObject arg = controller.readJsonObject(json);

if(  arg.containsKey(rootKey)) { 
	
	JsonObject data = arg.getJsonObject(rootKey);
	if(data.containsKey("evalJavaScript")) {
		waitmodel.setThinkTime(data.getBoolean("evalJavaScript" , true));
	} else {
		waitmodel.setThinkTime(true);	
	}


	waitmodel.setInterval(data.getString("value","3"));
} else { // default value
	
waitmodel.setThinkTime(true);
waitmodel.setInterval("3");
waitmodel.setUnit("seconds");
	
}

		setTitle( formatTitle(model.getIndex(), "Wait "+ waitmodel.getInterval() + " seconds" ) );
		

		
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextIntervaltextObserveWidget = WidgetProperties.text(SWT.Modify).observe(intervaltext);
		IObservableValue intervalWaitmodelObserveValue = BeanProperties.value("interval").observe(waitmodel);
		bindingContext.bindValue(observeTextIntervaltextObserveWidget, intervalWaitmodelObserveValue, null, null);
		//
		IObservableValue observeTextUnitTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(UnitText);
		IObservableValue unitWaitmodelObserveValue = BeanProperties.value("unit").observe(waitmodel);
		bindingContext.bindValue(observeTextUnitTextObserveWidget, unitWaitmodelObserveValue, null, null);
		//
		return bindingContext;
	}



	@Override
	public void playInteractive() throws TcXmlException {
		throw new TcXmlException("not implemented", new IllegalAccessException());
		
	}
}
