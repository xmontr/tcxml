package tcxmlplugin.composite.view;

import java.beans.PropertyChangeSupport;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.composite.StepView;

public class WaitView extends StepView  {
	
	
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



		public String getThinkTime() {
			return thinkTime;
		}



		public void setThinkTime(String thinkTime) {
			
			propertyChangeSupport.firePropertyChange("thinkTime", this.thinkTime,
					this.thinkTime = thinkTime);
		}



		private String unit;
		
		
		private String thinkTime ;
		
		
		
		
		private PropertyChangeSupport propertyChangeSupport;
		
		
		
		public WaitViewModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);	
			
			
			
		}
		
		
		
		
	}
	

	public WaitView(Composite parent, int style, TcXmlController controller) {
		super(parent, style, controller);
		this.setLayout(new GridLayout(2, false));
	}
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
		
super.populate(mo);		
		setTitle("Wait" );
		
		
	}
	

}
