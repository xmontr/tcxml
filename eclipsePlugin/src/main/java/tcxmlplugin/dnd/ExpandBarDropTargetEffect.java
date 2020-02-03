package tcxmlplugin.dnd;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

public class ExpandBarDropTargetEffect extends  DropTargetEffect  {
	
	

	
	

	private ExpandItem tempExpandItem;
	private int lastIndex = -1;


	public ExpandBarDropTargetEffect(Control control) {
		super(control);
	
		if (! (control instanceof ExpandBar )) {
			
		throw new IllegalArgumentException("ExpandBarDropTargetEffect   apply only on expandbar")	;
		}
		
		
	
		
		
		
		
	

	}
	
	



	private ExpandBar getBar () {
		
		return (ExpandBar) getControl();
		
	}
	

	
	

	
	@Override
	public void dragOver(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragOver(event);
		
		Widget cu = getItem(event.x, event.y) ;
		
		if(cu != null  ) {	
			ExpandItem newtarget = (ExpandItem)cu ;
			
			if(this.lastIndex == -1) {
				//first time should move
				moveTempBefore(newtarget);
				
			}else {
				int newindex = getIndexOfItem(newtarget);
				
				if( newindex != this.lastIndex  ) {				
			
					moveTempBefore(newtarget);
					
				}
				
				
			}
				
				
		
			
			
		}
		
	
	}
	
	
	private int getIndexOfItem( ExpandItem target ) {
		int theindex = 0;
		ExpandItem[] allitems =getBar().getItems();
		for (int i = 0; i < allitems.length; i++) { // find the index of the item in lis
			ExpandItem current = allitems[i];
			if(current == target) {
				theindex = i ;break;
				
				
			}
			
		}
		
	return theindex ;	
		
		
	}
	

	
	
	
	
	
	
	
	private void moveTempBefore( ExpandItem target) {

		
		int theindex = getIndexOfItem(target);
		this.lastIndex = theindex ;
// move old
		String txt = tempExpandItem.getText();
tempExpandItem.dispose();
// re insert at correct index
ExpandItem nitem = new ExpandItem(getBar(), getBar().getStyle(), theindex);	
Composite c = new Composite(getBar(), getBar().getStyle());
c.setLayout(new FillLayout());
nitem.setControl(c);
nitem.setText(txt);
tempExpandItem = nitem ;

System.out.println("moved");
	}
	





	@Override
	public void dragEnter(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragEnter(event);
		String txt = " --------   drop to insert here  -----------------------";
		addNewTemporaryItem(txt);
	
		
	}
	
	
	
	
	private void addNewTemporaryItem(String txt) {
		if(tempExpandItem == null) {
			 tempExpandItem= new ExpandItem(getBar(), getBar().getStyle());
			 Composite c = new Composite(getBar(), getBar().getStyle());
			 c.setLayout(new FillLayout());
			 tempExpandItem.setControl(c);
			 tempExpandItem.setText(txt);
			
			 
			 
		
			
			
		}

	 
		
	}











	@Override
	public void dragLeave(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragLeave(event);
		removeTemporaryItem() ; 
	
	}





	private void removeTemporaryItem() {
		if(tempExpandItem != null) {
			tempExpandItem.dispose();
			tempExpandItem=null;
			
			
		}
	;
		
	}
	
	
	@Override
	public Widget getItem(int x, int y) {
		ExpandItem ret = null;
		
		Point coordinates = new Point(x, y);
		coordinates = getBar().toControl(coordinates);
		ExpandItem[] allitems = getBar().getItems();
		for (ExpandItem expandItem : allitems) {
			Rectangle loc = expandItem.getControl().getBounds();
			
			Rectangle headerRec = new Rectangle(loc.x, loc.y -expandItem.getHeaderHeight() , loc.width , expandItem.getHeaderHeight()) ;
			
			
			if( headerRec.contains(coordinates)) {
				ret = expandItem ;
				
			}			
		}	
		
		return ret;
	}
	
	
	
	





}
