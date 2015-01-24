package uk.cbooksys.client.events;

import uk.cbooksys.shared.USER;

import com.google.gwt.event.shared.GwtEvent;

public class Logout extends GwtEvent<LogoutHandler>{
	USER user;
	
	
	public Logout() {
	}

	public static Type<LogoutHandler> TYPE = new Type<LogoutHandler>();
	
	@Override
	public Type<LogoutHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogoutHandler handler) {
		handler.onEvent(this);		
	}

}
