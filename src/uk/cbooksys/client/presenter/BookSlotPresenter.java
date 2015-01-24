package uk.cbooksys.client.presenter;

import java.util.List;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.events.BookSlotEvent;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class BookSlotPresenter implements Presenter {

	public interface Display {
		HasClickHandlers bookSlot();
		SLOT getSlot();
		MyPopPanel getMyPopPanel();
		Widget asWidget();
		void updateUsers(List<USER> userList);
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public BookSlotPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;


	}

	public void bind() {






		display.bookSlot().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getMyPopPanel().hide();				
				rpcService.book(display.getSlot(),true, new AsyncCallback<SLOT>() {

					@Override
					public void onSuccess(SLOT slot) {
						Show.message("Booking confimred ! ");
						eventBus.fireEvent(new BookSlotEvent(slot));

					}

					@Override
					public void onFailure(Throwable caught) {
						Show.message("Something went wrong with the booking !");

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
