package uk.cbooksys.server;

import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


public class OfyService {
	static {
		factory().register(USER.class);
		factory().register(SLOT.class);

		//factory().register(OtherThing.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}