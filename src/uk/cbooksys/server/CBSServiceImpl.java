package uk.cbooksys.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uk.cbooksys.client.services.CBSService;
import uk.cbooksys.server.send.Mail;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;

import static uk.cbooksys.server.OfyService.ofy;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CBSServiceImpl extends RemoteServiceServlet implements
CBSService {


	@Override
	public String makeUsers(List<USER> users, boolean delTable) throws IllegalArgumentException {

		String tim1,tim2,tim0;
		long sTime = System.currentTimeMillis();
		if(delTable)
			ofy().delete().keys(ofy().load().type(USER.class).keys());
		tim0 = (System.currentTimeMillis() - sTime) +"ms";	

		sTime = System.currentTimeMillis();
		ofy().save().entities(users).now();
		tim1 = (System.currentTimeMillis() - sTime) +"ms";
		sTime = System.currentTimeMillis();
		for (USER user : users) {
			ofy().save().entity(user).now();	
		}
		tim2 = (System.currentTimeMillis() - sTime) +"ms";

		return "Server:"+users.size()+" users added  flag:"+delTable+"d el:"+tim0+"bulk: "+tim1+" array:"+tim2;
	}

	@Override
	public String getFileUploadUrl(String context){
		BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
		return service.createUploadUrl(context);
	}


	@Override
	public void updateUser(USER user){
		ofy().save().entity(user);
	}

	@Override
	public USER login(USER user,String token,int loginType){
		//TODO need to do something about the token over here.
		//user.setToken(token)
		USER newUser  = ofy().load().type(USER.class)
				.filter("email",user.getEmail())
				.filter("token", user.getToken()).iterator().next();
		user.setLastLoginTime(System.currentTimeMillis());
		user.setLastLoginType(loginType);
		ofy().save().entity(newUser);
		return newUser;
	}

	@Override
	public String createDumSlots(int slots, boolean delSlots, String courtType){
		long sTime = System.currentTimeMillis();

		if(delSlots)
			ofy().delete().keys(ofy().load().type(SLOT.class).keys());

		List<SLOT> slotList = new ArrayList<SLOT>();
		SLOT slot;

		//Date today = new Date();
		int startTime = 6, slotTime=45;//, endTime=22;

		List<USER> userList = new ArrayList<USER>();
		Iterator<USER> itr =   ofy().load().type(USER.class).iterator();
		while (itr.hasNext()) {
			userList.add(itr.next());
		}

		Calendar today = Calendar.getInstance(); 
		//		Calendar curDate = Calendar.getInstance();
		//		curDate.add(Calendar.MONTH, startTime * 60);
		Random random = new Random();
		for (int i = 0; i < slots; i++) {
			slot = new SLOT();

			today = Calendar.getInstance(); 
			today.add(Calendar.DAY_OF_MONTH, random.nextInt(4));
			slot.setDate(today.getTime());

			today.add(Calendar.MINUTE, startTime * 60 * random.nextInt(15));
			slot.setStartTime(today.getTime());

			today.add(Calendar.MINUTE, slotTime);
			slot.setEndTime(today.getTime());

			slot.setCourtType(SLOT.SQUASH);
			slot.setCourtNo(random.nextInt(4)+1);
			slot.setCourtStatus(random.nextInt(2)+1);

			slot.setBookingTime(today.getTime().toString()); // Not sure if this is correct
			slot.setBookTime(slot.getDate());
			slot.setMainUser(userList.get(random.nextInt(userList.size()-1)).getName());
			slot.setUsers(new String[]{userList.get(random.nextInt(userList.size()-1)).getName()});
			slotList.add(slot);
		}
		ofy().save().entities(slotList).now();

		return slots+" slots created in "+(System.currentTimeMillis()-sTime)+"ms";
	}

	@Override
	public List<SLOT> getAllBookedSlots(){
		List<SLOT> slotList = new ArrayList<SLOT>();
		Iterator<SLOT> itr =   ofy().load().type(SLOT.class).iterator();
		while (itr.hasNext()) {
			slotList.add(itr.next());
		}
		return slotList;
	}

	@Override
	public List<USER> getAllUsers() {
		List<USER> slotUser = new ArrayList<USER>();
		Iterator<USER> itr =   ofy().load().type(USER.class).iterator();
		while (itr.hasNext()) {
			slotUser.add(itr.next());
		}
		return slotUser;
	}

	@Override
	public SLOT book(SLOT slot) throws IllegalArgumentException {
		ofy().save().entity(slot).now();
		return slot;
	}
	@Override
	public SLOT book(SLOT slot,boolean sendEmail) throws IllegalArgumentException {
		slot = book(slot);
		Mail.sendMail(slot.getUser().getEmail(), slot.getUser().getName(), "Booking confirmation "+slot.getDate(), "Some confirmation text");

		return slot;
	}

	@Override
	public void delete(SLOT slot) throws IllegalArgumentException {
		ofy().delete().entity(slot);
	}

	@Override
	public void deleteSlot(long slotId) throws IllegalArgumentException {
		Key<SLOT> key = Key.create(SLOT.class, slotId); 
		ofy().delete().key(key);	
	}
}
