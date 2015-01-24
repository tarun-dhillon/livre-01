package uk.cbooksys.client.presenter;

import uk.cbooksys.client.Container;

import com.google.gwt.user.client.ui.HasWidgets;

public abstract interface Presenter {
  public abstract void go(final Container container);
}
