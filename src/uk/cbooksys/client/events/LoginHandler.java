package uk.cbooksys.client.events;

import uk.cbooksys.shared.USER;

import com.google.gwt.event.shared.EventHandler;



public interface LoginHandler extends EventHandler {

	void onEvent(Login event,USER user);

	
}
