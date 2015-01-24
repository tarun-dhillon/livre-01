package uk.cbooksys.client.views;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.client.presenter.BookWeekPresenter;
import uk.cbooksys.client.widgets.BookCell;
import uk.cbooksys.shared.AppUtil;
import uk.cbooksys.shared.DateUtil;
import uk.cbooksys.shared.SLOT;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class BookWeekView extends Composite implements BookWeekPresenter.Display {
	@UiField AppCss appCss;
	private static WeekBookViewUiBinder uiBinder = GWT.create(WeekBookViewUiBinder.class);

	interface WeekBookViewUiBinder extends UiBinder<Widget, BookWeekView> {

	}

	interface GridStyle extends CssResource {
		String rowHeader();

		String colHeader();

		String highlight();
		// String applyFilterLink();

	}

	@UiField
	GridStyle style;

	@UiField
	Anchor morningFilterLink, eveningFilterLink, afterNoonFilterLink;
	int startTime = 6, endTime = 22, gridCols = 8;

	boolean mFilterApplied = false;
	boolean aFilterApplied = false;
	boolean eFilterApplied = false;

	@UiField
	Grid weekGrid, weekGridHead;

	DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd MMM yy");
	DateTimeFormat dayFormatter = DateTimeFormat.getFormat("ccc");

	DateTimeFormat ddFormatter = DateTimeFormat.getFormat("ccc dd MMM yy");
	DateTimeFormat timeFormatter = DateTimeFormat.getFormat("HH:mm");
	int sportType;
	int noCourts;


	public BookWeekView(int sportType,int noCourts) {
		this.sportType = sportType;
		this.noCourts = noCourts;

		initWidget(uiBinder.createAndBindUi(this));
		// inject external
		appCss.style().ensureInjected();
	}

	@Override
	public void updateSlot(List<SLOT> slotList) {

		for (SLOT slot : slotList) {
			int row = getSlotRow(slot);
			int col = getSlotCol(slot);

			if (row == -1 || col == -1)
				continue;

			BookCell bookCell = (BookCell) weekGrid.getWidget(row, col);
			bookCell.setCourtStatus(slot.getCourtNo(), slot.getCourtStatus());

			if (bookCell.getCellStatus() == SLOT.NOT_AVAILABLE) {
				weekGrid.getCellFormatter().addStyleName(row, col, appCss.style().dataCellNotAvail());
			}
		}

	}

	@Override
	public BookCell makeCell(int row, int col){
		HashMap<Integer, Integer> slotMap = new HashMap<Integer, Integer>();
		Date dateTime = getDateTimeFromCell(row, col);
		BookCell bookCell = new BookCell(row, col, dateTime);
		slotMap = new HashMap<Integer, Integer>();
		for (int k = 0; k < noCourts; k++) {
			slotMap.put(k + 1, SLOT.AVAILABLE);
		}
		bookCell.setCourtStatus(slotMap); // automatically will update
		bookCell.setCellText("["+row+","+col+"]");
		bookCell.setSportType(sportType);
		bookCell.setNoCourts(noCourts);
		bookCell.addMouseOverHandler(mouseOverHandle);
		bookCell.addMouseOutHandler(mouseOutHandle);

		bookCell.getCourtPanel().addStyleName(appCss.style().textOutFocus());
		weekGrid.getCellFormatter().setStylePrimaryName(row, col, appCss.style().dataCell());


		return bookCell;
	}

	private int getSlotRow(SLOT slot) {
		Date slotDate = slot.getStartTime(), startDate, endDate;
		int rows = weekGrid.getRowCount();
		HTML cellHTML;
		//GWT.log("["+startDate+","+endDate+"]::"+slot.getStartTime());
		String dateStr = DateTimeFormat.getFormat(DateUtil.TIME_ONLY).format(slotDate);
		slotDate = DateTimeFormat.getFormat(DateUtil.TIME_ONLY).parse(dateStr);
		// slotDate = DateUtil.getYearAsString(date)

		for (int row = 0; row < rows; row++) {
			cellHTML = (HTML) weekGrid.getWidget(row, 0);
			startDate = DateTimeFormat.getFormat(DateUtil.TIME_ONLY).parse(cellHTML.getElement().getAttribute("start"));
			endDate = DateTimeFormat.getFormat(DateUtil.TIME_ONLY).parse(cellHTML.getElement().getAttribute("end"));

			// GWT.log("["+startDate+","+endDate+"]::"+slot.getStartTime());
			// if(slotDate.compareTo(startDate) >= 0 &&
			// slotDate.compareTo(endDate) <=0)
			if (DateUtil.timeCompare(slotDate, startDate) >= 0 && DateUtil.timeCompare(slotDate, endDate) < 0)
				return row;

		}
		return -1;
	}

	private int getSlotCol(SLOT slot) {
		Date slotDate = slot.getDate(), colDate, nextDate;
		int cols = weekGridHead.getColumnCount();
		HTML cellHTML;
		String dateStr;
		for (int col = cols - 1; col > 1; col--) {

			cellHTML = (HTML) weekGridHead.getWidget(0, col);
			dateStr = cellHTML.getElement().getAttribute("date");
			colDate = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).parse(dateStr);

			cellHTML = (HTML) weekGridHead.getWidget(0, col - 1);
			// GWT.log("col: "+col+" cellHTML:"+cellHTML);
			dateStr = cellHTML.getElement().getAttribute("date");
			nextDate = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).parse(dateStr);

			// nextDate =
			// DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).parse(weekGrid.getWidget(0,col-1).getElement().getAttribute("date"));
			if (slotDate.compareTo(colDate) <= 0 && slotDate.compareTo(nextDate) >= 0)
				return col-1;
		}
		return -1;
	}

	@Override
	public void removeFilterLableStyles() {
		mFilterApplied = false;
		aFilterApplied = false;
		eFilterApplied = false;
		morningFilterLink.removeStyleName(appCss.style().cLink());
		afterNoonFilterLink.removeStyleName(appCss.style().cLink());
		eveningFilterLink.removeStyleName(appCss.style().cLink());

	}

	@Override
	public void initGrid(int startTime, int endTime, int slotTime, int gridCols, int noCourts) {
		int gridRows = AppUtil.getNoOfSlots(startTime, endTime, slotTime) + 1;
		//GWT.log("gridRows = " + gridRows);
		weekGrid.resize(gridRows, gridCols);
		weekGridHead.resize(1, gridCols);

		// set header row
		Date today = new Date();
		today = DateUtil.resetTime(today);
		Date curDate, nextDate;
		HTML cellHTML;

		// prepare col header
		for (int i = 1; i < gridCols; i++) {
			curDate = DateUtil.addDays(today, i - 1);
			cellHTML = new HTML(dayFormatter.format(curDate) + "<br>" + dateFormatter.format(curDate));
			cellHTML.getElement().setAttribute("date", DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).format(curDate));
			weekGridHead.setWidget(0, i, cellHTML);
			weekGridHead.getCellFormatter().setStylePrimaryName(0, i, style.colHeader());

		}

		curDate = DateUtil.addMM(today, startTime * 60);
		// prepare row header

		for (int i = 0; i < gridRows; i++) {
			nextDate = DateUtil.addMM(curDate, slotTime);
			cellHTML = new HTML(timeFormatter.format(curDate) + " - " + timeFormatter.format(nextDate));
			cellHTML.getElement().setAttribute("start", DateTimeFormat.getFormat(DateUtil.TIME_ONLY).format(curDate));
			cellHTML.getElement().setAttribute("end", DateTimeFormat.getFormat(DateUtil.TIME_ONLY).format(nextDate));
			weekGrid.setWidget(i, 0, cellHTML);
			weekGrid.getCellFormatter().setStylePrimaryName(i, 0, style.rowHeader());
			curDate = DateUtil.addMM(curDate, slotTime);
		}

	}

	Date getDateTimeFromCell(int row, int col) {
		Date dateTime, time;
		// GWT.log("getDateTimeFromCell :: {"+row+","+col+"}");
		String rowStr = weekGrid.getWidget(row, 0).getElement().getInnerText();// Get
		String colStr = weekGridHead.getWidget(0, col).getElement().getInnerHTML();// Get

		colStr = colStr.replaceAll("<br>", " ");
		dateTime = ddFormatter.parse(colStr);

		rowStr = rowStr.substring(0, rowStr.indexOf("-") - 1);
		time = timeFormatter.parse(rowStr);

		// TODO fix this without using deprecrated functions. See DateUtil for
		// more details.
		dateTime.setHours(time.getHours());
		dateTime.setMinutes(time.getMinutes());

		return dateTime;
	}





	boolean contains(int[] court, int val) {
		for (int i = 0; i < court.length; i++) {
			if (court[i] == val)
				return true;
		}
		return false;
	}

	MouseOverHandler mouseOverHandle = new MouseOverHandler() {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			BookCell bookCell = (BookCell) event.getSource();
			bookCell.getCourtPanel().removeStyleName(appCss.style().textOutFocus());
			//Show.message("mouse over ("+ bookCell.getRow()+","+bookCell.getRow()+")");
			weekGrid.getCellFormatter().addStyleName(bookCell.getRow(), 0, style.highlight());
			weekGridHead.getCellFormatter().addStyleName(0, bookCell.getCol(), style.highlight());
			weekGrid.getCellFormatter().addStyleName(bookCell.getRow(), bookCell.getCol(), appCss.style().inFocus());
		}
	};

	MouseOutHandler mouseOutHandle = new MouseOutHandler() {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			BookCell bookCell = (BookCell) event.getSource();
			bookCell.getCourtPanel().addStyleName(appCss.style().textOutFocus());
			weekGrid.getCellFormatter().removeStyleName(bookCell.getRow(), 0, style.highlight());
			weekGridHead.getCellFormatter().removeStyleName(0, bookCell.getCol(), style.highlight());
			weekGrid.getCellFormatter().removeStyleName(bookCell.getRow(), bookCell.getCol(), appCss.style().inFocus());

		}
	};

	@Override
	public HasClickHandlers getMorningFilterLink() {
		return this.morningFilterLink;
	}

	@Override
	public HasClickHandlers getEveningFilterLink() {
		return this.eveningFilterLink;
	}

	@Override
	public HasClickHandlers getAfternoonFilterLink() {
		return this.afterNoonFilterLink;
	}

	@Override
	public void manageMorningClick() {
		if (!mFilterApplied) {
			initGrid(startTime, 12, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
			mFilterApplied = true;
			morningFilterLink.addStyleName(appCss.style().cLink());
		} else {
			initGrid(startTime, endTime, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
		}		
	}

	@Override
	public void manageEveningClick() {
		if (!eFilterApplied) {
			initGrid(17, endTime, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
			aFilterApplied = true;
			eveningFilterLink.addStyleName(appCss.style().cLink());
		} else {
			initGrid(startTime, endTime, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
		}
		eFilterApplied = !eFilterApplied;
	}

	@Override
	public void manageAfternoonClick() {
		if (!aFilterApplied) {
			initGrid(12, 17, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
			aFilterApplied = true;
			afterNoonFilterLink.addStyleName(appCss.style().cLink());
		} else {
			initGrid(startTime, endTime, SLOT.DURATION, gridCols, noCourts);
			removeFilterLableStyles();
		}
	}

	@Override
	public Grid getGrid() {
		return this.weekGrid;
	}

	@Override
	public void updateGrid(SLOT slot) {
		int col = getSlotCol(slot);
		int row = getSlotRow(slot);
		BookCell bookCell = (BookCell) weekGrid.getWidget(row, col);
		bookCell.setCourtStatus(slot.getCourtNo(), slot.getCourtStatus());
	}

}
