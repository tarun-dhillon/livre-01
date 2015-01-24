package uk.cbooksys.client.events;

import java.util.List;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateAllSlotEvent extends GwtEvent<UpdateAllSlotEventHandler>{
	List<SLOT> slotList;


	public UpdateAllSlotEvent(List<SLOT> slotList) {
		super();
		this.slotList = slotList;
	}

	public static Type<UpdateAllSlotEventHandler> TYPE = new Type<UpdateAllSlotEventHandler>();

	@Override
	public Type<UpdateAllSlotEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateAllSlotEventHandler handler) {
		handler.onEvent(this,this.slotList);		
	}

}
