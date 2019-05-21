package tcxmlplugin.composite.view.arguments;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlException;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import tcxmlplugin.composite.view.TextInputView;
import tcxml.model.ArgModel;
import tcxml.model.SetArgModel;

public class SetArgument  extends StepArgument{
	private TextInputView pathInput;
	
	SetArgModel model;

	public SetArgument(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		model = new SetArgModel();
		
		Label lblPath = new Label(this, SWT.NONE);
		lblPath.setText("Path");
		
		pathInput = new TextInputView(this, SWT.NONE);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(argu);	
		pathInput.SetArgModel(argu.get("Path"));
	}

}


