package uk.cbooksys.client.services;

import java.util.List;

import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CBSServiceAsync {


	void getFileUploadUrl(String context, AsyncCallback<String> callback) throws IllegalArgumentException;



	//User related calls 
	void makeUsers(List<USER> users, boolean delTable, AsyncCallback<String> callback) throws IllegalArgumentException;
	void updateUser(USER user,AsyncCallback<Void> callback) throws IllegalArgumentException;
	void getAllUsers(AsyncCallback<List<USER>> callback);
	void  login(USER user,String token,int loginType,AsyncCallback<USER> callback) throws IllegalArgumentException;

	// Slot related functions 
	void getAllBookedSlots(AsyncCallback<List<SLOT>> callback);
	void book(SLOT slot,AsyncCallback<SLOT> callback) throws IllegalArgumentException;
	void book(SLOT slot,boolean sendEmail,AsyncCallback<SLOT> callback) throws IllegalArgumentException;
	void createDumSlots(int slots, boolean delSlots,String courtType, AsyncCallback<String> callback);
	void delete(SLOT slot, AsyncCallback<Void> callback) throws IllegalArgumentException;
	void deleteSlot(long slotId, AsyncCallback<Void> callback) throws IllegalArgumentException;

}
