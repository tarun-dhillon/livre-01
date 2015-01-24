package uk.cbooksys.client.events;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.GwtEvent;

public class SlotCancelledEvent extends GwtEvent<SlotCancelledEventHandler>{
	SLOT slot;


	public SlotCancelledEvent(SLOT slot) {
		super();
		this.slot = slot;
	}

	public static Type<SlotCancelledEventHandler> TYPE = new Type<SlotCancelledEventHandler>();

	@Override
	public Type<SlotCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SlotCancelledEventHandler handler) {
		handler.onEvent(this,this.slot);		
	}

}
