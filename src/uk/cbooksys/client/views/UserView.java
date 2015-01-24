package uk.cbooksys.client.views;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserView extends Composite implements HasText {

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);

	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}
	@UiField AppCss appCss;
	
	public static int USER_VIEW_SMALL = 0;
	public static int USER_VIEW_MEDIUM = 1;
	public static int USER_VIEW_LAGRE = 2;
	
	@UiField Label userName;
	@UiField Image userPicture;
	USER user;
	int type;
	public UserView(USER user,int size, int type) {
		this.user = user;
		this.type = type;
		initWidget(uiBinder.createAndBindUi(this));
		appCss.style().ensureInjected();
		
		userName.setText(user.getName());
		GWT.log(user.getName()+"----");
		
		switch (size) {
		case 0:
			userPicture.setSize("30px", "30px");
			break;
		case 1:
			userPicture.setSize("50px", "50px");
			break;
		case 2:
			userPicture.setSize("75px", "75px");
			break;
		default:
			break;
		}
		
		userPicture.setUrl(user.getPicture());
		userName.setText(user.getName());
	}

	@UiFactory
	MetricView getUserView() { 
		return new MetricView(this.user,this.type);
	}

	

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
