package uk.cbooksys.client.objs;

import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleUserJSO extends JavaScriptObject {

	/*Example response from Google
	 * { "id": "106876095136541424006", 
	 * "name": "Tarun Dhillon",
	 * "given_name": "Tarun", 
	 * "family_name": "Dhillon", 
	 * "link":"https://plus.google.com/106876095136541424006", "picture":
	 * "https://lh6.googleusercontent.com/-Nza2D2Kl0R4/AAAAAAAAAAI/AAAAAAAAAAA/EONt4wlnm38/photo.jpg"
	 * , "gender": "male", "locale": "en-GB" }
	 */
	
	
		protected GoogleUserJSO(){}
		
		public final native String getID() /*-{ return this.ID;}-*/;

		public final native String getName() /*-{return this.name;}-*/;

		public final native String getGiven_name() /*-{return this.given_name;}-*/;

		public final native String getFamily_name() /*-{return this.family_name;}-*/;

		public final native String getLink() /*-{return this.link;}-*/;

		public final native String getPicture() /*-{return this.picture;}-*/;

		public final native String getGender() /*-{return this.gender;}-*/;

		public final native String getLocale() /*-{return this.locale;}-*/;
		public final native String getEmail() /*-{return this.email;}-*/;
		

		public final USER make(){
			USER user = new USER();
			user.setId(getID());
			user.setName(getName());
			user.setEmail(getEmail());
			user.setGiven_name(getGiven_name());
			user.setFamily_name(getFamily_name());
			user.setLink(getLink());
			user.setPicture(getPicture());
			user.setGender(getGender());
			user.setLocale(getLocale());
			return user;
		}
		
}
