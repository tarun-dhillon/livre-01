package uk.cbooksys.client.presenter;

import java.util.ArrayList;
import java.util.List;

import uk.cbooksys.client.Container;
import uk.cbooksys.client.events.UpdateAllSlotEvent;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.views.BookSlotView;
import uk.cbooksys.client.widgets.BookCell;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.SLOT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class BookWeekPresenter implements Presenter {

	public interface Display {

		Grid getGrid();

		void initGrid(int startTime, int endTime, int slotTime, int gridCols, int noCourts);

		void updateSlot(List<SLOT> slotList);

		HasClickHandlers getMorningFilterLink();

		HasClickHandlers getEveningFilterLink();

		HasClickHandlers getAfternoonFilterLink();

		void manageMorningClick();

		void manageEveningClick();

		void manageAfternoonClick();

		void removeFilterLableStyles();

		void updateGrid(SLOT slot);

		BookCell makeCell(int row, int col);

		Widget asWidget();

	}

	private final CBSServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	int startTime = 6, endTime = 22, slotTime = 45, gridCols = 8, noCourts = 4;

	public BookWeekPresenter(CBSServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		fetchSlot(false);
	}

	private void fetchSlot(){
		fetchSlot(false);
	}
	private void fetchSlot(boolean silent) {
		if(!silent) Show.message("Fetching slots ..");
		rpcService.getAllBookedSlots(new AsyncCallback<List<SLOT>>() {
			@Override
			public void onSuccess(List<SLOT> slotList) {
				display.updateSlot(slotList);
				eventBus.fireEvent(new UpdateAllSlotEvent(slotList));
			}

			@Override
			public void onFailure(Throwable caught) {
				Show.message("Something went wrong! Can't fetch slots ..");
			}
		});

	}

	public void bind() {
		display.initGrid(startTime, endTime, slotTime, gridCols, noCourts);

		// Draw skeleton grid
		BookCell bookCell;

		int gridRows = display.getGrid().getRowCount();
		int gridCols = display.getGrid().getColumnCount();
		//GWT.log("Grid : [" + gridRows + "," + gridCols + "]");
		for (int i = 0; i < gridRows; i++) {
			for (int j = 1; j < gridCols; j++) {
				//GWT.log("makeCell : [" + i + "," + j + "]");
				bookCell = display.makeCell(i, j);
				bookCell.addClickHandler(slotClickHandler);
				display.getGrid().setWidget(i, j, bookCell);
			}
		}

		display.getMorningFilterLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.manageMorningClick();
			}
		});

		display.getAfternoonFilterLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.manageAfternoonClick();
			}
		});

		display.getEveningFilterLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.manageEveningClick();
			}
		});

	}

	ClickHandler slotClickHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			BookCell bookCell = (BookCell) event.getSource();
			//GWT.log("click -- [" + bookCell.getRow() + "," + bookCell.getCol() + "]");
			// display.getGrid().getCellFormatter().addStyleName(bookCell.getRow(),
			// bookCell.getCol(), appCss.style().inFocus());

			BookSlotView bookSlotView = new BookSlotView(bookCell);
			BookSlotPresenter bookSlotPresenter = new BookSlotPresenter(rpcService, eventBus, bookSlotView);
			bookSlotPresenter.go(null);

			String[] panelHead = new String[] { "Book a court / Partner Up", "typcn-tick" };
			MyPopPanel bookSlotPanel = new MyPopPanel(panelHead[0], panelHead[1]);
			bookSlotPanel.addContent(bookSlotView);

			bookSlotView.setMyPopPanel(bookSlotPanel);

		}
	};

	public void refreshGrid(SLOT slot) {
		// First update the UI since the slot has been saved successfully
		ArrayList<SLOT> slotList = new ArrayList<SLOT>();
		slotList.add(slot);
		display.updateSlot(slotList);

		// fire a refresh from the server side.
		//fetchSlot(true);
	}

	@Override
	public void go(Container container) {
		bind();
		container.center.clear();
		container.center.add(display.asWidget());
	}

	public void updateGrid(SLOT slot) {
		display.updateGrid(slot);
	}

}
