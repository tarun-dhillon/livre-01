package uk.cbooksys.client.events;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.EventHandler;



public interface BookSlotEventHandler extends EventHandler {

	void onEvent(BookSlotEvent event,SLOT slot);


}
