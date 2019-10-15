package tcxmlplugin.jfaceprovider;

import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.kscs.util.jaxb.BoundList;

import tcxml.model.Step;
public class StepTreeContentProvider implements ITreeContentProvider  {
	
	
	
	private Step root ;

	public StepTreeContentProvider(Step root) {
		super();
		this.root = root;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Step[] ret = this.root.getStep().toArray(new Step[root.getStep().size()]);
		return ret;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if( parentElement instanceof  Step) {
			
			Step testep = (Step)parentElement ;
			Step[] ret = testep.getStep().toArray(new Step[testep.getStep().size()]);
			return replaceAlternativeStep(ret);
			
		} else {
			
			return null ;
		}
		
	}
	
	/***
	 *  aletrnative step are not shown in the tree so replace alternativestep by the step alternative.
	 * 
	 * 
	 * @param src
	 * @return
	 */
	private Step[] replaceAlternativeStep( Step[] src) {
		ArrayList<Step> ret = new ArrayList<Step>() ;
		for (Step step : src) {
			if( step.getType().equals("alternative")) {
				int index = Integer.parseInt(step.getActiveStep()) ;	
				Step altstep = step.getStep().get(index);	
				altstep.setIndex(step.getIndex());	
				ret.add(altstep);
			}else {
			ret.add(step);	
				
			}
			
		}
		
		
	
	
	
	return ret.toArray(new Step[ret.size()]) ; 
		
	}
	

	

	
	

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
	boolean ret = false;
	if( element instanceof  Step) {
		
		Step testep = (Step)element ;
		int size = testep.getStep().size() ;
		if(size > 0) ret = true;		
	}
	
		return ret;
	}

}
