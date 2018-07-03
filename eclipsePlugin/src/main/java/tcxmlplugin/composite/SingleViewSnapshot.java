package tcxmlplugin.composite;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.text.AbstractDocument.Content;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Canvas;

public class SingleViewSnapshot extends Composite {
	
	
	
	private String title;
	private Group maingroup;
	private ScrolledComposite scroller;
	private Canvas canvas;
	
	private Image currentImage;
	

	public SingleViewSnapshot(Composite parent, int style) {
		super(parent, style);

		

	buildGui();
	}

	/**
	 * 
	 */
	private void buildGui() {
		setLayout(new FillLayout());
		maingroup = new Group(this, SWT.NONE);
		maingroup.setLayout(new FillLayout());
		scroller = new ScrolledComposite(maingroup, SWT.V_SCROLL | SWT.H_SCROLL);
		scroller.setExpandVertical(true);
		scroller.setExpandHorizontal(true);
		scroller.setAlwaysShowScrollBars(true);
		
/*		scroller.addListener( SWT.Resize, event -> {
			  int width = scroller.getClientArea().width;
			  int height = scroller.getClientArea().height;
			  scroller.setMinWidth(width);
			  scroller.setMinHeight(height);		 
			 		 scroller.layout();
			} );*/
		
		maingroup.setText(title);

		
		
		canvas = new Canvas(scroller, SWT.NONE);
		scroller.setContent(canvas);
		
		
		showsampleimage();
		
	}

	public SingleViewSnapshot(Composite parent, int style, String title) {
		super(parent, style);
		
		this.title=title;
		buildGui();
	}
	
	private Image getSampleImage() throws FileNotFoundException {
		
	File f = new File("H:/vugenworkspace/SAMPLE_ECASL/snapshots/yasaqqn.png");
		
		FileInputStream in = new FileInputStream(f);
		Image img = new Image(getDisplay(), in);
		return img;
		
		
		
		
	}
	
	
	private void  showsampleimage() {
		
		try {
			showImage(getSampleImage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	private void  showImage( Image img) {
		if(this.currentImage != null) {
			this.currentImage.dispose();
			
		}
		
	currentImage = img ;
		

		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(img, 0, 0);
				
				
			}
		});
		
		
		canvas.redraw();
		Point newsize =new Point( img.getBounds().width , img.getBounds().height);				
				canvas.setSize(newsize);
				scroller.setMinSize(newsize);
				scroller.layout(true, true);

		
		

		
	
		
		
	}

}
