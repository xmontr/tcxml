package tcxmlplugin.composite.view.arguments.vts;

import org.eclipse.swt.widgets.Composite;

import tcxmlplugin.composite.view.arguments.StepArgument;
import tcxml.model.ArgModel;
import tcxml.model.VtcConnectArgModel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import tcxml.core.TcXmlException;

import java.util.HashMap;

import javax.json.JsonObject;

import org.eclipse.swt.SWT;
import tcxmlplugin.composite.view.TextInputView;

public class VtcConnectArgView extends StepArgument{

	private VtcConnectArgModel model;
	private TextInputView serverInput;
	private TextInputView porttInput;
	private TextInputView nameInput;
	private TextInputView variableInput;

	public VtcConnectArgView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		model = new VtcConnectArgModel();
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("Server");
		
		serverInput = new TextInputView(this, SWT.NONE);
		
		Label lblPort = new Label(this, SWT.NONE);
		lblPort.setText("port");
		
		porttInput = new TextInputView(this, SWT.NONE);
		
		Label lblVtsName = new Label(this, SWT.NONE);
		lblVtsName.setText("vts name");
		
		nameInput = new TextInputView(this, SWT.NONE);
		
		Label lblVariable = new Label(this, SWT.NONE);
		lblVariable.setText("variable");
		
		variableInput = new TextInputView(this, SWT.NONE);
	
	}
	
	@Override
	public void populate(HashMap<String, ArgModel> argu) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(argu);

		
		nameInput.SetArgModel(argu.get("serverName"));
		
		porttInput.SetArgModel(argu.get("port"));
		
		variableInput.SetArgModel(argu.get("Variable"));
		;
		serverInput.SetArgModel(argu.get("vtsName"));
		
		
		
		
	}

}
