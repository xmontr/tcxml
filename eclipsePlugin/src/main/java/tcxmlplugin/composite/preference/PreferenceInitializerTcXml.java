package tcxmlplugin.composite.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import tcxmlplugin.Activator;

public class PreferenceInitializerTcXml  extends AbstractPreferenceInitializer  {

	@Override
	public void initializeDefaultPreferences() {
		 IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		 
		 store.setDefault(TcXmlPreference.PATH2FIREFOX, "C:\\PROGRA~2\\MOZILL~1\\firefox.exe");
		 store.setDefault(TcXmlPreference.PATH2FIREFOX, "C:\\bin\\mozilla-SDK\\newsdkversion\\addon-sdk-master\\lib");
	
}
		
	}


