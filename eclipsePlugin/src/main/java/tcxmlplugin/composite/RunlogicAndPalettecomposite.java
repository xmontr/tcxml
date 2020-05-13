package tcxmlplugin.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;

public class RunlogicAndPalettecomposite extends Composite {
	private Composite runlogicview;

	public RunlogicAndPalettecomposite(Composite parent, int style) {
		
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		runlogicview = new Composite(this, SWT.NONE);
		runlogicview.setLayout(new FillLayout());
		runlogicview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		LogicDesignPalette logicDesignPalette = new LogicDesignPalette(this, SWT.NONE);
		logicDesignPalette.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
	}

	public Composite getRunlogicview() {
		return runlogicview;
	}

}
