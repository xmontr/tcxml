package tcxml.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.prefs.BackingStoreException;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import util.WinRegistry;

public class RegistryTest {
	
	//HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome\ExtensionInstallBlacklist
	
	
	@Test
	public void testPolicyThatPreventChromeFromWorking() {
		
		
boolean ex = false;
try {
	ex = WinRegistry.keyExist("HKEY_LOCAL_MACHINE");
} catch (BackingStoreException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
	assertThat(ex,is( true));
	
		
	}

}
