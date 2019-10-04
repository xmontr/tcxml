package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class VideoRecorderComposite extends Composite {
	public Button getCheckButton() {
		return checkButton;
	}

	private Text text;
	private Button checkButton;
	private String videoName ;

	public VideoRecorderComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		checkButton = new Button(this, SWT.CHECK);
		checkButton.setText("record video");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	
	}
	
	
	public String getVideoFilename() {
		String newname = text.getText();
		if( newname.isEmpty() || newname==null)
			return videoName ;
		else
			return newname ;
		
		
		
	}
	
	
	public void setDefaultVideoName( String videoname){
		this.videoName = videoname ;
		text.setText(videoname);
		
		
	}
	

}
