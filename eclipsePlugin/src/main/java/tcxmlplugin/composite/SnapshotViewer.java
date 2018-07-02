package tcxmlplugin.composite;

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class SnapshotViewer extends Composite implements SelectionListener {
	
	
	public snapshotViewerMode getMode() {
		return mode;
	}

	public void setMode(snapshotViewerMode mode) {
		this.mode = mode;
		updateGUI();
	}

	private void updateGUI() {
		
		switch ( this.mode) {
		
		case RECORD: maincontainerlayout.topControl = viewRecord; break;
		
		case REPLAY:  maincontainerlayout.topControl = viewReplay; break;
		
		case BOTH :maincontainerlayout.topControl = dualView;   break;
		
		}
		
		layout(true,true);
		
		


		
		
		
		
	}

	private enum snapshotViewerMode {
		RECORD,
		REPLAY,
		BOTH
		
		
	}
	
	
	
	
	private snapshotViewerMode mode;
	private ToolItem recorditem;
	private ToolItem replayitem;
	private ToolItem bothitem;
	private StackLayout maincontainerlayout;
	private Composite viewRecord;
	private Composite viewReplay;
	private Composite dualView;
	

	public SnapshotViewer(Composite parent, int style) {
		super(parent, style);
		buildGUI();
	}

	private void buildGUI() {
		
setLayout(new GridLayout(1, false));
ToolBar toolbar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);





recorditem = new ToolItem(toolbar, SWT.RADIO);

//new ToolItem(toolbar, SWT.SEPARATOR);
recorditem.setText("record");
recorditem.addSelectionListener(this);

replayitem = new ToolItem(toolbar, SWT.RADIO);
replayitem.setText("replay");
replayitem.addSelectionListener(this);


bothitem = new ToolItem(toolbar, SWT.RADIO);
bothitem.setText("record &replay");
bothitem.addSelectionListener(this);



Composite mainContainer = new Composite(this, this.getStyle());
mainContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
maincontainerlayout = new StackLayout();

mainContainer.setLayout(maincontainerlayout);


viewRecord = new SingleViewSnapshot(mainContainer, this.getStyle() , "record");
viewReplay = new SingleViewSnapshot(mainContainer, this.getStyle() , "replay");
dualView = new DualViewSnapshot(mainContainer, this.getStyle());

layout(true,true);
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		boolean selection = ((ToolItem)e.getSource()).getSelection();
		
		if( src == recorditem ) {
			if(selection == true) {
				
			
				setMode(snapshotViewerMode.RECORD);
			
			
			
			
		}
		}
		
		if( src == replayitem ) {
			if(selection == true) {
				
				setMode(snapshotViewerMode.REPLAY);

			
			
			
			
		}
		
		
		
		
	}


		if( src == bothitem ) {
			if(selection == true) {
				
				setMode(snapshotViewerMode.BOTH);

			
			
			
			
		}
		
		
		
		
	}
		



}
		


	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

	}
	
}
