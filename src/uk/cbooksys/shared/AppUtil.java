package uk.cbooksys.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import uk.cbooksys.client.AppController;

import com.google.gwt.core.shared.GWT;
import com.kfuntak.gwt.json.serialization.client.Serializer;

public class AppUtil {

	static Serializer serializer = (Serializer) GWT.create(Serializer.class);

	// TODO THis might be a risk.
	public static String toJson(USER user) {
		return serializer.serialize(user);
	}

	public static USER fromJson(String json) {
		return (USER) serializer.deSerialize(json, "uk.cbooksys.shared.USER");
	}

	public static String toJson(List<USER> userList) {
		StringBuffer buffer = new StringBuffer();
		for (USER user : userList) {
			buffer.append(toJson(user));
			buffer.append(";");
		}
		return buffer.substring(0);
	}

	public static List<USER> fromJsonList(String jsonList) {
		String[] json = jsonList.split(";");
		List<USER> userList = new ArrayList<USER>();
		for (int i = 0; i < json.length-1; i++) {
			userList.add((USER) serializer.deSerialize(json[i], "uk.cbooksys.shared.USER"));
		}
		return userList; 
	}

	public static List<USER> getAllUsers(){
		return fromJsonList(AppController.storage.getItem("userAll"));
	}

	public static int getInnerGridSize(int noCourts) {
		int gridSize = 1;

		while ((gridSize * gridSize) < noCourts)
			gridSize++;
		return gridSize;
	}

	public static HashSet<String> getDummyNames() {
		String[] names = new String[] { "Ok Oswalt", "Francesco Farraj", "Dionne Discher", "Gerard Gravelle", "Vania Valla", "Nedra Nilles", "Tobias Tozier", "Clare Converse", "Shelley Stearman",
				"Amos Atnip", "Li Longfellow", "Londa Losh", "Dagmar Daugherty", "Denny Durr", "Carina Carrero", "Tiesha Thon", "Cristy Clermont", "Florance Frew", "Fran Francia", "Pansy People" };
		return new HashSet<String>(Arrays.asList(names));

	}

	public static int getNoOfSlots(int startTime, int endTime, int slotTime) {
		Date startDate = resetTime(new Date());
		startDate = DateUtil.addMM(startDate, startTime * 60);

		Date endDate = resetTime(new Date());
		endDate = DateUtil.addMM(endDate, endTime * 60);
		int noSlots = 0;
		while (startDate.before(endDate)) {
			startDate = DateUtil.addMM(startDate, slotTime);
			noSlots++;
		}
		return noSlots;
	}

	public static Date resetTime(Date date) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

}
