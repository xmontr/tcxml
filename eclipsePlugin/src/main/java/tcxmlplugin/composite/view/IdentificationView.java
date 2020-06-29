package tcxmlplugin.composite.view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

import stepWrapper.TestObjectWrapper;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.IdentificationModel;
import tcxml.model.TestObject;
import tcxmlplugin.TcXmlPluginController;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class IdentificationView extends Composite{
	private DataBindingContext m_bindingContext;

	private Label lblMethod;
	private Combo combo_method;
	private Label xpathLabel;
	private Button HighLightButton;
	private Label javascriptLabel;
	private Text javascriptText;
	private Text xpathText;
	
	private IdentificationModel identmodel;
	private TcXmlController controller ;

	private TestObjectWrapper testObjectWrapper;
	

	public IdentificationView(Composite parent, int style,TcXmlController controller) {
		super(parent, style);
		this.controller = controller ;
		setLayout(new GridLayout(3, false));
		identmodel = new IdentificationModel();
		
		lblMethod = new Label(this, SWT.NONE);
		lblMethod.setText("method");
		new Label(this, SWT.NONE);
		
		
		combo_method = new Combo(this, SWT.NONE);
		combo_method.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
				
				xpathLabel = new Label(this, SWT.NONE);
				xpathLabel.setText("Xpath");
		
		HighLightButton = new Button(this, SWT.NONE);
		HighLightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					highlightXpath();
				} catch (TcXmlException e1) {
					TcXmlPluginController.getInstance().error("fail to highlight xpath " + identmodel.getXpath() , e1);
				}
			}
		});
		HighLightButton.setToolTipText("HighLight element");
		HighLightButton.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/system-search-6.png"));
		HighLightButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		xpathText = new Text(this, SWT.BORDER);
		xpathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		javascriptLabel = new Label(this, SWT.NONE);
		javascriptLabel.setText("javascript");
		
		Button HighLightButton2 = new Button(this, SWT.NONE);
		HighLightButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					highlightXpath();
				} catch (TcXmlException e1) {
					TcXmlPluginController.getInstance().error("fail to highlight xpath " + identmodel.getXpath() , e1);
				}
			}
		});
		HighLightButton2.setToolTipText("HighLight element");
		HighLightButton2.setImage(ResourceManager.getPluginImage("tcxmlplugin", "icons/system-search-6.png"));
		HighLightButton2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		javascriptText = new Text(this, SWT.BORDER);
		javascriptText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		m_bindingContext = initDataBindings();
		
		
		
		
		
		
		
	}

	protected void highlightXpath() throws TcXmlException{
		String xpath = identmodel.getXpath();
		controller.highLightXpath(xpath );
		
	}

	public void populate(TestObjectWrapper testObjectWrapper) throws TcXmlException {
		
		this.testObjectWrapper = testObjectWrapper;
		identmodel.setAllMethods(testObjectWrapper.getIdentificationsMethods());
		
		// the selected method
String act = testObjectWrapper.getActiveIdentification();
identmodel.setSelectedMethod(testObjectWrapper.getActiveIdentification());
		
		
	if(act.equals("XPath"))	{
		identmodel.setXpath(testObjectWrapper.getIdentForTestObject( "XPath"));
		
	}
	
	if(act.equals("JavaScript"))	{
		identmodel.setJavascript(testObjectWrapper.getIdentForTestObject( "JavaScript"));
		
	}
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsCombo_methodObserveWidget = WidgetProperties.items().observe(combo_method);
		IObservableList allMethodsIdentmodelObserveList = BeanProperties.list("allMethods").observe(identmodel);
		bindingContext.bindList(itemsCombo_methodObserveWidget, allMethodsIdentmodelObserveList, null, null);
		//
		IObservableValue observeTextXpathTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(xpathText);
		IObservableValue xpathIdentmodelObserveValue = BeanProperties.value("xpath").observe(identmodel);
		bindingContext.bindValue(observeTextXpathTextObserveWidget, xpathIdentmodelObserveValue, null, null);
		//
		IObservableValue observeTextJavascriptTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(javascriptText);
		IObservableValue javascriptIdentmodelObserveValue = BeanProperties.value("javascript").observe(identmodel);
		bindingContext.bindValue(observeTextJavascriptTextObserveWidget, javascriptIdentmodelObserveValue, null, null);
		//
		IObservableValue observeSelectionCombo_methodObserveWidget = WidgetProperties.selection().observe(combo_method);
		IObservableValue selectedMethodIdentmodelObserveValue = BeanProperties.value("selectedMethod").observe(identmodel);
		bindingContext.bindValue(observeSelectionCombo_methodObserveWidget, selectedMethodIdentmodelObserveValue, null, null);
		//
		return bindingContext;
	}

	public void saveModel() throws TcXmlException {
		
		
		testObjectWrapper.saveArguments();
		
		
		testObjectWrapper.saveAction(testObjectWrapper.getAction());
		if(!testObjectWrapper.isBrowserStep()) {
			
			testObjectWrapper.setActiveIdentMehod(identmodel.getSelectedMethod());
			testObjectWrapper.setIdentForTestObject("XPath", identmodel.getXpath());
			testObjectWrapper.setIdentForTestObject("JavaScript", identmodel.getJavascript());
			
		}
		
	}
}
