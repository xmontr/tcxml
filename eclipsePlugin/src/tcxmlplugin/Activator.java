package tcxmlplugin;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "tcxmlplugin"; //$NON-NLS-1$
	
	public static final String FF_IMG = "firefox_16x16.png";
	
	static final String TC_ROOT_FOLDER_IMG = "tc_folder.png";
	
	static final String TC_FOLDER_IMG = "tc.png";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	
	
	public void log ( String message, int severity, Throwable ex){
		IStatus status = new Status(severity, Activator.PLUGIN_ID, message,ex);
		getLog().log(status );
		
		
	}
	
	
	private void addImage(String connectionImg, ImageRegistry registry, Bundle bundle) {
		
		  IPath path = new Path("icons/"+connectionImg);
	        URL url = FileLocator.find(bundle, path, null);
	        ImageDescriptor desc = ImageDescriptor.createFromURL(url);
	        registry.put(connectionImg, desc);
		
	}
	
	
	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		addImage(FF_IMG,registry,bundle);
		addImage(TC_ROOT_FOLDER_IMG,registry,bundle);
		addImage(TC_FOLDER_IMG,registry,bundle);
		
    
	}
	
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
