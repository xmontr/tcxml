package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;

public class DualViewSnapshot extends Composite {

	private SashForm sf;
	private SingleViewSnapshot viewRecord;
	private SingleViewSnapshot viewReplay;

	public DualViewSnapshot(Composite parent, int style) {
		super(parent, style);
	
		buildGUI();
	}

	private void buildGUI() {
		setLayout(new FillLayout());
		
		sf = new SashForm(this, SWT.HORIZONTAL);
		viewRecord = new SingleViewSnapshot(sf, this.getStyle() , "record");
		viewReplay = new SingleViewSnapshot(sf, this.getStyle() , "replay");
		
		
		layout(true,true);
		
	}

}
