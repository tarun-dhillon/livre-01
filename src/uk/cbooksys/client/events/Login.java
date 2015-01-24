package uk.cbooksys.client.events;

import uk.cbooksys.shared.USER;

import com.google.gwt.event.shared.GwtEvent;

public class Login extends GwtEvent<LoginHandler>{
	USER user;
	
	
	public Login(USER user) {
		super();
		this.user = user;
	}

	public static Type<LoginHandler> TYPE = new Type<LoginHandler>();
	
	@Override
	public Type<LoginHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginHandler handler) {
		handler.onEvent(this,this.user);		
	}

}
