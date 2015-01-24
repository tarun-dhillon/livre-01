package uk.cbooksys.shared;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;


@Entity
@Index
public class USER implements Serializable,JsonSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public static int CARD_TYPE_MEM = 0;
	public static int CARD_TYPE_SQUASH = 1;
	public static int CARD_TYPE_TENNIS = 2;
	public static int CARD_TYPE_GYM = 3;

	public static int LOGIN_TYPE_FORM = 0;
	public static int LOGIN_TYPE_GOOGLE = 1;
	public static int LOGIN_TYPE_FACEBOOK = 2;


	@Id Long userId;
	@Index String email;
	int lastLoginType;
	String id,name,given_name,family_name,link,picture,gender,locale,token;
	long lastLoginTime;
	public int getLastLoginType() {
		return lastLoginType;
	}
	public void setLastLoginType(int lastLoginType) {
		this.lastLoginType = lastLoginType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public USER(){}
	public USER(String id, String email, String name, String given_name, String family_name, String link, String picture, String gender, String locale) {
		super();

		this.id = id;
		this.email = email;
		this.name = name;
		this.given_name = given_name;
		this.family_name = family_name;
		this.link = link;
		this.picture = picture;
		this.gender = gender;
		this.locale = locale;
	}

	public static USER makeDumUser(){
		return new USER("001","Carolineradix@yahoo.fr","Caroline","Caro","Radix",
				"https://www.facebook.com/caroline.radix","http://graph.facebook.com/caroline.radix/picture","Female","En");

	}

	public static List<USER> makeDumList(){
		List<USER> dumList = new ArrayList<USER>();
		dumList.add(new USER("FB000","Carolineradix@yahoo.fr","Caroline","Caro","Radix","https://www.facebook.com/caroline.radix","http://graph.facebook.com/caroline.radix/picture","Female","En"));
		dumList.add(new USER("FB001","Tarundhillon@gmail.com","James","James Smith","Smith","http://graph.facebook.com/James.Smith","http://graph.facebook.com/James.Smith/picture","Make","EN"));
		dumList.add(new USER("FB002","Tarundhillon@gmail.com","Michael","Michael Smith","Smith","http://graph.facebook.com/Michael.Smith","http://graph.facebook.com/Michael.Smith/picture","Make","EN"));
		dumList.add(new USER("FB003","Tarundhillon@gmail.com","Robert","Robert Smith","Smith","http://graph.facebook.com/Robert.Smith","http://graph.facebook.com/Robert.Smith/picture","Make","EN"));
		dumList.add(new USER("FB004","Tarundhillon@gmail.com","Maria","Maria Garcia","Garcia","http://graph.facebook.com/Maria.Garcia","http://graph.facebook.com/Maria.Garcia/picture","Make","EN"));
		dumList.add(new USER("FB005","Tarundhillon@gmail.com","David","David Smith","Smith","http://graph.facebook.com/David.Smith","http://graph.facebook.com/David.Smith/picture","Make","EN"));
		dumList.add(new USER("FB006","Tarundhillon@gmail.com","Maria","Maria Rodriguez","Rodriguez","http://graph.facebook.com/Maria.Rodriguez","http://graph.facebook.com/Maria.Rodriguez/picture","Make","EN"));
		dumList.add(new USER("FB007","Tarundhillon@gmail.com","Mary","Mary Smith","Smith","http://graph.facebook.com/Mary.Smith","http://graph.facebook.com/Mary.Smith/picture","Make","EN"));
		dumList.add(new USER("FB008","Tarundhillon@gmail.com","Maria","Maria Hernandez","Hernandez","http://graph.facebook.com/Maria.Hernandez","http://graph.facebook.com/Maria.Hernandez/picture","Make","EN"));
		dumList.add(new USER("FB009","Tarundhillon@gmail.com","Maria","Maria Martinez","Martinez","http://graph.facebook.com/Maria.Martinez","http://graph.facebook.com/Maria.Martinez/picture","Make","EN"));
		dumList.add(new USER("FB010","Tarundhillon@gmail.com","James","James Johnson","Johnson","http://graph.facebook.com/James.Johnson","http://graph.facebook.com/James.Johnson/picture","Make","EN"));
		dumList.add(new USER("FB011","Tarundhillon@gmail.com","William","William Smith","Smith","http://graph.facebook.com/William.Smith","http://graph.facebook.com/William.Smith/picture","Make","EN"));
		dumList.add(new USER("FB012","Tarundhillon@gmail.com","Robert","Robert Johnson","Johnson","http://graph.facebook.com/Robert.Johnson","http://graph.facebook.com/Robert.Johnson/picture","Make","EN"));
		dumList.add(new USER("FB013","Tarundhillon@gmail.com","John","John Smith","Smith","http://graph.facebook.com/John.Smith","http://graph.facebook.com/John.Smith/picture","Make","EN"));
		return dumList;
	}

	//static Serializer serializer =  (Serializer)GWT.create( Serializer.class );

	//	public String toJson(){
	//		return serializer.serialize(this);
	//	}
	//
	//	public static USER fromJson(String json){
	//		return (USER)serializer.deSerialize(json, "uk.cbooksys.shared.USER");
	//	}



	public String getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
