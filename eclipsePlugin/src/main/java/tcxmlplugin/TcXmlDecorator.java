package tcxmlplugin;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TcXmlDecorator implements ILabelDecorator  {

	@Override
	public Image decorateImage(Image image, Object element) {
		
	
		
		Image ret = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		
	IFolder currentFolder = (IFolder)element;
	if(currentFolder.getName()=="Test Cases") {
	int i =0;
		
	}
	
	if( TcXmlPluginController.getInstance().isTypeFolder(currentFolder , TcXmlPluginController.TESTCASE_ROOT_FOLDER)){
		ret = Activator.getDefault().getImageRegistry().get(Activator.TC_ROOT_FOLDER_IMG) ;
	}
	
	if( TcXmlPluginController.getInstance().isTypeFolder(currentFolder , TcXmlPluginController.TESTCASE_FOLDER)){
		ret = Activator.getDefault().getImageRegistry().get(Activator.TC_FOLDER_IMG) ;
	}
		
		
		return ret;
	}

	@Override
	public String decorateText(String text, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

}