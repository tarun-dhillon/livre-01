package uk.cbooksys.client.presenter;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.events.Login;
import uk.cbooksys.client.objs.FacebookUserJSO;
import uk.cbooksys.client.objs.GoogleUserJSO;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.AppConfig;
import uk.cbooksys.shared.USER;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getGPLogin();

		HasClickHandlers getFBLogin();


		MyPopPanel getLoginPanel();

		Widget asWidget();
	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	String userToken;
	public LoginPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {
		display.getGPLogin().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Show.message("Login using Google");
				AuthRequest req = new AuthRequest(AppConfig.GOOGLE_AUTH_URL, AppConfig.GOOGLE_CLIENT_ID).withScopes("email");

				Auth.get().login(req, new Callback<String, Throwable>() {
					@Override
					public void onSuccess(String token) {
						String jsonURL = AppConfig.GOOGLE_USERINFO_URL + "?access_token=" + token;
						userToken = token;
						GWT.log("URL =" + jsonURL);

						new JsonpRequestBuilder().requestObject(jsonURL, new AsyncCallback<GoogleUserJSO>() {
							@Override
							public void onSuccess(GoogleUserJSO result) {
								GWT.log("login success result=" + result.getName());
								Show.message("Wellcome back "+result.getName());
								display.getLoginPanel().hide(false);
								USER user = result.make();
								user.setLastLoginTime(System.currentTimeMillis());
								user.setLastLoginType(USER.LOGIN_TYPE_GOOGLE);
								//TODO need to remove this as we need to 
								user.setToken(userToken);
								rpcService.updateUser(user,new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {};
									@Override
									public void onSuccess(Void result) {
										Show.message("User updated");
									};
								});


								//TODO get user over here
								rpcService.login(user, userToken, USER.LOGIN_TYPE_GOOGLE, new AsyncCallback<USER>() {
									@Override
									public void onSuccess(USER result) {
										Show.message("Welcome back "+result.getName()+"!");
										eventBus.fireEvent(new Login(result));		
									}
									@Override
									public void onFailure(Throwable caught) {
										Show.message("Login error!");
										GWT.log(caught.toString());
									}
								});


							}

							@Override
							public void onFailure(Throwable caught) {
								Show.message("Something went wrong");
								GWT.log("Something went wrong");
							}
						});
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Display message in the UI
						Show.message("Invalid credentials");
						GWT.log("login failure");
					}
				});

			}
		});

		display.getFBLogin().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Show.message("Login using Facebook");
				final AuthRequest req = new AuthRequest(AppConfig.FACEBOOK_AUTH_URL, AppConfig.FACEBOOK_CLIENT_ID).withScopes(AppConfig.FACEBOOK_EMAIL_SCOPE, AppConfig.FACEBOOK_ME_SCOPE)
						.withScopeDelimiter(",");
				Auth.get().login(req, new Callback<String, Throwable>() {
					@Override
					public void onSuccess(String token) {

						String jsonURL = AppConfig.FACEBOOK_USERINFO_URL + "?access_token=" + token;

						new JsonpRequestBuilder().requestObject(jsonURL, new AsyncCallback<FacebookUserJSO>() {
							@Override
							public void onSuccess(FacebookUserJSO result) {

								Show.message("Wellcome back "+result.getName());
								display.getLoginPanel().hide(false);
								eventBus.fireEvent(new Login(result.make()));

							}

							@Override
							public void onFailure(Throwable caught) {
								GWT.log("Something went wrong");
								Show.message("Something went wrong");
							}
						});



					}

					@Override
					public void onFailure(Throwable caught) {
						Show.message("Invalid credentials");
					}
				});
			}
		});

	}

	@Override
	public void go(Container container) {
		bind();
		// TODO insert code here to add user-dashboard
		// container.left.add(new DashboardView());
	}

}
