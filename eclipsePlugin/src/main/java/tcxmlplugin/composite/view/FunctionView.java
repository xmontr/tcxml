package tcxmlplugin.composite.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.kscs.util.jaxb.BoundList;

import model.Function;
import tcxml.core.PlayingContext;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.model.Step;
import tcxml.model.TruLibrary;
import tcxmlplugin.TcXmlPluginController;
import tcxmlplugin.composite.StepView;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxmlplugin.composite.stepViewer.StepContainer;
import tcxmlplugin.composite.stepViewer.StepViewerFactory;
import tcxmlplugin.job.PlayingJob;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class FunctionView extends StepView implements StepContainer {
	private ExpandBar bar;

	private TruLibrary Library;

	private Function function;

	private List<StepViewer> stepViwerChildren;

	private String libName;

	public String getLibName() {
		return libName;
	}

	public void setLibName(String libName) {
		this.libName = libName;
	}

	public TruLibrary getLibrary() {
		return Library;
	}

	public void setLibrary(TruLibrary library) {
		Library = library;
	}

	public FunctionView(Composite parent, int style, TcXmlController controller) {

		super(parent, style, controller);
		GridLayout gridlayout = new GridLayout(1, false);
		setLayout(gridlayout);
		stepViwerChildren = new ArrayList<StepViewer>();
		bar = new ExpandBar(this, SWT.V_SCROLL);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		bar.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		bar.setSpacing(10);

	}

	public void addStep(Step step) throws TcXmlException {

		StepViewer tv = StepViewerFactory.getViewer(step, this, controller);

		ExpandItem xpndtmNewExpanditem = new ExpandItem(bar, SWT.NONE);

		xpndtmNewExpanditem.setExpanded(false);
		xpndtmNewExpanditem.setText(tv.getTitle());

		xpndtmNewExpanditem.setHeight(tv.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		xpndtmNewExpanditem.setControl(tv);
		tv.setParentExpandItem(xpndtmNewExpanditem);

		stepViwerChildren.add(tv);

	}

	@Override
	public ExpandBar getBar() {
		// TODO Auto-generated method stub
		return bar;
	}

	@Override

	public void clean() {
		ExpandItem[] li = bar.getItems();
		for (ExpandItem expandItem : li) {
			Control innercontrol = expandItem.getControl();
			if (innercontrol instanceof StepContainer) {

				expandItem.dispose();
				((StepContainer) innercontrol).clean();

			}

			else {
				innercontrol.dispose();
				expandItem.dispose();
			}

		}
		bar.redraw();
		stepViwerChildren.clear();
	}

	public void populate(Step mo) throws TcXmlException {
		function = new Function();
		function.setName(mo.getAction());
		function.setId(mo.getStepId());
		function.setArgumentSchema(mo.getArgsSchema());
		function.setIndex(mo.getIndex());
		function.setLevel(mo.getLevel());

		setTitle("Function " + mo.getAction());
		// first step child is internal - skipit
		sanityCheck(mo);
		super.populate(mo);
		BoundList<Step> li = mo.getStep().get(0).getStep();
		for (Step step : li) {
			addStep(step);
		}

	}

	public Function getFunction() {
		return function;
	}

	private void sanityCheck(Step mo) throws TcXmlException {
		Step firstchild = mo.getStep().get(0);
		if (!(firstchild.getType().equals("block") && firstchild.getAction().equals("internal"))) {

			throw new TcXmlException("bad structure for function step - expected block internal not found",
					new IllegalStateException());

		}

	}

	@Override
	public PlayingContext play(PlayingContext ctx) throws TcXmlException {

		PlayingContext temp = ctx;

		for (Iterator iterator = stepViwerChildren.iterator(); iterator.hasNext();) {
			StepViewer stepViewer = (StepViewer) iterator.next();

			PlayingJob j = stepViewer.getplayInteractiveJob(temp);
			j.schedule();

			try {
				j.join();
				temp = j.getCtx();

				IStatus lastExecStatus = j.getResult();

				if (lastExecStatus != Status.OK_STATUS) {

					throw new TcXmlException("error in child step", new IllegalStateException());
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return temp;

	}

	@Override
	public List<StepViewer> getChildViewer() {
		// TODO Auto-generated method stub
		return stepViwerChildren;
	}

}
