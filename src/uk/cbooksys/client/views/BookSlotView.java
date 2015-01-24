package uk.cbooksys.client.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uk.cbooksys.client.AppController;
import uk.cbooksys.client.AppCss;
import uk.cbooksys.client.presenter.BookSlotPresenter;
import uk.cbooksys.client.widgets.BookCell;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.shared.AppUtil;
import uk.cbooksys.shared.DateUtil;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

public class BookSlotView extends Composite implements BookSlotPresenter.Display {

	@UiField
	AppCss appCss;

	private static BookSlotViewUiBinder uiBinder = GWT.create(BookSlotViewUiBinder.class);

	interface BookSlotViewUiBinder extends UiBinder<Widget, BookSlotView> {
	}

	@UiField
	Label slotDateLabel, slotTime;

	@UiField
	Button addUser, bookSlot;

	@UiField
	FlexTable userTable;

	@UiField
	HTMLPanel partnerUpPanel, bookPanel;

	@UiField(provided = true)
	UserView partnerUpView;

	@UiField 
	CheckBox isPartnerUp;

	@UiField
	Grid courtGrid;
	@UiField(provided = true)
	SuggestBox secondPlayer;
	DateTimeFormat dateFormatter = DateTimeFormat.getFormat("ccc, dd MMM yy");
	DateTimeFormat timeFormatter = DateTimeFormat.getFormat("HH:mm");
	int courtsDisplayLen = 4;
	int rows, cols, selectedCourt;
	HandlerRegistration mOuthandler;

	BookCell bookCell;
	SLOT slot;
	MyPopPanel myPopPanel;

	@Override
	public MyPopPanel getMyPopPanel() {
		return myPopPanel;
	}

	public void setMyPopPanel(MyPopPanel myPopPanel) {
		this.myPopPanel = myPopPanel;
	}

	public BookSlotView(BookCell bookCell) {
		this.bookCell = bookCell;

		initOracle();

		initWidget(uiBinder.createAndBindUi(this));

		// inject external css
		appCss.style().ensureInjected();
		secondPlayer.addStyleName(appCss.style().bgLabel());
		secondPlayer.getValueBox().addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (secondPlayer.getText() != null && secondPlayer.getText().contains("--or--")) {
					secondPlayer.setText("");
					secondPlayer.removeStyleName(appCss.style().bgLabel());

				}
			}
		});


		isPartnerUp.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//TODO some logic to disable
			}
		});

		showCourts();

	}

	@Override
	public SLOT getSlot() {

		USER user = AppUtil.fromJson(AppController.storage.getItem("user"));

		slot = new SLOT();
		slot.setDate(bookCell.getDateTime());
		slot.setStartTime(bookCell.getDateTime());
		slot.setEndTime(DateUtil.addMin(bookCell.getDateTime(), SLOT.DURATION));
		slot.setBookingTime(new Date().toString()); // Not sure if this is
		// correct
		slot.setBookTime(slot.getDate());
		slot.setMainUser(user.getEmail());
		slot.setUser(user);
		slot.setCourtType(bookCell.getSportType());
		slot.setCourtNo(selectedCourt);
		if(isPartnerUp.getValue().booleanValue())
			slot.setCourtStatus(SLOT.PARTNER_UP);
		else 
			slot.setCourtStatus(SLOT.NOT_AVAILABLE);

		slot.setUsers(null);
		return slot;

	}

	void showCourts() {
		HashMap<Integer, Integer> courtMap = bookCell.getCourtStatus();
		int noCourts = courtMap.size();
		// TODO test with more than 4 courts

		if (noCourts == courtsDisplayLen)
			courtGrid.resize(rows = (noCourts / courtsDisplayLen), cols = noCourts);
		else if (noCourts < courtsDisplayLen)
			courtGrid.resize(rows = 1, cols = noCourts);
		else if (noCourts > courtsDisplayLen)	
			courtGrid.resize(rows = (1 + noCourts / courtsDisplayLen), cols = courtsDisplayLen);
		GWT.log("noCourts = "+noCourts+"rows = "+rows+" cols="+cols);
		HTML courtHTML;
		FocusPanel focusPanel;
		int courtNo = 1, status;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols && courtNo<=noCourts; col++, courtNo++) {

				courtHTML = new HTML("Court " + courtNo + "<br/>" + getSlotHTML(status = courtMap.get(courtNo)));
				courtHTML.addStyleName(appCss.style().bgLabel());
				focusPanel = new FocusPanel();
				// courtHTML = new HTML("df");
				focusPanel.getElement().setAttribute("status", String.valueOf(status));
				focusPanel.getElement().setAttribute("id", String.valueOf(courtNo));
				focusPanel.getElement().setAttribute("row", String.valueOf(row));
				focusPanel.getElement().setAttribute("col", String.valueOf(col));
				focusPanel.addStyleName(appCss.style().dataCell());
				focusPanel.add(courtHTML);
				focusPanel.addStyleName(getSlotSTyle(status));

				if (status > 1) {
					focusPanel.addClickHandler(courtSelectHandler);
					focusPanel.addMouseOverHandler(mouseOverHandler);
					mOuthandler = focusPanel.addMouseOutHandler(mouseOutHandler);
				}
				// focusPanel.addClickHandler(handler);
				courtGrid.setWidget(row, col, focusPanel);
			}
		}

		userTable.addStyleName(appCss.style().myTable());
		addUser.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				int userRows = userTable.getRowCount();
				GWT.log("userRows =" + userRows);

				if ("".equals(secondPlayer.getText()) || secondPlayer.getText().contains("--or--") || userRows > 3)
					return;
				HTML html = new HTML("<span class='typcn typcn-delete " + appCss.style().tinyIcon() + " " + appCss.style().clickIcon() + "' />");
				html.getElement().setAttribute("row", String.valueOf(userRows + 1));
				userTable.insertRow(0);
				if (userTable.getRowCount() >= 3)
					addUser.setVisible(false);
				userTable.setWidget(0, 0, html);
				userTable.setHTML(0, 1, secondPlayer.getText());
				secondPlayer.setText("");

				html.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						GWT.log("row before::" + userTable.getRowCount());
						event.getRelativeElement().getParentElement().getParentElement().removeFromParent();
						if (userTable.getRowCount() < 3)
							addUser.setVisible(true);
						GWT.log("row after::" + userTable.getRowCount());
					}
				});

			}
		});

	}

	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

	private void initOracle() {

		// oracle.addAll(AppUtil.getDummyNames());
		oracle.addAll(getUserDisplayList(AppUtil.getAllUsers()));
		secondPlayer = new SuggestBox(oracle);

		USER partnerUpUser = USER.makeDumUser();
		partnerUpView = new UserView(partnerUpUser, UserView.USER_VIEW_MEDIUM, USER.CARD_TYPE_SQUASH);
	}

	public List<String> getUserDisplayList(List<USER> userList) {
		List<String> userNameList = new ArrayList<String>();
		for (USER user : userList) {
			// userNameList.add(user.getName()+"("+user.getEmail()+")");
			userNameList.add(user.getEmail());

		}
		return userNameList;
	}

	@Override
	public void updateUsers(List<USER> userList) {
		List<String> userNameList = new ArrayList<String>();
		for (USER user : userList) {
			userNameList.add(user.getName() + "(" + user.getEmail() + ")");

		}
		// ((MultiWordSuggestOracle)
		// secondPlayer.getSuggestOracle()).addAll(userNameList);
	}

	MouseOverHandler mouseOverHandler = new MouseOverHandler() {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			int row = Integer.parseInt(event.getRelativeElement().getAttribute("row"));
			int col = Integer.parseInt(event.getRelativeElement().getAttribute("col"));
			((FocusPanel) courtGrid.getWidget(row, col)).addStyleName(appCss.style().inFocus());
		}
	};

	MouseOutHandler mouseOutHandler = new MouseOutHandler() {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			int row = Integer.parseInt(event.getRelativeElement().getAttribute("row"));
			int col = Integer.parseInt(event.getRelativeElement().getAttribute("col"));
			boolean selected = Boolean.parseBoolean(event.getRelativeElement().getAttribute("selected"));
			if (selected)
				return;
			((FocusPanel) courtGrid.getWidget(row, col)).removeStyleName(appCss.style().inFocus());
			// GWT.log("mouseOut "+row+","+col);
		}
	};

	ClickHandler courtSelectHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			selectedCourt = Integer.parseInt(event.getRelativeElement().getAttribute("id"));
			int row = Integer.parseInt(event.getRelativeElement().getAttribute("row"));
			int col = Integer.parseInt(event.getRelativeElement().getAttribute("col"));
			int status = Integer.parseInt(event.getRelativeElement().getAttribute("status"));

			if (status == 2) {
				partnerUpPanel.setVisible(true);
				bookPanel.setVisible(false);
			} else if (status == 3) {
				partnerUpPanel.setVisible(false);
				bookPanel.setVisible(true);
			}

			// GWT.log("click "+row+","+col);
			clearStyle(courtGrid, appCss.style().inFocus());
			FocusPanel focusPanel = (FocusPanel) courtGrid.getWidget(row, col);
			focusPanel.getElement().blur();
			focusPanel.addStyleName(appCss.style().inFocus());
			focusPanel.getElement().setAttribute("selected", "true");
		}
	};

	void clearStyle(Grid grid, String styleName) {
		int rows = grid.getRowCount();
		int cols = grid.getColumnCount();
		FocusPanel focusPanel;
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++) {
				focusPanel = (FocusPanel) courtGrid.getWidget(row, col);
				focusPanel.removeStyleName(appCss.style().inFocus());
				focusPanel.getElement().setAttribute("selected", "false");
			}
	}

	private String getSlotSTyle(int status) {
		switch (status) {
		case 1:
			return appCss.style().notAvailable() + " " + appCss.style().bgLabel();
		case 2:
			return appCss.style().partnerUp();
		case 3:
			return appCss.style().available();
		}
		return appCss.style().notAvailable();
	}

	private String getSlotHTML(int slotStatus) {
		switch (slotStatus) {
		case 1:
			return "<span class='typcn typcn-cancel " + appCss.style().smallIcon() + "' />";
		case 2:
			return "<span class='typcn typcn-user-add " + appCss.style().smallIcon() + " " + appCss.style().lPartnerUp() + "' />";
		case 3:
			return "<span class='typcn typcn-tick " + appCss.style().smallIcon() + " " + appCss.style().lAvail() + "' />";
		}
		return "<span class='typcn typcn-cancel " + appCss.style().smallIcon() + "' />";
	}

	@Override
	public HasClickHandlers bookSlot() {
		return this.bookSlot;
	}

}
