package tcxmlplugin.composite.view.arguments.tc;

import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.view.arguments.StepArgument;
import tcxml.model.ArgModel;
import tcxml.model.TcLogModel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlException;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import tcxmlplugin.composite.view.TextInputView;

public class TlcLogArgView  extends StepArgument {
	
	
	private TcLogModel model ;
	private TextInputView textInputView;

	public TlcLogArgView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblText = new Label(this, SWT.NONE);
		lblText.setText("text");
		
		textInputView = new TextInputView(this, SWT.NONE);
		model = new TcLogModel();
	}
	
	
	@Override
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(argu);
		textInputView.SetArgModel(argu.get("text"));
	}
	
}
