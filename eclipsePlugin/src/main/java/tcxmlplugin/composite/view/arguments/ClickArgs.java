package tcxmlplugin.composite.view.arguments;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import javax.json.JsonObject;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import tcxml.core.TcXmlException;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxmlplugin.composite.view.TextInputView;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class ClickArgs extends StepArgument{
	private DataBindingContext m_bindingContext;
	
	
	public static class ClickArgModel {
		
		private PropertyChangeSupport propertyChangeSupport;
		
		private ArgModel xcoordinate;
		
		private ArgModel button;
		
		
		private ArgModel ctrlkey;
		
		public ArgModel getButton() {
			return button;
		}

		public void setButton(ArgModel button) {
			propertyChangeSupport.firePropertyChange("button", this.button,
					this.button = button);
			;
		}

		public ArgModel getCtrlkey() {
			return ctrlkey;
		}

		public void setCtrlkey(ArgModel ctrlkey) {
			propertyChangeSupport.firePropertyChange("ctrlkey", this.ctrlkey,
					this.ctrlkey = ctrlkey);
			
		}

		public ArgModel getAltkey() {
			return altkey;
		}

		public void setAltkey(ArgModel altkey) {
			propertyChangeSupport.firePropertyChange("altkey", this.altkey,
					this.altkey = altkey);
			
		}

		public ArgModel getShiftkey() {
			return shiftkey;
		}

		public void setShiftkey(ArgModel shiftkey) {
			propertyChangeSupport.firePropertyChange("shiftkey", this.shiftkey,
					this.shiftkey = shiftkey);
			;
		}

		private ArgModel altkey ;
		
		
		private ArgModel shiftkey ;
		
		
		
		public ArgModel getXcoordinate() {
			return xcoordinate;
		}

		public void setXcoordinate(ArgModel xcoordinate) {
			propertyChangeSupport.firePropertyChange("xcoordinate", this.xcoordinate,
					this.xcoordinate = xcoordinate);
			
		}

		public ArgModel getYcoordinate() {
			return ycoordinate;
		}

		public void setYcoordinate(ArgModel ycoordinate) {
			propertyChangeSupport.firePropertyChange("ycoordinate", this.ycoordinate,
					this.ycoordinate = ycoordinate);
		}

		private ArgModel ycoordinate ;
		
		
		
		
public ClickArgModel() {
			
			propertyChangeSupport = new PropertyChangeSupport(this);
			
			altkey = new ArgModel("Alt Key");
			button = new ArgModel("Button");
			ctrlkey = new ArgModel("Ctrl Key");
			shiftkey = new ArgModel("Shift Key");
			xcoordinate = new ArgModel("X Coordinate");
			ycoordinate = new ArgModel("Y Coordinate");
			
			
			
			
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
	private TextInputView textButton;
	private TextInputView textXcoor;
	private TextInputView textYcoor;
	private TextInputView text_CtrlKey;
	private TextInputView text_AltKey;
	private TextInputView text_ShiftKey;
	
	
	
	public ClickArgs (Composite parent, int style) {
		super(parent, style);
		this.clickmodel = new ClickArgModel();
		setLayout(new GridLayout(2, false));
		
		Label lblButton = new Label(this, SWT.NONE);
		lblButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblButton.setText("button");
		
		textButton = new TextInputView(this, SWT.BORDER);
		textButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblXcoor = new Label(this, SWT.NONE);
		lblXcoor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblXcoor.setText("X coordinate");
		
		textXcoor = new TextInputView(this, SWT.BORDER);
		textXcoor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Y coordinate");
		
		textYcoor = new TextInputView(this, SWT.BORDER);
		textYcoor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCtrlKey = new Label(this, SWT.NONE);
		lblCtrlKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCtrlKey.setText("Ctrl Key");
		
		text_CtrlKey = new TextInputView(this, SWT.BORDER);
		text_CtrlKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAltkey = new Label(this, SWT.NONE);
		lblAltkey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAltkey.setText("Altkey");
		
		text_AltKey = new TextInputView(this, SWT.BORDER);
		text_AltKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblShiftKey = new Label(this, SWT.NONE);
		lblShiftKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblShiftKey.setText("Shift Key");
		
		text_ShiftKey = new TextInputView(this, SWT.BORDER);
		text_ShiftKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
	}
	
	private ArgModel nonEmptyArg(HashMap<String, ArgModel> argu , String name ) {
		ArgModel ret = argu.get(name);
		if( ret == null) {
			
			ret = new ArgModel(name);
			ret.setValue("");
			
		}
		
		
		
		return ret;
		
		
	}
	
	
	
	@Override
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		
		super.populate(argu);
		
		
		text_AltKey.SetArgModel(nonEmptyArg(argu,"Alt Key"));
		text_CtrlKey.SetArgModel(nonEmptyArg(argu,"Ctrl Key"));
		text_ShiftKey.SetArgModel(nonEmptyArg(argu,"Shift Key"));
		textButton.SetArgModel(nonEmptyArg(argu,"Button"));
		textXcoor.SetArgModel(nonEmptyArg(argu,"X Coordinate"));
		textYcoor.SetArgModel(nonEmptyArg(argu,"Y Coordinate"));
		
		
		/*
		
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
		
		
		*/
	
	}

}
