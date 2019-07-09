package tcxmlplugin.composite.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import tcxml.core.ExecutionContext;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.TcViewer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.view.arguments.ArgumentViewFactory;
import tcxmlplugin.composite.view.arguments.CallFunctionArg;
import tcxmlplugin.composite.view.arguments.StepArgument;
import tcxmlplugin.job.PlayingJob;
import tcxml.model.ActionsModel;

import org.eclipse.swt.widgets.Label;

import tcxml.model.CallFunctionAttribut;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Group;

public class CallFunctionView  extends StepView implements PropertyChangeListener{
	private DataBindingContext m_bindingContext;
	
	
	
	private CallFunctionViewModel  callfunctmodel;
	private Combo libcombo;
	private Combo funcombo;
	private Group argumentEditorGroup;



public static class CallFunctionViewModel {
	
	
	private String selectedLib;
	
	
	private String selectedFunction;
		
		private List<String> allLibs;
		private List<String> allFunctions;		
		

		
		
		public CallFunctionViewModel() {
			propertyChangeSupport = new PropertyChangeSupport(this);
		}
		

		public String getSelectedLib() {
			return selectedLib;
		}

		public void setSelectedLib(String selectedLib) {
			propertyChangeSupport.firePropertyChange("selectedLib", this.selectedLib, this.selectedLib = selectedLib);
			
		}

		public String getSelectedFunction() {
			return selectedFunction;
		}

		public void setSelectedFunction(String selectedFunction) {
			propertyChangeSupport.firePropertyChange("selectedFunction", this.selectedFunction,
					this.selectedFunction = selectedFunction);
			
		}


		private PropertyChangeSupport propertyChangeSupport;
		
		
		public List<String> getAllFunctions() {
			return allFunctions;
		}
		
		public List<String> getAllLibs() {
			return allLibs;
		}





		public void setAllLibs(List<String> allLibs) {
			propertyChangeSupport.firePropertyChange("allLibs", this.allLibs,
					this.allLibs = allLibs);
			
		}


		public void setAllFunctions(List<String> allFunctions) {
			propertyChangeSupport.firePropertyChange("allFunctions", this.allFunctions,
					this.allFunctions = allFunctions);
			
		}


	





	
		
		public void addPropertyChangeListener(String propertyName,
			      PropertyChangeListener listener) {
			    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
			  }

			  public void removePropertyChangeListener(PropertyChangeListener listener) {
			    propertyChangeSupport.removePropertyChangeListener(listener);
			  }

		
		
	}
	
	

	

	public CallFunctionView(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent,  style,controller,truLibrary);
	
		
		// color for the viewer
		color=SWT.COLOR_DARK_MAGENTA ;
		
		callfunctmodel = new CallFunctionViewModel();
		this.setLayout(new GridLayout(2, false));
		
		Label LibraryNameLabel = new Label(this, SWT.NONE);
		LibraryNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		LibraryNameLabel.setText("Library Name:");
		
		libcombo = new Combo(this, SWT.NONE);
		libcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label functionnameLabel = new Label(this, SWT.NONE);
		functionnameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		functionnameLabel.setText("Function name:");
		
		funcombo = new Combo(this, SWT.NONE);
		funcombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		argumentEditorGroup = new Group(this, SWT.NONE);
		argumentEditorGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		argumentEditorGroup.setText("Arguments");
		//argumentEditorGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		argumentEditorGroup.setLayout(new FillLayout());
		
		
		
		
		m_bindingContext = initDataBindings();
		
		callfunctmodel.addPropertyChangeListener("selectedLib", this);
		callfunctmodel.addPropertyChangeListener("selectedFunction", this);
		
		
	}





	




	@Override
	public void populate(Step mo) throws TcXmlException {
		super.populate(mo);
		
		ArrayList<String> li = new ArrayList<String>();
		
		
		
		
		
		
		callfunctmodel.setSelectedFunction(mo.getFuncName());
		callfunctmodel.setSelectedLib(mo.getLibName());
		

		li.addAll(controller.getLibraries().keySet());
		callfunctmodel.setAllLibs(li);
		
		updateFunctionList(mo.getLibName());
		
	
		
			
		
		
		
		
	}










	private void updateFunctionList(String libName) {
		try {
			List<String> listname = controller.getFunctionsNameForLib(model.getLibName());
			
			callfunctmodel.setAllFunctions(listname);
			
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("fail to load functions for library" + model.getLibName() , e);
		
		}
		
	}










	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		
		PlayingContext ret = ctx;
		
		

		
		List<CallFunctionAttribut> listArguments = ((CallFunctionArg)theArgument).getCallArguments();
		
		String name = "Call function " +model.getLibName() + "." +  model.getFuncName();
		ExecutionContext ec = new ExecutionContext(name  ,listArguments,controller.buildInitialJavascriptContext()); //ctx.getCurrentExecutionContext().getJsContext()
		//create new execution context for call
		
		ScriptContext  jsctx = controller.buildCallFunctionContext(ec);
		
		
		
		ret.pushContext(ec);
		
		
	TcViewer tcviewer = TcXmlPluginController.getInstance().getTcviewer();	
	
	

	
	
	StepViewer funcViewer = tcviewer.getLibraryViewer().getFunction(model.getLibName(), model.getFuncName());
	
	PlayingJob j = funcViewer.getplayInteractiveJob(ret);
	
	
	
	j.schedule();	
	
	try {
		j.join();
		
		IStatus lastExecStatus = j.getResult() ;
		
		
		
		if(lastExecStatus != Status.OK_STATUS) {
			
			throw new TcXmlException("error in child step", new IllegalStateException());	
		}
		
		ret = j.getCtx();
		return ret;
		
	} catch (InterruptedException e) {
		throw new TcXmlException("Interupted", e);
		
	}
	
	finally {
		ctx.popContext();
	}
	

	}










	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	switch (evt.getPropertyName() ) {
	case "selectedLib" : updateFunctionList(evt.getNewValue().toString());break;
	case "selectedFunction" :updateArgument (evt.getNewValue().toString(), callfunctmodel.getSelectedLib());break;
	
	
	}
		
	}
	private void updateArgument(String functName, String libname) {
		
		
		StepArgument ar;
		try {
			ar = ArgumentViewFactory.getArgumentForFUnction(functName, this);
			setArgumentView(ar);
			controller.getLog().info("setting nw argument for function : " + functName);
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("fail to create argument view for step", e);

		}
		
		
	
		
	}




	private void setArgumentView(StepArgument ar) {
		// remove oldone if necessary
		if (theArgument != null) {
			theArgument.dispose();

		}
		ar.setParent(argumentEditorGroup);
		argumentEditorGroup.layout();
		setTheArgument(ar);

		



	}
	
	






	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableList itemsLibcomboObserveWidget = WidgetProperties.items().observe(libcombo);
		IObservableList allLibsCallfunctmodelObserveList = BeanProperties.list("allLibs").observe(callfunctmodel);
		bindingContext.bindList(itemsLibcomboObserveWidget, allLibsCallfunctmodelObserveList, null, null);
		//
		IObservableList itemsFuncomboObserveWidget = WidgetProperties.items().observe(funcombo);
		IObservableList allFunctionsCallfunctmodelObserveList = BeanProperties.list("allFunctions").observe(callfunctmodel);
		bindingContext.bindList(itemsFuncomboObserveWidget, allFunctionsCallfunctmodelObserveList, null, null);
		//
		IObservableValue observeSelectionLibcomboObserveWidget = WidgetProperties.selection().observe(libcombo);
		IObservableValue selectedLibCallfunctmodelObserveValue = BeanProperties.value("selectedLib").observe(callfunctmodel);
		bindingContext.bindValue(observeSelectionLibcomboObserveWidget, selectedLibCallfunctmodelObserveValue, null, null);
		//
		IObservableValue observeSelectionFuncomboObserveWidget = WidgetProperties.selection().observe(funcombo);
		IObservableValue selectedFunctionCallfunctmodelObserveValue = BeanProperties.value("selectedFunction").observe(callfunctmodel);
		bindingContext.bindValue(observeSelectionFuncomboObserveWidget, selectedFunctionCallfunctmodelObserveValue, null, null);
		//
		return bindingContext;
	}










	@Override
	public String buildTitle() {
		String ret = formatTitle(model.getIndex(), " Call Function " + model.getLibName() + "." + model.getFuncName()) ;
		return ret;
	}










	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		Vector<String> liparam = new Vector<String>();
		String[] tab;
		StringBuffer sb = new StringBuffer("// ").append(buildTitle());
		pw.println(sb.toString());
		StringBuffer sb2 = new StringBuffer();
		sb2.append(callfunctmodel.selectedLib).append(".").append(callfunctmodel.selectedFunction).append("(");
		//add param of function call 
		CallFunctionArg temp = (CallFunctionArg)theArgument;
		List<CallFunctionAttribut> li = temp.getCallArguments() ;
		

		
		sb2.append(controller.generateJsFunctArgument(li));
		sb2.append(");");
		
		pw.println(sb2.toString());	
	}






















}
