package uk.cbooksys.client;

import com.google.gwt.user.client.ui.HasWidgets;

public class Container {

	public final HasWidgets header;
	public final HasWidgets center;
	public final HasWidgets left;
	public final HasWidgets footer;

	public Container(HasWidgets header, HasWidgets center, HasWidgets left, HasWidgets footer) {
		super();
		this.header = header;
		this.center = center;
		this.left = left; 
		this.footer = footer;
	}



}
