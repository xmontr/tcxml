package tcxmlplugin.composite;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;

public class SingleViewSnapshot extends Composite {
	
	
	
	private String title;
	private Group maingroup;
	private ScrolledComposite scrolledComposite;

	public SingleViewSnapshot(Composite parent, int style) {
		super(parent, style);

		

	buildGui();
	}

	private void buildGui() {
		setLayout(new FillLayout());
		maingroup = new Group(this, SWT.NONE);
		maingroup.setLayout(new FillLayout());
		scrolledComposite = new ScrolledComposite(maingroup, getStyle());
		maingroup.setText(title);
		
	}

	public SingleViewSnapshot(Composite parent, int style, String title) {
		super(parent, style);
		
		this.title=title;
		buildGui();
	}

}
