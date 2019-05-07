package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class WinRegistry {
	
	//HKEY_LOCAL_MACHINE\SOFTWARE\Policies\Google\Chrome\ExtensionInstallBlacklist

	

	
	
	
	public static List<String>  keyExist (String[] loc , String key) throws IOException {
		Runtime p = Runtime.getRuntime();
		List<String> ret = new ArrayList<String>();
		for (String path : loc) {
			StringBuffer command = new StringBuffer("reg  QUERY ").append(path).append(" /s /k   /f ").append(key);
			Process process = p.exec(command.toString() );		
			BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ( (line = output.readLine())  != null ){
				if(line.contains("HKEY_"))
					{ ret.add(line); 	} 
				
			}	
			
			
			
		}
		
		

				
		return ret;

	}
	
public static void dropKey( String key) throws IOException {
	Runtime p = Runtime.getRuntime();
	if( key.contains("\\Policies\\Google\\")  &&  ! key.contains("Group Policy Objects")  ) {
		StringBuffer command = new StringBuffer("reg  DELETE ").append(key).append(" /f " );
		System.out.println(command.toString());
		Process process = p.exec(command.toString() );
		
		
	}
	

	
	
	
}
	
	
}
