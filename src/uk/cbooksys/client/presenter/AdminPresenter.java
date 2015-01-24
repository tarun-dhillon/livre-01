package uk.cbooksys.client.presenter;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.USER;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class AdminPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getUserExecute();
		HasClickHandlers getSlotExecute();

		int getNoUsers();
		boolean cleanUsers();

		int getNoSlots();
		boolean cleanSlots();

		Widget asWidget();
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public AdminPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {
		display.getUserExecute().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// 1. create dummy users 
				// 2. call the RPC method
				rpcService.makeUsers(USER.makeDumList(), display.cleanUsers(), new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						Window.alert(result);

					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.toString());
					}
				});
			}
		});

		display.getSlotExecute().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Show.message("Creating "+display.getNoSlots()+" slots");
				rpcService.createDumSlots(display.getNoSlots(),display.cleanSlots(), "Squash",new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						Show.message(result);
					}
					@Override
					public void onFailure(Throwable caught) {
						Show.message("Error while creating slots");
						GWT.log(caught.toString());
					}
				});
			}
		});

	}

	@Override
	public void go(Container container) {
		bind();
		container.center.clear();
		container.center.add(display.asWidget());
	}

}
