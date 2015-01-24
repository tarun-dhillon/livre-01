package uk.cbooksys.client.views;

import uk.cbooksys.client.presenter.LoginPresenter;
import uk.cbooksys.client.widgets.MyPopPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class LoginView extends Composite implements LoginPresenter.Display {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, LoginView> {
	}


	@UiField
	Button fbLogin, gpLogin;
	
	MyPopPanel loginPanel;
	
		
	public  LoginView(){
		loginPanel = new MyPopPanel("Login/Sign Up", "typcn-key");
		initWidget(uiBinder.createAndBindUi(this));
		loginPanel.addContent(this);
		
	}

	
	@Override
	public HasClickHandlers getGPLogin() {
		return gpLogin;
	}

	@Override
	public HasClickHandlers getFBLogin() {
		return fbLogin;
	}


	@Override
	public MyPopPanel getLoginPanel() {
		return this.loginPanel;
	}



}
