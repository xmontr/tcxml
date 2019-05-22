package tcxml.test;

import static org.junit.Assert.fail;

import java.io.File;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import tcxml.core.TcXmlException;
import util.TcxmlUtils;

public class UtilTest {
	
	
	@Test
	public void testListFilesInUSRfile() {
		
		

		
	
		
		
		try {
			File usrFile = new File( getClass().getClassLoader().getResource("tcAll_esubmission_final.usr").getFile());
			
			String basedir = usrFile.getParentFile().getAbsolutePath() + File.separator ;
			
			
			
			String[] expectedfiles = new String[] {
					basedir+"declarationhonour1.pdf",
					basedir+"declarationhonour2.pdf",
					basedir+"declarationhonour3.pdf",
					basedir+"financialguide1.pdf",
					basedir+"financialguide2.pdf",
					basedir+"financialguide3.pdf",
					basedir+"SignedTenderReport.pdf",
					basedir+"technicalguide1.pdf",
					basedir+"technicalguide2.pdf",
					basedir+"technicalguide3.pdf"
					
					
			};
			
			
			
			List<String> li = TcxmlUtils.listExtraFilesinUsrfiles(usrFile);
			
		assertThat(li.toArray(  new String[li.size()]), arrayContainingInAnyOrder(expectedfiles));	
			
			
			
			
			
			
		} catch (TcXmlException e) {
			fail(" fail to parse usr file ");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
	}

}
