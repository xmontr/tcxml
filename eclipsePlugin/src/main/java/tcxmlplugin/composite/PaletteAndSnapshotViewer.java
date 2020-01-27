package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;

public class PaletteAndSnapshotViewer extends Composite {

	private Composite leftComposite;
	private Composite rightComposite;







	public PaletteAndSnapshotViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		leftComposite = new Composite(this, SWT.NONE);
		leftComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		leftComposite.setLayout(new FillLayout());
		
		rightComposite = new Composite(this, SWT.NONE);
		rightComposite.setLayout(new FillLayout());
		rightComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	public Composite getPalette() {
		
		return leftComposite;
	}
	
	
	public Composite getSnapshot() {
		
		return rightComposite;
	}
	

}
