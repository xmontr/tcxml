package tcxmlplugin.composite.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.TestObjectWrapper;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlException;
import tcxml.model.AbstractModel;
import tcxml.model.ArgModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.arguments.ArgumentViewFactory;
import tcxmlplugin.composite.view.arguments.DynamicArgumentView;
import tcxmlplugin.composite.view.arguments.StepArgument;

public class TestObjectView extends StepView implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;

	//private TruLibrary library;

	

	private Group grpArguments;



	public TestObjectView(Composite parent, int style )  {

		super(parent, style);
		testobjectmodel = new TestObjectModel();

		testobjectmodel.addPropertyChangeListener("selectedAction", this);

		this.setLayout(new GridLayout(2, false));

		Label actionLabel = new Label(this, SWT.NONE);
		actionLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		actionLabel.setText("action");

		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		grpIdentification = new Group(this, SWT.NONE);
		grpIdentification.setText("identification");
		grpIdentification.setLayout(new GridLayout(3, false));
		grpIdentification.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		
		lblMethod = new Label(grpIdentification, SWT.NONE);
		lblMethod.setText("method");
		new Label(grpIdentification, SWT.NONE);
		
		combo_method = new Combo(grpIdentification, SWT.NONE);
		combo_method.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		
		// identification by xpath
		Label xpathLabel = new Label(grpIdentification, SWT.NONE);
		xpathLabel.setText("Xpath");
		
		HighLightButton = new Button(grpIdentification, SWT.NONE);
		HighLightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					highlightXpath();
				} catch (TcXmlException e1) {
					TcXmlPluginController.getInstance().error("fail to highlight xpath " + testobjectmodel.getXpath() , e1);
				}
			}
		});
		HighLightButton.setToolTipText("HighLight element");
		HighLightButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/system-search-6.png"));
		HighLightButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		xpathText = new Text(grpIdentification, SWT.BORDER);
		xpathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// identification by javascript
		Label javascriptLabel = new Label(grpIdentification, SWT.NONE);
		javascriptLabel.setText("javascript");
		
		HighLightButton2 = new Button(grpIdentification, SWT.NONE);
		HighLightButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					highlightXpath();
				} catch (TcXmlException e1) {
					TcXmlPluginController.getInstance().error("fail to highlight xpath " + testobjectmodel.getXpath() , e1);
				}
			}
		});
		HighLightButton2.setToolTipText("HighLight element");
		HighLightButton2.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/system-search-6.png"));
		HighLightButton2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		javascriptText = new Text(grpIdentification, SWT.BORDER);
		javascriptText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpIdentification, SWT.NONE);
		new Label(grpIdentification, SWT.NONE);
		
		selectInBrowserButton = new Button(grpIdentification, SWT.NONE);
		selectInBrowserButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		selectInBrowserButton.setText("Select in the Browser");
		selectInBrowserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					TestObjectWrapper newObject = TcXmlPluginController.getInstance().selectInBrowser(getLibrary());
					setStepWrapper(newObject);
					
				} catch (TcXmlException e1) {
					TcXmlPluginController.getInstance().error("fail to highlight xpath " + testobjectmodel.getXpath() , e1);
				}
			}
		});
		
		
		
		
		

		grpArguments = new Group(this, SWT.SHADOW_ETCHED_IN);
		grpArguments.setLayout(new FillLayout());

		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpArguments.setText("arguments");
		
		
		label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		new Label(this, SWT.NONE);

		m_bindingContext = initDataBindings();
	}

	protected void highlightXpath() throws TcXmlException {

		
		
		String xpath = testobjectmodel.getXpath();
		controller.highLightXpath(xpath );
		
		
	}

	public static class TestObjectModel extends AbstractModel {

		public List<String> getAllActions() {
			return allActions;
		}

		public void setAllActions(List<String> allActions) {
			propertyChangeSupport.firePropertyChange("allActions", this.allActions, this.allActions = allActions);

		}

		public String getSelectedAction() {
			return selectedAction;
		}

		public void setSelectedAction(String selectedAction) {
			propertyChangeSupport.firePropertyChange("selectedAction", this.selectedAction,
					this.selectedAction = selectedAction);

		}

		public String getXpath() {
			return xpath;
		}

		public void setXpath(String xpath) {
			propertyChangeSupport.firePropertyChange("xpath", this.xpath, this.xpath = xpath);

		}

		public List<String> getAllMethods() {
			return allMethods;
		}

		public void setAllMethods(List<String> allMethods) {
			propertyChangeSupport.firePropertyChange("allMethods", this.allMethods, this.allMethods = allMethods);
			
		}

		public String getSelectedMethod() {
			return selectedMethod;
		}

		public void setSelectedMethod(String selectedMethod) {
			propertyChangeSupport.firePropertyChange("selectedMethod", this.selectedMethod,
					this.selectedMethod = selectedMethod);
			;
		}

		public String getJavascript() {
			return javascript;
		}

		public void setJavascript(String javascript) {
			propertyChangeSupport.firePropertyChange("javascript", this.javascript, this.javascript = javascript);
			
		}

		private List<String> allMethods;

		private String selectedMethod;

		private String javascript;

		private List<String> allActions;

		private String selectedAction;

		private String xpath;

		public TestObjectModel() {

		
		}

	


	}

	private TestObjectModel testobjectmodel;
	private Text xpathText;
	private Text javascriptText;
	private Combo combo;
	private Group grpIdentification;
	private Label label;
	private Button HighLightButton;
	private Button HighLightButton2;
	private Label lblMethod;
	private Combo combo_method;
	private Button selectInBrowserButton;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		if(evt.getPropertyName().equals("selectedAction")) {
			
			String newAction = (String) evt.getNewValue();
			StepArgument ar;
			try {
				getModel().setAction(newAction);
				ar = ArgumentViewFactory.getArgumentViewForStep(this);
				
			
				
				
				setArgumentView(ar);
				controller.getLog().info("setting nw action for step : " + newAction);
			} catch (TcXmlException e) {
				TcXmlPluginController.getInstance().error("fail to create argument view for step", e);

			}
			
			
			
		}


	}

	private void setArgumentView(StepArgument ar) {
		// remove oldone if necessary
		if (theArgument != null) {
			theArgument.dispose();

		}
		ar.setParent(grpArguments);
		grpArguments.layout();
		setTheArgument(ar);

		



	}
	
	
@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
	
	if(! (stepWrapper2 instanceof TestObjectWrapper )) {
		throw new TcXmlException("TestObject view can only be populated by from a TestObject wrapper ", new IllegalArgumentException());
		
	}
	
	TestObjectWrapper testobjectwrapper = (TestObjectWrapper)stepWrapper2 ;
	
	HashMap<String, ArgModel> argumentMap = stepWrapper2.getArgumentMap();	
	
	Step model = testobjectwrapper.getModel();
	
	
	
	
		testobjectmodel.setAllActions(testobjectwrapper.getAvailableAction());
		testobjectmodel.setSelectedAction(testobjectwrapper.getAction());
		
		
		
		testobjectmodel.setAllMethods(testobjectwrapper.getIdentificationsMethods());
		


		if (!testobjectwrapper.isBrowserStep()) { // testobject is not browser

			showXpath();
			// find the testobject in the script or in the lib

				// the selected method
	String act = testobjectwrapper.getActiveIdentification();
	testobjectmodel.setSelectedMethod(act);
			
			
		if(act.equals("XPath"))	{
			testobjectmodel.setXpath(testobjectwrapper.getIdentForTestObject( "XPath"));
			
		}
		
		if(act.equals("JavaScript"))	{
			testobjectmodel.setJavascript(testobjectwrapper.getIdentForTestObject( "JavaScript"));
			
		}
			

	
			
		} else { // testobject is browser

			hideXpath();

		

		}
		layout(true,true);

	}

	private void hideXpath() {
		grpIdentification.setVisible(false);

	}

	private void showXpath() {
		grpIdentification.setVisible(true);

	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsTestobjectmodelObserveList = BeanProperties.list("allActions").observe(testobjectmodel);
		bindingContext.bindList(itemsComboObserveWidget, allActionsTestobjectmodelObserveList, null, null);
		//
		IObservableList itemsCombomethodObserveWidget = WidgetProperties.items().observe(combo_method);
		IObservableList allMethodsTestobjectmodelObserveList = BeanProperties.list("allMethods").observe(testobjectmodel);
		bindingContext.bindList(itemsCombomethodObserveWidget, allMethodsTestobjectmodelObserveList, null, null);
		
		
		
		
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue selectedActionTestobjectmodelObserveValue = BeanProperties.value("selectedAction").observe(testobjectmodel);
		bindingContext.bindValue(observeSelectionComboObserveWidget, selectedActionTestobjectmodelObserveValue, null, null);
		
		IObservableValue observeSelectionCombomethodObserveWidget = WidgetProperties.selection().observe(combo_method);
		IObservableValue selectedMethodTestobjectmodelObserveValue = BeanProperties.value("selectedMethod").observe(testobjectmodel);
		bindingContext.bindValue(observeSelectionCombomethodObserveWidget, selectedMethodTestobjectmodelObserveValue, null, null);
		
		
		
		
		
		
		//
		IObservableValue observeTextXpathTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(xpathText);
		IObservableValue xpathTestobjectmodelObserveValue = BeanProperties.value("xpath").observe(testobjectmodel);
		bindingContext.bindValue(observeTextXpathTextObserveWidget, xpathTestobjectmodelObserveValue, null, null);
		
		
		IObservableValue observeTextJavascriptTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(javascriptText);
		IObservableValue javascriptTestobjectmodelObserveValue = BeanProperties.value("javascript").observe(testobjectmodel);
		bindingContext.bindValue(observeTextJavascriptTextObserveWidget, javascriptTestobjectmodelObserveValue, null, null);
		
		//
		return bindingContext;
	}

	@Override
	public PlayingContext doplay( PlayingContext ctx) throws TcXmlException {

	saveModel();
	PlayingContext ret = stepWrapper.play(ctx);
	
	return ret;
		
	}



	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		
		TestObjectWrapper towrapper = (TestObjectWrapper)stepWrapper ;
		towrapper.export(pw);
		

		
	}

	@Override
	public void saveModel() throws TcXmlException {
		stepWrapper.saveArguments();
		
		TestObjectWrapper towrapper = (TestObjectWrapper)stepWrapper ;
		towrapper.saveAction(testobjectmodel.getSelectedAction());
		if(!towrapper.isBrowserStep()) {
			
			towrapper.setActiveIdentMehod(testobjectmodel.getSelectedMethod());
			towrapper.setIdentForTestObject("XPath", testobjectmodel.getXpath());
			towrapper.setIdentForTestObject("JavaScript", testobjectmodel.getJavascript());
			
		}

		
		
	}

}



