package uk.cbooksys.client.presenter;

import uk.cbooksys.client.AppController;
import uk.cbooksys.client.Container;
import uk.cbooksys.client.events.Logout;
import uk.cbooksys.client.events.PopLoginEvent;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.shared.AppConfig;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavBarPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getLoginLink();
		HasClickHandlers getLogoutLink();
		HasClickHandlers getSquashLink();
		HasClickHandlers getTennisLink();
		HasClickHandlers getHomeLink();
		HasClickHandlers getAdminLink();
		void markSelected(Anchor link);
		HTMLPanel getActionPanel();

		Widget asWidget();
		void doLogin(boolean login);
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public NavBarPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;

	}

	public void bind() {

		this.display.getLoginLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new PopLoginEvent());
			}
		});

		this.display.getLogoutLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new Logout());
			}
		});

		this.display.getSquashLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				display.markSelected((Anchor)display.getSquashLink());
				History.newItem(AppController.TAG_SQUASH);
			}
		});

		this.display.getTennisLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				display.markSelected((Anchor)display.getTennisLink());
				History.newItem(AppController.TAG_TENNIS);
			}
		});


		this.display.getHomeLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				display.markSelected((Anchor)display.getHomeLink());
				History.newItem("home",true);
			}
		});

		this.display.getAdminLink().addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {
				display.markSelected((Anchor)display.getAdminLink());
				History.newItem("admin",true);
			}
		});


	}



	@Override
	public void go(Container container) {

		bind();
		container.header.clear();
		//		container.header.add(Note.attach());
		//		Note.log("Note is now attached");
		container.header.add(display.asWidget());
		//Show.message("Loading applicaction engine ..");
	}

	public void activateLink(int sportTypeSquash) {
		switch (sportTypeSquash) {
		case AppConfig.SPORT_TYPE_SQUASH:
			display.markSelected((Anchor)display.getSquashLink());
			break;
		case AppConfig.SPORT_TYPE_TENNIS:
			display.markSelected((Anchor)display.getTennisLink());
			break;
		}
	}

}
