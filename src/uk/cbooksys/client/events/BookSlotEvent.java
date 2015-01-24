package uk.cbooksys.client.events;

import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.shared.GwtEvent;

public class BookSlotEvent extends GwtEvent<BookSlotEventHandler>{
	SLOT slot;


	public BookSlotEvent(SLOT slot) {
		super();
		this.slot = slot;
	}

	public static Type<BookSlotEventHandler> TYPE = new Type<BookSlotEventHandler>();

	@Override
	public Type<BookSlotEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookSlotEventHandler handler) {
		handler.onEvent(this,this.slot);		
	}

}
