package tcxmlplugin.composite.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;


import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxml.model.WaitModel;
import tcxmlplugin.composite.StepView;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.ForWrapper;
import stepWrapper.WaitWrapper;

import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class WaitView extends StepView  {
	private DataBindingContext m_bindingContext;
	
	
	private WaitModel waitmodel ;
	
	
	
	private TextInputView textInputView;
	private Combo comboUnit;
	

	public WaitView(Composite parent, int style )  {
		super(parent, style );
		
		// color for the viewer
		color=SWT.COLOR_BLUE ;
		this.setLayout(new GridLayout(2, false));
		
		Label intervalLabel = new Label(this, SWT.NONE);
		intervalLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		intervalLabel.setText("Interval");
		
		textInputView = new TextInputView(this, SWT.NONE);
		
		Label UnitLabel = new Label(this, SWT.NONE);
		UnitLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		UnitLabel.setText("Unit");
		
		comboUnit = new Combo(this, SWT.NONE);
		comboUnit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		waitmodel = new WaitModel();
		m_bindingContext = initDataBindings();
	
	}
	
	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2 ) throws TcXmlException {	
		
		if(! (stepWrapper2 instanceof WaitWrapper )) {
			throw new TcXmlException("wait view can only be populated by from a waitwrapper", new IllegalArgumentException());
			
		}
		
		HashMap<String, ArgModel> argumentMap = stepWrapper2.getArgumentMap();	
		
		ArgModel arguinter = argumentMap.get("Interval");

		
		
		waitmodel.setInterval(arguinter);
		
		
		ArrayList<String> li = new ArrayList<String>();
		li.add("Seconds");
		li.add("MilliSeconds");
		
		waitmodel.setAllUnits(li);
		
	
		textInputView.SetArgModel(arguinter);
		

			waitmodel.setSelectedunit(argumentMap.get("Unit").getValue());
			
		



		
		

		
		
	}







	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		
		
	PlayingContext ret = stepWrapper.play(ctx);
	return ret;
	}



	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
	
		WaitWrapper waitwrapper = (WaitWrapper)stepWrapper ;
		waitwrapper.export(pw);
		
	}




	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboUnitObserveWidget = WidgetProperties.items().observe(comboUnit);
		IObservableList allUnitsWaitmodelObserveList = BeanProperties.list("allUnits").observe(waitmodel);
		bindingContext.bindList(itemsComboUnitObserveWidget, allUnitsWaitmodelObserveList, null, null);
		//
		IObservableValue observeSelectionComboUnitObserveWidget = WidgetProperties.selection().observe(comboUnit);
		IObservableValue selectedunitWaitmodelObserveValue = BeanProperties.value("selectedunit").observe(waitmodel);
		bindingContext.bindValue(observeSelectionComboUnitObserveWidget, selectedunitWaitmodelObserveValue, null, null);
		//
		return bindingContext;
	}


	@Override
	public void saveModel() throws TcXmlException {
		// TODO Auto-generated method stub
		
	}
}
