package uk.cbooksys.client.presenter;

import java.util.List;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.objs.GoogleUser;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.views.MatchView;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
public class DashboardPresenter implements Presenter {

	public interface Display {
		void addBooking(SLOT slot);
		void addBooking(List<SLOT> slotList, USER inUser);
		SingleSelectionModel<SLOT> getSelectionModel();
		void cancelSlot(SLOT slot);
		Widget asWidget();
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	public  Container contrainer;

	public DashboardPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public DashboardPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view, GoogleUser user) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {

		final SingleSelectionModel<SLOT> selectionModel = display.getSelectionModel();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				SLOT selected =selectionModel.getSelectedObject();
				if (selected != null) {
					MatchView matchView = new MatchView(selected);
					MatchPresenter matchPresenter = new MatchPresenter(rpcService, eventBus, matchView);
					matchPresenter.go(null); // since this ia popup doesnt need to be attached to the contrainer.
					String[] panelHead = new String[] { "Update or canel booking ", "typcn-eye-outline" };
					MyPopPanel bookSlotPanel = new MyPopPanel(panelHead[0], panelHead[1]);
					bookSlotPanel.addContent(matchView);
					matchView.setMyPopPanel(bookSlotPanel);
				}
			}
		});
	}

	@Override
	public void go(Container container) {
		this.contrainer = container;
		bind();
		container.left.clear();
		DockLayoutPanel dockLayoutPanel = ((DockLayoutPanel)((FlowPanel)container.left).getParent());
		dockLayoutPanel.setWidgetSize((FlowPanel)container.left, 20);
		dockLayoutPanel.animate(750);
		container.left.add(display.asWidget());
	}

	public void addBooking(SLOT slot) {
		display.addBooking(slot);
	}

	public void addBooking(List<SLOT> slotList, USER inUser) {
		display.addBooking(slotList,inUser);
	}

	public void cancelSlot(SLOT slot) {
		display.cancelSlot(slot);

	}

}
