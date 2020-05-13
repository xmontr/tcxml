package tcxmlplugin.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import tcxml.model.CallFunctionAttribut;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;

import tcxmlplugin.composite.stepViewer.MainStepContainer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxml.model.LibraryModel;

public class LibraryViewer extends Composite {
	
	
	private LibraryModel model = new LibraryModel() ; 
	private Combo combo;
	
	private DataBindingContext m_bindingContext;
	
	
	private Map<String, LibraryView> librariesView;
	
	
	public Map<String, LibraryView> getLibrariesView() {
		return librariesView;
	}



	private TcXmlController controller;
	private Composite maincontainer;
	private StackLayout maincontainerlayout;
	private StackLayout functionlayout;
	private Composite stepContainer;
	private SnapshotViewer snapshotviewer;
	private Composite stepContainerwithsnapshot;
	private Composite stepcontainerwithoutsnapshot;
	private Composite viewwithSnapshot;
	private Composite viewWitoutSnapshot;
	private boolean isSnapshotlayout;
	
	public LibraryViewer(Composite parent, int style) {
		
		super(parent, style);
		buildGUI();
	}
	

	public LibraryViewer(Composite parent, int style, TcXmlController controller) {
		super(parent, style);

		this.controller = controller;
	
		buildGUI();
		librariesView= new HashMap<String,LibraryView>();	
		
		
		
	
		m_bindingContext = initDataBindings();
		
		
		
	}
	
	
	private void buildGUI() {
		setLayout(new GridLayout(2, false));	
	
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("library:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		maincontainer = new Composite(this,this.getStyle());
		maincontainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		maincontainerlayout = new StackLayout();
		maincontainer.setLayout(maincontainerlayout);	
		stepContainer =  new Composite(maincontainer, getStyle());		
		functionlayout = new StackLayout();
		stepContainer.setLayout(functionlayout);
		
		viewwithSnapshot = createaViewWithsnapshotViewer();
		viewWitoutSnapshot = createViewWithoutsnapshotViewer();

	// default layout with snapshot viewer
		maincontainerlayout.topControl = viewwithSnapshot;
		isSnapshotlayout=true;
		stepContainer.setParent(stepContainerwithsnapshot);
		
		
	}
	
	

	public LibraryModel getModel() {
		return model;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsComboObserveWidget = WidgetProperties.items().observe(combo);
		IObservableList allActionsModelObserveList = BeanProperties.list(LibraryModel.ALL_LIBRARY).observe(model);
		bindingContext.bindList(itemsComboObserveWidget, allActionsModelObserveList, null, null);
		//
		IObservableValue observeSelectionComboObserveWidget = WidgetProperties.selection().observe(combo);
		IObservableValue actionSelectedModelObserveValue = BeanProperties.value(LibraryModel.LIBRARY_SELECTED).observe(model);
		bindingContext.bindValue(observeSelectionComboObserveWidget, actionSelectedModelObserveValue, null, null);
		//
		return bindingContext;
	}



	private Composite createaViewWithsnapshotViewer() {
		
		Composite parent = new Composite(maincontainer, getStyle());
		parent.setLayout(new FillLayout());		
		SashForm sf = new SashForm(parent,SWT.HORIZONTAL);	
		// the list of step at the left
		stepContainerwithsnapshot = new Composite(sf,sf.getStyle());
		stepContainerwithsnapshot.setLayout(new FillLayout());
		//at the right the palette with the snapshotviewer
		PaletteAndSnapshotViewer plt = new PaletteAndSnapshotViewer(sf, getStyle());
		
		snapshotviewer = new SnapshotViewer(plt.getSnapshot(), getStyle(),controller);	
		ActionDesignPalette palette = new ActionDesignPalette(plt.getPalette(), getStyle());			
		return parent;	
			
		}
	
	
	
	private Composite createViewWithoutsnapshotViewer() {
		Composite parent = new Composite(maincontainer, getStyle());
		parent.setLayout(new FillLayout());		
		SashForm sf = new SashForm(parent,SWT.HORIZONTAL);	
		// the list of step at the left
		stepcontainerwithoutsnapshot = new Composite(sf,sf.getStyle());
		stepcontainerwithoutsnapshot.setLayout(new FillLayout());
		//palette at the right
		Composite paletteContainer = new Composite(sf, getStyle());
		paletteContainer.setLayout(new FillLayout());
		ActionDesignPalette palette = new ActionDesignPalette(paletteContainer, getStyle());		
	return parent;	
	}
	
	public void setSnapshotLayout( boolean  issnapshotlayout) {
		this.isSnapshotlayout=issnapshotlayout;
		LibraryView currentaction = (LibraryView) functionlayout.topControl;
		
		
		if(issnapshotlayout == false) { // view without snapshot viewer		
			if(currentaction != null) {
			
				currentaction.removePropertyChangeListener(snapshotviewer);
				
			}
			
			stepContainer.setParent(stepcontainerwithoutsnapshot);	
		maincontainerlayout.topControl = viewWitoutSnapshot;	
		
			
		}else { // view with snapshot viewer 
			if(currentaction != null) {
		
			currentaction.addPropertyChangeListener("currentStepExpanded", snapshotviewer);
			}
			
			stepContainer.setParent(stepContainerwithsnapshot);
			maincontainerlayout.topControl = viewwithSnapshot;
		
		
	}
		maincontainer.layout(true,true);
layout(true,true);
	}


	public void showSelectedLibrary(String libname) {
		
		LibraryView	ctrl = librariesView.get(libname);
		
		LibraryView old = (LibraryView)functionlayout.topControl ;
		if(old != null) {
			old.removePropertyChangeListener(snapshotviewer);	
			
		}

		if(isSnapshotlayout) {
			ctrl.addPropertyChangeListener("currentStepExpanded", snapshotviewer);	
		}
		
		
		
		
		functionlayout.topControl=ctrl;
		layout(true,true);
		
		
		

		
	}

/**
 * 
 * execution interactive of the function with the list of attributs 
 * 
 * 
 * @param libName
 * @param funcName
 * @param ctx
 * @throws TcXmlException 
 */

	public StepViewer getFunction(String libName, String funcName) throws TcXmlException {
		
	
		
	return librariesView.get(libName).getFunction( libName,  funcName) ;	
	}
	
	public void showLibrary(String libName) {
		
		model.setLibrarySelected(libName);
		
	}



	public void buildAllLibraries(Map<String, TruLibrary> libmap) {
		List<String> allLib =    new ArrayList<String>(libmap.keySet())    ;
		
		controller.getLog().info(("found libraries :" + allLib.size()  ))   ;
		 
		 
		 model.setAllLibraries(allLib);
		 
			for (Iterator iterator = allLib.iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				
				LibraryView libv = new LibraryView(name,stepContainer, this.getStyle(), controller);
				libv.buildLibrary(libmap.get(name));
				librariesView.put(name, libv);
				
			}
		
	}



	

}
