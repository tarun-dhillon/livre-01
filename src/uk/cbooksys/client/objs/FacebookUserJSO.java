package uk.cbooksys.client.objs;

import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.JavaScriptObject;

public class FacebookUserJSO extends JavaScriptObject {

	/*Example response from Google
email: "tarundhillon@gmail.com"
first_name: "Tarun"
gender: "male"
id: "10152999937470917"
last_name: "Dhillon"
link: "https://www.facebook.com/app_scoped_user_id/10152999937470917/"
locale: "en_GB"
name: "Tarun Dhillon"
timezone: 0
updated_time: "2014-03-17T16:00:19+0000"
verified: true
	 */
	
	
		protected FacebookUserJSO(){}
		
		public final native String getEmail() /*-{ return this.email;}-*/;
		public final native String getFirst_name() /*-{return this.first_name;}-*/;
		public final native String getGender() /*-{return this.gender;}-*/;
		public final native String getId() /*-{ return this.id;}-*/;
		public final native String getLast_name() /*-{return this.last_name;}-*/;
		public final native String getLink() /*-{return this.link;}-*/;		
		public final native String getLocale() /*-{return this.locale;}-*/;
		public final native String getName() /*-{return this.name;}-*/;
		public final native String getTimezone() /*-{return this.timezone;}-*/;
		public final native String getUpdate_time() /*-{return this.update_time;}-*/;
		
//		public final native String getGiven_name() /*-{return this.given_name;}-*/;
//		public final native String getPicture() /*-{return this.picture;}-*/;





		public final USER make(){
			USER user = new USER();
			user.setId(getId());
			user.setName(getName());
			user.setEmail(getEmail());
//			user.setGiven_name(getGiven_name());
			user.setFamily_name(getLast_name());
			user.setLink(getLink());
			user.setPicture("https://graph.facebook.com/"+getId()+"/picture");
			user.setGender(getGender());
			user.setLocale(getLocale());
			return user;
		}
		
}
