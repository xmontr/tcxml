package tcxmlplugin.composite.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;



import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.arguments.ArgumentViewFactory;
import tcxmlplugin.composite.view.arguments.StepArgument;
import tcxml.model.GenericAPIModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.ForWrapper;
import stepWrapper.GenericApiWrapper;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class GenericAPIStepView extends StepView implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;
	
	
	private GenericAPIModel genericapimodel ;
	private Group argGroup;
	private Combo categorycombo;
	private Combo methodCombo;


	private StepArgument ar;
	

	public GenericAPIStepView(Composite parent, int style)  {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		genericapimodel = new GenericAPIModel();
		Group maingroup = new Group(this, SWT.NONE);
		maingroup.setText("Main");
		maingroup.setLayout(new GridLayout(2, false));
		maingroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCategory = new Label(maingroup, SWT.NONE);
		lblCategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCategory.setText("Category");
		
		categorycombo = new Combo(maingroup, SWT.NONE);
		categorycombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(maingroup, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Method");
		
		methodCombo = new Combo(maingroup, SWT.NONE);
		methodCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		argGroup = new Group(this,SWT.SHADOW_ETCHED_IN);
		argGroup.setText("Argument");
		argGroup.setLayout(new FillLayout());
		
		argGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		// binding argumentlectedmethod of model
		genericapimodel.addPropertyChangeListener("methodName", this);
		m_bindingContext = initDataBindings();
		
	}
	
	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {	
		
		if(! (stepWrapper2 instanceof GenericApiWrapper )) {
			throw new TcXmlException("GenericApiWrapper view can only be populated by from a GenericApiWrapper wrapper ", new IllegalArgumentException());
			
		}
		GenericApiWrapper wr = (GenericApiWrapper) stepWrapper2;
		
		Step model = stepWrapper2.getModel();
	
		genericapimodel.setSelectedCategory(wr.getCategory());
			
		genericapimodel.setMethodName(wr.getMethod()); 
		
	}
	
	
	@Override
	public void saveModel() throws TcXmlException {
		
		GenericApiWrapper wr = (GenericApiWrapper)stepWrapper ;
		wr.SaveCategoryMethod(genericapimodel.getSelectedCategory(), genericapimodel.getMethodName());
		// save argument
		
		wr.saveArguments(this.ar.getArguments());
	}
	
	
	private void setArgumentView(StepArgument ar) {
		// remove oldone if necessary
		if (theArgument != null) {
			theArgument.dispose();

		}
		ar.setParent(argGroup);
		argGroup.layout();
		setTheArgument(ar);



	}
	
	
	



	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {

		saveModel();
		PlayingContext ret	 = stepWrapper.play(ctx);
	return ret;
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {

		
		stepWrapper.export(pw);

	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if(prop.equals("methodName")) { //adapt the argument to the selected method
			String newmethod = (String) evt.getNewValue();
			try {
				
				getModel().setMethodName(newmethod);
				ar = ArgumentViewFactory.getArgumentViewForStep(getModel(), this);
				setArgumentView(ar);
				controller.getLog().info("setting nw argument for method : " + newmethod);
			} catch (TcXmlException e) {
				TcXmlPluginController.getInstance().error("fail to create argument view for step", e);

			}
			
			
		}
		

		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsCategorycomboObserveWidget = WidgetProperties.items().observe(categorycombo);
		IObservableList allCategoryGenericapimodelObserveList = BeanProperties.list("allCategory").observe(genericapimodel);
		bindingContext.bindList(itemsCategorycomboObserveWidget, allCategoryGenericapimodelObserveList, null, null);
		//
		IObservableValue observeSelectionCategorycomboObserveWidget = WidgetProperties.selection().observe(categorycombo);
		IObservableValue selectedCategoryGenericapimodelObserveValue = BeanProperties.value("selectedCategory").observe(genericapimodel);
		bindingContext.bindValue(observeSelectionCategorycomboObserveWidget, selectedCategoryGenericapimodelObserveValue, null, null);
		//
		IObservableList itemsMethodComboObserveWidget = WidgetProperties.items().observe(methodCombo);
		IObservableList allMethodGenericapimodelObserveList = BeanProperties.list("allMethod").observe(genericapimodel);
		bindingContext.bindList(itemsMethodComboObserveWidget, allMethodGenericapimodelObserveList, null, null);
		//
		IObservableValue observeSelectionMethodComboObserveWidget = WidgetProperties.selection().observe(methodCombo);
		IObservableValue methodNameGenericapimodelObserveValue = BeanProperties.value("methodName").observe(genericapimodel);
		bindingContext.bindValue(observeSelectionMethodComboObserveWidget, methodNameGenericapimodelObserveValue, null, null);
		//
		return bindingContext;
	}
}
