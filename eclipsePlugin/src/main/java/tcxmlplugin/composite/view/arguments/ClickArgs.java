package tcxmlplugin.composite.view.arguments;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.json.JsonObject;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import tcxml.core.TcXmlException;
import tcxml.model.Step;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class ClickArgs extends StepArgument{
	private DataBindingContext m_bindingContext;
	
	
	public static class ClickArgModel {
		
		private PropertyChangeSupport propertyChangeSupport;
		
		private long xcoordinate;
		
		private String button;
		
		
		private String ctrlkey;
		
		public String getButton() {
			return button;
		}

		public void setButton(String button) {
			propertyChangeSupport.firePropertyChange("button", this.button,
					this.button = button);
			;
		}

		public String getCtrlkey() {
			return ctrlkey;
		}

		public void setCtrlkey(String ctrlkey) {
			propertyChangeSupport.firePropertyChange("ctrlkey", this.ctrlkey,
					this.ctrlkey = ctrlkey);
			
		}

		public String getAltkey() {
			return altkey;
		}

		public void setAltkey(String altkey) {
			propertyChangeSupport.firePropertyChange("altkey", this.altkey,
					this.altkey = altkey);
			
		}

		public String getShiftkey() {
			return shiftkey;
		}

		public void setShiftkey(String shiftkey) {
			propertyChangeSupport.firePropertyChange("shiftkey", this.shiftkey,
					this.shiftkey = shiftkey);
			;
		}

		private String altkey ;
		
		
		private String shiftkey ;
		
		
		
		public long getXcoordinate() {
			return xcoordinate;
		}

		public void setXcoordinate(long xcoordinate) {
			propertyChangeSupport.firePropertyChange("xcoordinate", this.xcoordinate,
					this.xcoordinate = xcoordinate);
			
		}

		public long getYcoordinate() {
			return ycoordinate;
		}

		public void setYcoordinate(long ycoordinate) {
			propertyChangeSupport.firePropertyChange("ycoordinate", this.ycoordinate,
					this.ycoordinate = ycoordinate);
		}

		private long ycoordinate ;
		
		
		
		
public ClickArgModel() {
			
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

	
	
	
	private ClickArgModel clickmodel;
	private Text textButton;
	private Text textXcoor;
	private Text textYcoor;
	private Text text_CtrlKey;
	private Text text_AltKey;
	private Text text_ShiftKey;
	
	
	
	public ClickArgs (Composite parent, int style) {
		super(parent, style);
		this.clickmodel = new ClickArgModel();
		setLayout(new GridLayout(2, false));
		
		Label lblButton = new Label(this, SWT.NONE);
		lblButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblButton.setText("button");
		
		textButton = new Text(this, SWT.BORDER);
		textButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblXcoor = new Label(this, SWT.NONE);
		lblXcoor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblXcoor.setText("X coordinate");
		
		textXcoor = new Text(this, SWT.BORDER);
		textXcoor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Y coordinate");
		
		textYcoor = new Text(this, SWT.BORDER);
		textYcoor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCtrlKey = new Label(this, SWT.NONE);
		lblCtrlKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCtrlKey.setText("Ctrl Key");
		
		text_CtrlKey = new Text(this, SWT.BORDER);
		text_CtrlKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAltkey = new Label(this, SWT.NONE);
		lblAltkey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAltkey.setText("Altkey");
		
		text_AltKey = new Text(this, SWT.BORDER);
		text_AltKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblShiftKey = new Label(this, SWT.NONE);
		lblShiftKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblShiftKey.setText("Shift Key");
		
		text_ShiftKey = new Text(this, SWT.BORDER);
		text_ShiftKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		
		
	}
	
	@Override
	public void populate(Step model) throws TcXmlException {
		
		super.populate(model);
		long xcoor=0;
		long ycoor=0;
		String button="left";
		String ctrlkey="false";
		String altkey="false";
		String shiftkey="false";
		
		
		
		if(arg.containsKey("X Coordinate")) {
		xcoor = arg.getJsonObject("X Coordinate").getJsonNumber("value").longValueExact();
		}
		
		
		
		if(arg.containsKey("Y Coordinate")) {
		ycoor = arg.getJsonObject("Y Coordinate").getJsonNumber("value").longValueExact();
		}
		
		if(arg.containsKey("Button")) {
			 button = arg.getJsonObject("Button").getJsonString("value").getString() ;
	}
		
		if(arg.containsKey("Ctrl Key")) {
			 ctrlkey = arg.getJsonObject("Ctrl Key").getJsonString("value").getString() ;
	}
		if(arg.containsKey("Alt Key")) {
			 altkey = arg.getJsonObject("Alt Key").getJsonString("value").getString() ;
	}
		
		if(arg.containsKey("Shift Key")) {
			 altkey = arg.getJsonObject("Shift Key").getJsonString("value").getString() ;
	}
	
		clickmodel.setAltkey(altkey);
		clickmodel.setButton(button);
		clickmodel.setCtrlkey(ctrlkey);
		clickmodel.setShiftkey(shiftkey);
		clickmodel.setXcoordinate(xcoor);
		clickmodel.setYcoordinate(ycoor);
	
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextButtonObserveWidget = WidgetProperties.text(SWT.Modify).observe(textButton);
		IObservableValue buttonClickmodelObserveValue = BeanProperties.value("button").observe(clickmodel);
		bindingContext.bindValue(observeTextTextButtonObserveWidget, buttonClickmodelObserveValue, null, null);
		//
		IObservableValue observeTextTextXcoorObserveWidget = WidgetProperties.text(SWT.Modify).observe(textXcoor);
		IObservableValue xcoordinateClickmodelObserveValue = BeanProperties.value("xcoordinate").observe(clickmodel);
		bindingContext.bindValue(observeTextTextXcoorObserveWidget, xcoordinateClickmodelObserveValue, null, null);
		//
		IObservableValue observeTextTextYcoorObserveWidget = WidgetProperties.text(SWT.Modify).observe(textYcoor);
		IObservableValue ycoordinateClickmodelObserveValue = BeanProperties.value("ycoordinate").observe(clickmodel);
		bindingContext.bindValue(observeTextTextYcoorObserveWidget, ycoordinateClickmodelObserveValue, null, null);
		//
		IObservableValue observeTextText_CtrlKeyObserveWidget = WidgetProperties.text(SWT.Modify).observe(text_CtrlKey);
		IObservableValue ctrlkeyClickmodelObserveValue = BeanProperties.value("ctrlkey").observe(clickmodel);
		bindingContext.bindValue(observeTextText_CtrlKeyObserveWidget, ctrlkeyClickmodelObserveValue, null, null);
		//
		IObservableValue observeTextText_AltKeyObserveWidget = WidgetProperties.text(SWT.Modify).observe(text_AltKey);
		IObservableValue altkeyClickmodelObserveValue = BeanProperties.value("altkey").observe(clickmodel);
		bindingContext.bindValue(observeTextText_AltKeyObserveWidget, altkeyClickmodelObserveValue, null, null);
		//
		IObservableValue observeTextText_ShiftKeyObserveWidget = WidgetProperties.text(SWT.Modify).observe(text_ShiftKey);
		IObservableValue shiftkeyClickmodelObserveValue = BeanProperties.value("shiftkey").observe(clickmodel);
		bindingContext.bindValue(observeTextText_ShiftKeyObserveWidget, shiftkeyClickmodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
