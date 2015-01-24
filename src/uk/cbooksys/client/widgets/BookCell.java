package uk.cbooksys.client.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.shared.SLOT;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BookCell extends FocusPanel  {
	@UiField
	AppCss appCss;

	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, BookCell> {
	}

	int row,col;
	Date dateTime; // Date and slot start time
	int cellStatus, noCourts, sportType;





	HashMap<Integer,Integer> courtStatus;
	@UiField HTMLPanel iconPanel,cellPanel;
	@UiField Label cellText;

	@UiField HorizontalPanel courtPanel;



	private void updateIcon(){

		Set<Integer> keys = courtStatus.keySet();
		List<Integer> iconList = new ArrayList<Integer>();
		Integer status;
		StringBuffer courtStr = new StringBuffer();

		courtPanel.clear();
		for (Integer key : keys) {
			status = courtStatus.get(key);
			//update court status (dots)
			//courtStr.append(getCourtIcon(status));
			courtPanel.add(new HTML(getCourtIcon(status)));
			if(!iconList.contains(status))
				iconList.add(status);
		}

		//courtPanel.add(new HTML(courtStr.substring(0)));

		Iterator<Integer> itr =  iconList.iterator();
		Collections.sort(iconList,Collections.reverseOrder());
		iconPanel.clear();
		if(itr.hasNext()){
			cellStatus = itr.next();
			iconPanel.add(new HTML(getSlotIconHTML(cellStatus)));
		}




		//update court panel 

	}

	//	private String getCourtIcon(int slotStatus) {
	//
	//		switch (slotStatus) {
	//		case SLOT.NOT_AVAILABLE:
	//			return "<span class='typcn typcn-media-record " + appCss.style().microIcon() + " " +appCss.style().lNonAvail() +"' />";
	//		case SLOT.PARTNER_UP:
	//			return "<span class='typcn typcn-media-record " + appCss.style().microIcon() + " " +appCss.style().lPartnerUp() +"' />";
	//		case SLOT.AVAILABLE:
	//			return "<span class='typcn typcn-media-record " + appCss.style().microIcon() + " " +appCss.style().lAvail() +"' />";
	//		}
	//		return "<span class='typcn typcn--media-record " + appCss.style().microIcon() + "' />";
	//	}

	private String getCourtIcon(int slotStatus) {

		switch (slotStatus) {
		case SLOT.NOT_AVAILABLE:
			return "<span class='typcn typcn-cancel " + appCss.style().microIcon() + " " +appCss.style().lNonAvail() +"' />";
		case SLOT.PARTNER_UP:
			return "<span class='typcn typcn-user-add " + appCss.style().microIcon() + " " +appCss.style().lPartnerUp() +"' />";
		case SLOT.AVAILABLE:
			return "<span class='typcn typcn-tick " + appCss.style().microIcon() + " " +appCss.style().lAvail() +"' />";
		}
		return "<span class='typcn typcn-cancel " + appCss.style().microIcon() + "' />";
	}

	private String getSlotIconHTML(int slotStatus) {
		switch (slotStatus) {
		case SLOT.NOT_AVAILABLE:
			return "<span class='typcn typcn-cancel " + appCss.style().tinyIcon() + " " +appCss.style().notAvailable() +"' />";
		case SLOT.PARTNER_UP:
			return "<span class='typcn typcn-user-add " + appCss.style().tinyIcon() + " " +appCss.style().lPartnerUp() +"' />";
		case SLOT.AVAILABLE:
			return "<span class='typcn typcn-tick " + appCss.style().tinyIcon() + " " +appCss.style().lAvail() +"' />";
		}
		return "<span class='typcn typcn-cancel " + appCss.style().tinyIcon() + "' />";
	}




	public BookCell(int row,int col,Date dateTime) {
		this.row = row;
		this.col = col;
		this.dateTime = dateTime;



		setWidget(binder.createAndBindUi(this));
		appCss.style().ensureInjected();

	}

	public HorizontalPanel getCourtPanel() {
		return courtPanel;
	}


	public void setCourtPanel(HorizontalPanel courtPanel) {
		this.courtPanel = courtPanel;
	}


	public HashMap<Integer, Integer> getCourtStatus() {
		return courtStatus;
	}


	public void setCourtStatus(HashMap<Integer, Integer> courtStatus) {
		this.courtStatus = courtStatus;
		updateIcon();

	}

	public void setCourtStatus(int courtNo, int courtStatus) {
		//		GWT.log("----------------------------------------------");
		//		GWT.log("courtNo="+courtNo+", courtStatus="+courtStatus);
		//		GWT.log(this.courtStatus.toString());
		this.courtStatus.put(courtNo,courtStatus);
		//		GWT.log(this.courtStatus.toString());
		//		GWT.log("----------------------------------------------");

		updateIcon();

	}

	public Date getDateTime() {
		return dateTime;
	}


	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}
	public Label getCellText() {
		return cellText;
	}


	public void setCellText(String cellText) {

		this.cellText.setText(cellText);
	}

	public int getCellStatus() {
		return cellStatus;
	}


	public void setCellStatus(int cellStatus) {
		this.cellStatus = cellStatus;
	}


	public HTMLPanel getCellPanel() {
		return cellPanel;
	}


	public void setCellPanel(HTMLPanel cellPanel) {
		this.cellPanel = cellPanel;
	}

	public int getNoCourts() {
		return noCourts;
	}

	public void setNoCourts(int noCourts) {
		this.noCourts = noCourts;
	}

	public int getSportType() {
		return sportType;
	}

	public void setSportType(int sportType) {
		this.sportType = sportType;
	}

}
