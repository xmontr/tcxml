package tcxmlplugin.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.TcViewer;


public class TcXmlEditor  extends EditorPart   {

	private TcViewer tcViewer;
	private FileEditorInput fi;

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
	setInput(input);
	setSite(site);
	fi = (FileEditorInput)input;
		
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		tcViewer = new TcViewer(parent, SWT.NONE);
		// inputstrem for the file
		
		 try {
			 InputStream is = fi.getFile().getContents() ;
			tcxml.core.TcXmlController.getInstance().loadXml(is);
		} catch (TcXmlException e) {
		TcXmlPluginController.getInstance().error("error when editor is retriving content of the file", e);
			
		} catch (CoreException e) {
			TcXmlPluginController.getInstance().error("error when editor is parsing the file ", e);
			
		}

	Map<String, Step> actionmap = tcxml.core.TcXmlController.getInstance().getActionMap();
		tcViewer.populateAction( actionmap);
		
	}

	@Override
	public void setFocus() {
		tcViewer.setFocus();
		
	}
	

}