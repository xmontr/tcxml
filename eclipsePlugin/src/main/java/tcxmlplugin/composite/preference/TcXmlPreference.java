package tcxmlplugin.composite.preference;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tcxmlplugin.Activator;

public class TcXmlPreference extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	
	
	
	
	// preference name for path to firefox selenium driver
	public static final String PATH2FIREFOX = "tcxml.pffpath";
	private StringFieldEditor firefoxPathField;


	
	//preference for the path to chrome selenium driver 
	public static final String PATH2CHROME = "tcxml.sdkpath"; 
	private StringFieldEditor chromePathField;
	
	
	//preference for the path FFMPEG exe
	public static final String PATH2FFMPEG = "tcxml.ffmpegpath"; 
	private StringFieldEditor ffmpegPathField;
	
	
	
	
	public TcXmlPreference() {
		
		 setPreferenceStore(

				 Activator.getDefault().getPreferenceStore()); 
		 setDescription("TCXML configuration");
		
		
	}
	
	
	
	@Override
	protected void createFieldEditors() {
		firefoxPathField = new StringFieldEditor(PATH2FIREFOX, "Path to firefox selenium driver", getFieldEditorParent());

		chromePathField =  new StringFieldEditor(PATH2CHROME, "Path to chrome selenium driver", getFieldEditorParent());
		
		ffmpegPathField = new StringFieldEditor(PATH2FFMPEG, "Path to ffmpeg executable", getFieldEditorParent());
		
		firefoxPathField.setEmptyStringAllowed(false);
	
		chromePathField.setEmptyStringAllowed(false);
		
		ffmpegPathField.setEmptyStringAllowed(false);
		
		firefoxPathField.setErrorMessage("firefox selenium path cannot be empty");
	
		chromePathField.setErrorMessage("Chrome selenium path cannot be empty");
		
		ffmpegPathField.setErrorMessage("ffmpeg executable path cannot be empty");
		
		addField(firefoxPathField);
	
		addField(chromePathField);
		
		addField(ffmpegPathField);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public boolean performOk() {
		firefoxPathField.store();
		
		chromePathField.store();
		ffmpegPathField.store();
		
		return true;
	}
	
	
	@Override
	protected void checkState() {
		if(isValid()){
			
			setValid(true);
		}else {
			
	setValid(false);		
			
		}
	}
	
	
	@Override
	public boolean isValid() {
		
boolean ret =  firefoxPathField.isValid() && chromePathField.isValid();


return ret;
	}
	
	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) { super.propertyChange(event);

	if (event.getProperty().equals(FieldEditor.VALUE)) { if (event.getSource() == firefoxPathField 
														||  event.getSource() == chromePathField
														
			
			) checkState();

	}

	}
	

}
