package uk.cbooksys.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class PopLoginEvent extends GwtEvent<PopLoginEventHandler>{
  public static Type<PopLoginEventHandler> TYPE = new Type<PopLoginEventHandler>();
  
  @Override
  public Type<PopLoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(PopLoginEventHandler handler) {
    handler.onEvent(this);
  }
}
