package uk.cbooksys.shared;

import java.io.Serializable;
import java.util.Date;

import uk.cbooksys.client.AppCss;

import com.google.gwt.resources.client.CssResource;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class SLOT implements Serializable{

	/**
	 * 
	 */

	public static final int NOT_AVAILABLE = 1;
	public static final int PARTNER_UP = 2;
	public static final int AVAILABLE = 3;

	public static final int SQUASH = 1;
	public static final int TENNIS = 2;
	public static final int GYM_SPIN = 3;

	public static final int DURATION = 45;

	private static final long serialVersionUID = 1L;
	Date date,startTime,endTime,bookTime;
	@Id Long id;
	int courtNo, courtStatus,courtType;
	String mainUser, bookingTime;

	USER user;	

	String[] users;

	static AppCss appCss;
	public SLOT(){}

	//	static Serializer serializer =  (Serializer)GWT.create( Serializer.class );
	//
	//	public String toJson(){
	//		return serializer.serialize(this);
	//	}
	//
	//	public static SLOT fromJson(String json){
	//		return (SLOT)serializer.deSerialize(json, "uk.cbooksys.shared.SLOT");
	//	}

	interface SlotStyle extends CssResource {
		String dataCell();
		String available();
		String notAvaiable();
		String partnerUp();
	}







	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainUser() {
		return mainUser;
	}

	public void setMainUser(String mainUser) {
		this.mainUser = mainUser;
	}

	public int getCourtType() {
		return courtType;
	}

	public void setCourtType(int courtType) {
		this.courtType = courtType;
	}

	public int getCourtNo() {
		return courtNo;
	}

	public void setCourtNo(int courtNo) {
		this.courtNo = courtNo;
	}


	public int getCourtStatus() {
		return courtStatus;
	}

	public void setCourtStatus(int courtStatus) {
		this.courtStatus = courtStatus;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	public USER getUser() {
		return user;
	}

	public void setUser(USER user) {
		this.user = user;
	}




}
