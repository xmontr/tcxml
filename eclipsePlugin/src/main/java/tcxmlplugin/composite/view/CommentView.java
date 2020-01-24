package tcxmlplugin.composite.view;

import org.eclipse.swt.widgets.Composite;

import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.composite.StepView;
import tcxml.model.AbstractModel;
import tcxml.model.ArgModel;
import tcxml.model.CommentModel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.CommentWrapper;
import stepWrapper.ForWrapper;

import static org.hamcrest.Matchers.nullValue;

import java.io.PrintWriter;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class CommentView extends StepView{
	private DataBindingContext m_bindingContext;

	private CommentModel commentModel;
	private Text commentText;

	public CommentView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpComment = new Group(this, SWT.NONE);
		grpComment.setText("Comment");
		grpComment.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		commentText = new Text(grpComment, SWT.BORDER);
		commentModel = new CommentModel();
		m_bindingContext = initDataBindings();
	}



	@Override
	public PlayingContext doplay(PlayingContext ctx) throws TcXmlException {
		
		saveModel();
		// nothing to do for a comment
		return ctx;
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextCommentTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(commentText);
		IObservableValue commentCommentModelObserveValue = BeanProperties.value("comment").observe(commentModel);
		bindingContext.bindValue(observeTextCommentTextObserveWidget, commentCommentModelObserveValue, null, null);
		//
		return bindingContext;
	}
	
	
	
	@Override
	public void populate(AbstractStepWrapper stepWrapper2) throws TcXmlException {
		if(! (stepWrapper2 instanceof CommentWrapper )) {
			throw new TcXmlException("comment  view can only be populated by from a comment wrapper ", new IllegalArgumentException());
			
		}		
		CommentWrapper commentwrapper = (CommentWrapper)stepWrapper2 ;		
		
		commentModel.setComment(commentwrapper.getComment());
	}

	@Override
	public void export(PrintWriter pw) throws TcXmlException {
		saveModel();
stepWrapper.export(pw);
		
	}
	
	@Override
	public void saveModel() {
		
		CommentWrapper thecomment = (CommentWrapper) stepWrapper ;
		thecomment.saveComment(commentModel.getComment());
		
	}


	
	
	
	
	
}
