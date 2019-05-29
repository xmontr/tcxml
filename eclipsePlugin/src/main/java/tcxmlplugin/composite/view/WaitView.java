package tcxmlplugin.composite.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.TestObjectRunner;
import tcxml.core.runner.WaitRunner;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxml.model.WaitModel;
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
import org.eclipse.swt.widgets.Combo;

public class WaitView extends StepView  {
	private DataBindingContext m_bindingContext;
	
	
	private WaitModel waitmodel ;
	
	
	
	private TextInputView textInputView;
	private Combo comboUnit;
	

	public WaitView(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);
		
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
	
	
	
	public void populate(Step mo  ) throws TcXmlException {	
		super.populate(mo);		

		
		ArgModel arguinter = argumentMap.get("Interval");
		if(arguinter == null) {// no value so set default args
			arguinter=waitmodel.getDefaultArgInterval();	
			
		}
		
		
		waitmodel.setInterval(arguinter);
		
		
		ArrayList<String> li = new ArrayList<String>();
		li.add("Seconds");
		li.add("MilliSeconds");
		
		waitmodel.setAllUnits(li);
		
	
		textInputView.SetArgModel(arguinter);
		
		if(argumentMap.get("Unit") != null) {
			
			waitmodel.setSelectedunit(argumentMap.get("Unit").getValue());
			
			
		} else {
			waitmodel.setSelectedunit("Seconds");
			
		}



		
		

		
		
	}







	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		WaitRunner runner = new WaitRunner(model, controller);
		
	PlayingContext ret = runner.runStep(ctx);
	return ret;
	}



	@Override
	public String buildTitle() throws TcXmlException {
		String ret = formatTitle(model.getIndex(), "Wait "+ waitmodel.getInterval().getValue() + " seconds" ) ;
		return ret;
	}



	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		pw.println(getTitle());
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
}
