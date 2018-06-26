package tcxmlplugin.composite.parameter;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.parameter.StepParameter;

public class ParamEditorFactory {
	
	
	public static ParameterEditor  createParamEditor (Composite editorArea, StepParameter param) {
		
		ParameterEditor ret = new NotImplementedEditor(editorArea);	 
		
		switch(param.getParameterType()) {
		
		case CURRENTITERATION : break;
		case RANDOM : break;
		case USERID : break;
		case TABLE : break;
		case UNIQUE: break;
		default :ret = new NotImplementedEditor(editorArea);
			
		
		
		
		
		}
		
		
	return ret;	
	}
	
	
	


}
