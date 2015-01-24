package uk.cbooksys.shared;

public final class AppConfig {

	public static String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	public static String GOOGLE_CLIENT_ID = "270331644651-1k0jms3nuf7u2gsip7e92rnfrrjsu8e7.apps.googleusercontent.com";
	public static String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	//	public static String GOOGLE_USERINFO_URL = "https://www.googleapis.com/auth/email";
	//	https://www.googleapis.com/auth/userinfo.email
	//	https://www.googleapis.com/auth/userinfo.profile
	//  https://accounts.google.com/o/oauth2/auth?redirect_uri=https%3A%2F%2Fdevelopers.google.com%2Foauthplayground
	//	&response_type=code
	//	&client_id=270331644651-1k0jms3nuf7u2gsip7e92rnfrrjsu8e7.apps.googleusercontent.com
	//	&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile
	//	&approval_prompt=force
	//	&access_type=offline	
	//	https://accounts.google.com/o/oauth2/auth?client_id=270331644651-1k0jms3nuf7u2gsip7e92rnfrrjsu8e7.apps.googleusercontent.com&response_type=token&scope=profile&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fcbooksys%2FoauthWindow.html

	public static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";
	public static final String FACEBOOK_CLIENT_ID = "762065093873276";
	public static final String FACEBOOK_EMAIL_SCOPE = "email";
	public static final String FACEBOOK_ME_SCOPE = "user_about_me";
	public static final String FACEBOOK_USERINFO_URL = "https://graph.facebook.com/v2.2/me";



	public static final int SPORT_TYPE_SQUASH = 0;
	public static final int SPORT_TYPE_TENNIS = 1;
	public static final int NO_TENNIS_COURTS = 6;
	public static final int NO_SQUASH_COURTS = 2;


}
