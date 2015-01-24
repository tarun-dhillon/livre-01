package uk.cbooksys.client;

import java.util.List;

import uk.cbooksys.client.events.BookSlotEvent;
import uk.cbooksys.client.events.BookSlotEventHandler;
import uk.cbooksys.client.events.Login;
import uk.cbooksys.client.events.LoginHandler;
import uk.cbooksys.client.events.Logout;
import uk.cbooksys.client.events.LogoutHandler;
import uk.cbooksys.client.events.PopLoginEvent;
import uk.cbooksys.client.events.PopLoginEventHandler;
import uk.cbooksys.client.events.SlotCancelledEvent;
import uk.cbooksys.client.events.SlotCancelledEventHandler;
import uk.cbooksys.client.events.UpdateAllSlotEvent;
import uk.cbooksys.client.events.UpdateAllSlotEventHandler;
import uk.cbooksys.client.presenter.AdminPresenter;
import uk.cbooksys.client.presenter.BookWeekPresenter;
import uk.cbooksys.client.presenter.DashboardPresenter;
import uk.cbooksys.client.presenter.LoginPresenter;
import uk.cbooksys.client.presenter.NavBarPresenter;
import uk.cbooksys.client.presenter.Presenter;
import uk.cbooksys.client.presenter.SlideShowPresenter;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.views.AdminView;
import uk.cbooksys.client.views.BookWeekView;
import uk.cbooksys.client.views.DashboardView;
import uk.cbooksys.client.views.HomeView;
import uk.cbooksys.client.views.LoginView;
import uk.cbooksys.client.views.NavBarView;
import uk.cbooksys.client.views.SlideShowView;
import uk.cbooksys.shared.AppConfig;
import uk.cbooksys.shared.AppUtil;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class AppController implements Presenter, ValueChangeHandler<String> {

	public static final String TAG_TENNIS = "tennis";
	public static final String TAG_SQUASH = "squash";
	public static final String TAG_HOME = "home";

	private final HandlerManager eventBus;
	private final CBSServiceAsync rpcService;

	private static NavBarPresenter navBarPresenter = null;
	private static LoginPresenter loginPresenter;
	private static SlideShowPresenter slideShowPresenter;
	private static DashboardPresenter dashboardPresenter;
	private static BookWeekPresenter bookWeekPresenter;

	// private HasWidgets container;
	// private HasWidgets containerLayout;
	private Container container;

	static USER inUser;

	public final static Storage storage = Storage.getLocalStorageIfSupported();

	public AppController(CBSServiceAsync rpcService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(Login.TYPE, new LoginHandler() {

			@Override
			public void onEvent(Login event, USER user) {
				doLogin(user);
			}

		});

		eventBus.addHandler(Logout.TYPE, new LogoutHandler() {

			@Override
			public void onEvent(Logout event) {
				doLogout(event);
			}

		});

		eventBus.addHandler(PopLoginEvent.TYPE, new PopLoginEventHandler() {

			@Override
			public void onEvent(PopLoginEvent event) {
				showLogin();
			}
		});

		eventBus.addHandler(BookSlotEvent.TYPE, new BookSlotEventHandler() {

			@Override
			public void onEvent(BookSlotEvent event, SLOT slot) {
				// 1. update the grid and possibly the dashboard
				manageSlotBooking(slot);

			}
		});

		eventBus.addHandler(UpdateAllSlotEvent.TYPE, new UpdateAllSlotEventHandler() {

			@Override
			public void onEvent(UpdateAllSlotEvent event, List<SLOT> slotList) {
				dashboardPresenter.addBooking(slotList, inUser);
			}
		});

		eventBus.addHandler(SlotCancelledEvent.TYPE, new SlotCancelledEventHandler() {

			@Override
			public void onEvent(SlotCancelledEvent event, SLOT slot) {

				dashboardPresenter.cancelSlot(slot);
				bookWeekPresenter.updateGrid(slot);
			}

		});

	}

	private void manageSlotBooking(SLOT slot) {
		//if (bookWeekPresenter == null)
		//	bookWeekPresenter = new BookWeekPresenter(rpcService, eventBus, new BookWeekView());
		bookWeekPresenter.refreshGrid(slot);

		dashboardPresenter.addBooking(slot);

	}

	protected void doLogout(Logout event) {
		storage.removeItem("user");
		navBarPresenter = new NavBarPresenter(rpcService, eventBus, new NavBarView(false));
		navBarPresenter.go(container);
		// This needs to move to homeView
		DockLayoutPanel dockLayoutPanel = ((DockLayoutPanel) ((FlowPanel) container.left).getParent());
		dockLayoutPanel.setWidgetSize((FlowPanel) container.left, 0);
		dockLayoutPanel.animate(750);
	}

	protected void doTest() {
		History.newItem("doTest");
	}

	protected void showLogin() {
		History.newItem("show_login");
	}

	protected void doLogin(USER user) {
		// TODO - we need to refresh the following data
		// 1. user data
		// 2. slots mat be not... need to seek a balance between local storage
		// and server call.
		inUser = user;
		storage.setItem("user", AppUtil.toJson(user));
		History.newItem("user_dashboard");
	}

	@Override
	public void go(Container container) {
		HomeView homeView = new HomeView();
		this.container = new Container(homeView.getHeader(), homeView.getContent(), homeView.getLeft(), homeView.getFooter());
		RootLayoutPanel.get().add(homeView);

		if ("".equals(History.getToken())) {
			History.newItem("home");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	boolean isUserLoggedIn() {
		if (storage != null && storage.getItem("user") != null)
			return true;
		else
			return false;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {

			if (isUserLoggedIn()) {
				// do login
				// USER user = USER.fromJson(storage.getItem("user"));
				USER user = AppUtil.fromJson(storage.getItem("user"));
				inUser = user;
				if(navBarPresenter==null){
					navBarPresenter = new NavBarPresenter(rpcService, eventBus, new NavBarView(true));
					navBarPresenter.go(container);
				}


				dashboardPresenter = new DashboardPresenter(rpcService, eventBus, new DashboardView(user));
				dashboardPresenter.go(container);

			} else {
				navBarPresenter = new NavBarPresenter(rpcService, eventBus, new NavBarView(false));
				navBarPresenter.go(container);
				History.newItem("home");
			}

			if ("home".equals(token)) {
				slideShowPresenter = new SlideShowPresenter(rpcService, eventBus, new SlideShowView());
				slideShowPresenter.go(container);

			} else if ("user_dashboard".equals(token)) {

			} else if ("show_login".equals(token)) {
				loginPresenter = new LoginPresenter(rpcService, eventBus, new LoginView());
				loginPresenter.go(container);
			} else if (TAG_SQUASH.equals(token)) {
				bookWeekPresenter = new BookWeekPresenter(rpcService, eventBus, new BookWeekView(AppConfig.SPORT_TYPE_SQUASH, AppConfig.NO_SQUASH_COURTS));
				bookWeekPresenter.go(container);
				navBarPresenter.activateLink(AppConfig.SPORT_TYPE_SQUASH);
			} else if (TAG_TENNIS.equals(token)) {
				bookWeekPresenter = new BookWeekPresenter(rpcService, eventBus, new BookWeekView(AppConfig.SPORT_TYPE_TENNIS, AppConfig.NO_TENNIS_COURTS));
				bookWeekPresenter.go(container);
				navBarPresenter.activateLink(AppConfig.SPORT_TYPE_TENNIS);
			} else if ("admin".equals(token)) {
				AdminPresenter adminPresenter = new AdminPresenter(rpcService, eventBus, new AdminView());
				adminPresenter.go(container);
			}

		}
	}

}
