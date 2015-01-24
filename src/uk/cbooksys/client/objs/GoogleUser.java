package uk.cbooksys.client.objs;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleUser  {

	/*Example response from Google
	 * { "id": "106876095136541424006", 
	 * "name": "Tarun Dhillon",
	 * "given_name": "Tarun", 
	 * "family_name": "Dhillon", 
	 * "link":"https://plus.google.com/106876095136541424006", "picture":
	 * "https://lh6.googleusercontent.com/-Nza2D2Kl0R4/AAAAAAAAAAI/AAAAAAAAAAA/EONt4wlnm38/photo.jpg"
	 * , "gender": "male", "locale": "en-GB" }
	 */
	
	String id,name,given_name,family_name,link,picture,gender,locale;

	public GoogleUser() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	

		
		
}
