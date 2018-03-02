package util;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class WinRegistry {
	
	//HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome\ExtensionInstallBlacklist

	
	public static boolean keyExist (String key) throws BackingStoreException {
		
		Preferences rootPref = Preferences.systemRoot();
		String[] li = rootPref.keys();
		
		for (String string : li) {
			System.out.println( li);
		}
		
		
		return true;

	}
	
	private static byte[] toCstr(String str) {
	    byte[] result = new byte[str.length() + 1];

	    for (int i = 0; i < str.length(); i++) {
	      result[i] = (byte) str.charAt(i);
	    }
	    result[str.length()] = 0;
	    return result;
	  }
	
	
}
