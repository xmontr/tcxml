package tcxml.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.util.List;
import java.util.prefs.BackingStoreException;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import util.WinRegistry;

public class RegistryTest {
	
	//HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome\ExtensionInstallBlacklist
	
	// HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome
	
	
	@Test
	public void testPolicyThatPreventChromeFromWorking() {
		
		String[] path = new String[] {"HKLM","HKCU","HKCR","HKCC","HKU"} ;


	try {
		
		List<String> ret = WinRegistry.keyExist(path,"Chrome");
		for (String key  : ret) {
			
		//System.out.println(" found key "+ key);	
		WinRegistry.dropKey(key);
		}
		

		
		
		
		
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	

	
		
	}

}
