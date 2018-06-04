package tcxmlplugin.composite.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.runner.TestObjectRunner;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.view.arguments.ArgumentFactory;
import tcxmlplugin.composite.view.arguments.StepArgument;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class TestObjectView extends StepView implements PropertyChangeListener {
	private DataBindingContext m_bindingContext;

	private TruLibrary library;

	

	private Group grpArguments;

	public TruLibrary getLibrary() {
		return library;
	}

	public void setLibrary(TruLibrary library) {
		this.library = library;
	}

	public TestObjectView(Composite parent, int style, TcXmlController controller) {

		super(parent, style, controller);
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

		grpArguments = new Group(this, SWT.SHADOW_ETCHED_IN);
		grpArguments.setLayout(new FillLayout());

		grpArguments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpArguments.setText("arguments");
		new Label(this, SWT.NONE);
		
		label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));

		m_bindingContext = initDataBindings();
	}

	protected void highlightXpath() throws TcXmlException {

		
		
		String xpath = testobjectmodel.getXpath();
		controller.highLightXpath(xpath );
		
		
	}

	public static class TestObjectModel {

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

		private PropertyChangeSupport propertyChangeSupport;

		private List<String> allActions;

		private String selectedAction;

		private String xpath;

		public TestObjectModel() {

			propertyChangeSupport = new PropertyChangeSupport(this);
		}

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(listener);
		}

	}

	private TestObjectModel testobjectmodel;
	private Text xpathText;
	private Combo combo;
	private Group grpIdentification;
	private Label label;
	private Button HighLightButton;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String newAction = (String) evt.getNewValue();
		StepArgument ar;
		try {
			ar = ArgumentFactory.getArgumentForTestObject(newAction, this);
			setArgumentView(ar);
			TcXmlPluginController.getInstance().info("setting nw action for step : " + newAction);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("fail to create argument view for step", e);

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

	public void populate(Step mo) throws TcXmlException {

		super.populate(mo);
		testobjectmodel.setAllActions(controller.getAvailableActionForStep(mo, library));
		testobjectmodel.setSelectedAction(mo.getAction());

		TestObject to = null;
		if (!controller.isBrowserStep(mo)) { // testobject is not browser

			showXpath();
			// find the testobject in the script or in the lib
			if (library == null) {
				to = controller.getTestObjectById(mo.getTestObject());

			} else {
				to = controller.getTestObjectById(mo.getTestObject(), library);

			}

			testobjectmodel.setXpath(controller.getXpathForTestObject(to));
			
		} else { // testobject is browser

			hideXpath();

		

		}

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
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue selectedActionTestobjectmodelObserveValue = BeanProperties.value("selectedAction").observe(testobjectmodel);
		bindingContext.bindValue(observeSelectionComboObserveWidget, selectedActionTestobjectmodelObserveValue, null, null);
		//
		IObservableValue observeTextXpathTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(xpathText);
		IObservableValue xpathTestobjectmodelObserveValue = BeanProperties.value("xpath").observe(testobjectmodel);
		bindingContext.bindValue(observeTextXpathTextObserveWidget, xpathTestobjectmodelObserveValue, null, null);
		//
		return bindingContext;
	}

	@Override
	public PlayingContext play( PlayingContext ctx) throws TcXmlException {
		TestObjectRunner runner = new TestObjectRunner(model,library, controller);
		
	PlayingContext ret = runner.runStep(ctx);
	return ret;
		
	}

	@Override
	public String buildTitle(Step mo) throws TcXmlException {
		
		String ret;
		TestObject to = null;
		if (!controller.isBrowserStep(mo)) { // testobject is not browser
			if (library == null) {
				to = controller.getTestObjectById(mo.getTestObject());

			} else {
				to = controller.getTestObjectById(mo.getTestObject(), library);

			}
			
			
			 ret = formatTitle(mo.getIndex(), mo.getAction() + " on " + to.getAutoName());
			
			
		}else {// testobject is browser
			
			ret = formatTitle(mo.getIndex(), mo.getAction() + " on Browser ");	
			
		}
		
		

		return ret;
	}


}
