package tcxmlplugin.composite.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import tcxmlplugin.Activator;

public class PreferenceInitializerTcXml  extends AbstractPreferenceInitializer  {

	@Override
	public void initializeDefaultPreferences() {
		 IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		 
		 store.setDefault(TcXmlPreference.PATH2FIREFOX, "C:\\bin\\seleniumDriver\\chrome_version_87\\chromedriver.exe");
		 store.setDefault(TcXmlPreference.PATH2CHROME, "C:\\bin\\seleniumDriver\\chrome_version_87\\chromedriver.exe");
		 
		 store.setDefault(TcXmlPreference.PATH2FFMPEG, "C:\\bin\\ffmpeg\\ffmpeg-latest-win64-static\\bin\\ffmpeg.exe");
	
}
		
	}


