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

	public CommentView(Composite parent, int style, TcXmlController controller,TruLibrary truLibrary) {
		super(parent, style, controller,truLibrary);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpComment = new Group(this, SWT.NONE);
		grpComment.setText("Comment");
		grpComment.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		commentText = new Text(grpComment, SWT.BORDER);
		commentModel = new CommentModel();
		m_bindingContext = initDataBindings();
	}

	@Override
	public String buildTitle() throws TcXmlException {
		StringBuffer sb =new StringBuffer();
		String txt = model.getComment();
		if(txt.length() > 10) {
			sb.append(txt.substring(0, 10));
			sb.append(" ... ");
			
			
		}else {
			sb.append(txt);
			
		}
		
		
	
		return sb.toString();
	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {
		// TODO Auto-generated method stub
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
	public void populate(Step mo) throws TcXmlException {
		// TODO Auto-generated method stub
		super.populate(mo);
		commentModel.setComment(mo.getComment());
	}

	@Override
	public void eexport(PrintWriter pw) throws TcXmlException {
		StringBuffer sb = new StringBuffer("// code for ").append(buildTitle());
		pw.println(sb.toString());
		StringBuffer sb2 = new StringBuffer("//").append(commentModel.getComment());
		pw.println(sb2.toString());
		
	}


	
	
	
	
	
}
