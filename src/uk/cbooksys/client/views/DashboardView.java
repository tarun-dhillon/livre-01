package uk.cbooksys.client.views;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.client.GridCss;
import uk.cbooksys.client.presenter.DashboardPresenter;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.AppConfig;
import uk.cbooksys.shared.DateUtil;
import uk.cbooksys.shared.SLOT;
import uk.cbooksys.shared.USER;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
public class DashboardView extends Composite  implements DashboardPresenter.Display {

	@UiField AppCss appCss;
	private static DashboardUiBinder uiBinder = GWT.create(DashboardUiBinder.class);

	interface DashboardUiBinder extends UiBinder<Widget, DashboardView> {
	}


	public DashboardView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField VerticalPanel upSlotsPanel;

	USER user;

	@UiField (provided=true) 
	UserView memType;//,squashType;


	@UiField (provided = true) DataGrid<SLOT> upSlotGrid;

	Set<String> keys;
	List<HashMap<String, String>> gridList;
	public DashboardView(USER user) {
		this.user = user;
		memType = new UserView(this.user,UserView.USER_VIEW_LAGRE,USER.CARD_TYPE_MEM);

		setupUpConingSlotsTabel();
		initWidget(uiBinder.createAndBindUi(this));

		appCss.style().ensureInjected();


	}
	final ListDataProvider<SLOT> provider = new ListDataProvider<SLOT>();
	final SingleSelectionModel<SLOT> selectionModel = new SingleSelectionModel<SLOT>();
	void setupUpConingSlotsTabel(){
		//Set up the up coming slot grid 


		GridCss gridCss = GWT.create(GridCss.class);
		upSlotGrid = new DataGrid<SLOT>(Integer.MAX_VALUE,gridCss);
		upSlotGrid.setWidth("100%");
		upSlotGrid.setVisible(true);
		upSlotGrid.setAutoHeaderRefreshDisabled(true);
		upSlotGrid.setEmptyTableWidget(new Label("No upcoming bookings"));
		upSlotGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);



		SafeHtmlCell safeHtmlCell = new SafeHtmlCell();
		Column<SLOT, SafeHtml> dateColumn = new Column<SLOT, SafeHtml>(safeHtmlCell) {

			@Override
			public SafeHtml getValue(SLOT object) {
				switch (object.getCourtType()) {
				case AppConfig.SPORT_TYPE_SQUASH:
					return new SafeHtmlBuilder().appendHtmlConstant("<span class='typcn typcn-social-flickr-circular "+appCss.style().smallIcon()+"'/>").toSafeHtml();
				case AppConfig.SPORT_TYPE_TENNIS:
					return new SafeHtmlBuilder().appendHtmlConstant("<span class='typcn typcn-media-record "+appCss.style().smallIcon()+"'/>").toSafeHtml();
				}
				return new SafeHtmlBuilder().appendHtmlConstant("<span class='typcn typcn-warning "+appCss.style().smallIcon()+"'/>").toSafeHtml();

			}
		};
		upSlotGrid.addColumn(dateColumn, " ");

		upSlotGrid.addColumn(new TextColumn<SLOT>() {
			@Override
			public String getValue(SLOT object) {

				return String.valueOf(object.getCourtNo());
			}
		},"#");


		upSlotGrid.addColumn(new TextColumn<SLOT>() {
			@Override
			public String getValue(SLOT object) {

				return DateUtil.getDayTime(object.getDate());
			}
		},"Date");



		upSlotGrid.addColumn(new TextColumn<SLOT>() {
			@Override
			public String getValue(SLOT object) {
				if (object.getUsers() != null && object.getUsers().length > 0)  
					return object.getUsers()[0];
				else return "None";
			}
		},"Vs.");


		// Add a selection model to handle user selection.
		upSlotGrid.setColumnWidth(0, "15%");
		upSlotGrid.setColumnWidth(1, "10%");
		upSlotGrid.setColumnWidth(2, "40%");
		//upSlotGrid.setColumnWidth(3, "10%");

		upSlotGrid.setSelectionModel(selectionModel);


		//provider.getList().add(null);
		//addDataDisplay(display)
		provider.addDataDisplay(upSlotGrid);


	}


	@Override
	public void addBooking(SLOT slot) {
		// Push the data into the widget.
		provider.getList().add(slot);
		//provider.addDataDisplay(upSlotGrid);
		//upSlotGrid.setRowData(upSlotGrid.getRowCount(), Arrays.asList(slot));
		//upSlotGrid.setRowData(0, Arrays.asList(slot));
	}


	@Override
	public void addBooking(List<SLOT> slotList, USER inUser) {
		Show.message(slotList.size()+" slots booked");
		for (SLOT slot : slotList) {
			if(inUser.getEmail().equals(slot.getMainUser()))
				addBooking(slot);
		}
	}


	@Override
	public SingleSelectionModel<SLOT> getSelectionModel() {
		return this.selectionModel;
	}


	@Override
	public void cancelSlot(SLOT slot) {
		provider.getList().remove(slot);
	}

}
