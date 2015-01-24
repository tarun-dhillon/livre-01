package uk.cbooksys.client.presenter;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.views.NavBarView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomePresenter implements Presenter {

	public interface Display {
		Panel getHeader();
		Panel getLeft();
		Panel getContent();
		Panel getFooter();
		DockLayoutPanel getDockPanel();
		
		Widget asWidget();
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public HomePresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	

	public void bind() {
	
	}

	@Override
	public void go(Container container) {
		bind();
		
	}

}
