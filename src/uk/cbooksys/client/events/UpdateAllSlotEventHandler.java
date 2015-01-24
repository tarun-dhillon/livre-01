package uk.cbooksys.client.events;

import java.util.List;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.EventHandler;



public interface UpdateAllSlotEventHandler extends EventHandler {

	void onEvent(UpdateAllSlotEvent event,List<SLOT> slotList);


}
