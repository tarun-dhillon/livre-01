package uk.cbooksys.client.events;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.EventHandler;



public interface SlotCancelledEventHandler extends EventHandler {

	void onEvent(SlotCancelledEvent event,SLOT slot);


}
