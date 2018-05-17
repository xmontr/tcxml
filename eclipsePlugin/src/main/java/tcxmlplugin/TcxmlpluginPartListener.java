package tcxmlplugin;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import tcxmlplugin.editor.TcXmlEditor;

public class TcxmlpluginPartListener implements IPartListener {

	@Override
	public void partActivated(IWorkbenchPart part) {
		System.out.println( "partActivated " + part.getTitle());

	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		System.out.println( "partBroughtToTop " + part.getTitle());

	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		
		if( part instanceof TcXmlEditor ) {
			
		TcXmlEditor ed = (TcXmlEditor)	part;
		ed.closeBrowser();
			
		}
		TcXmlPluginController.getInstance().info("closing browser associated to editor");

	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		

	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	

	}

}
