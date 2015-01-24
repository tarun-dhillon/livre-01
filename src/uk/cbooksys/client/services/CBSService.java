package uk.cbooksys.client.services;

import java.util.List;

import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("cbsService")
public interface CBSService extends RemoteService {

	String getFileUploadUrl(String context) throws IllegalArgumentException;

	String makeUsers(List<USER> users, boolean delTable) throws IllegalArgumentException;
	USER login(USER user,String token,int loginType) throws IllegalArgumentException;
	List<USER> getAllUsers();
	void updateUser(USER user) throws IllegalArgumentException;


	String createDumSlots(int slots, boolean delSlots, String courtType);
	List<SLOT> getAllBookedSlots();
	SLOT book(SLOT slot) throws IllegalArgumentException;
	SLOT book(SLOT slot,boolean sendEmail) throws IllegalArgumentException;
	void delete(SLOT slot) throws IllegalArgumentException;
	void deleteSlot(long slotId) throws IllegalArgumentException;


}
