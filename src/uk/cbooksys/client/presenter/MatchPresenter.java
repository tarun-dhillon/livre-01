package uk.cbooksys.client.presenter;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.events.SlotCancelledEvent;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class MatchPresenter implements Presenter {

	public interface Display {
		HasClickHandlers cancelBooking();
		SLOT getSLOT();
		MyPopPanel getMyPopPanel(); 
		Widget asWidget();
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public MatchPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}


	public void bind() {
		display.cancelBooking().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.getMyPopPanel().hide();
				rpcService.delete(display.getSLOT(), new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						Show.message("Book can been cancelled");
						SLOT slot = display.getSLOT();
						slot.setCourtStatus(SLOT.AVAILABLE);
						eventBus.fireEvent(new SlotCancelledEvent(slot));
					}

					@Override
					public void onFailure(Throwable caught) {
						Show.message("Something went wrong..Booking not cancelled");
					}
				});
			}
		});
	}

	@Override
	public void go(Container container) {
		bind();

	}

}
