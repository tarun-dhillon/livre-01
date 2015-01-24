package uk.cbooksys.client.views;

import org.moxieapps.gwt.uploader.client.Uploader;

import uk.cbooksys.client.presenter.AdminPresenter;
import uk.cbooksys.client.widgets.JsFileUpload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AdminView extends Composite implements AdminPresenter.Display {

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	@UiField
	TextBox noUsersRecords, noSlotRecords;
	@UiField
	CheckBox delUserTable, delSlotTable;
	@UiField
	Button userExecute, slotExecute;
	@UiField
	HTMLPanel sUploadPanel;
	//	@UiField
	//	HorizontalPanel gridPanel;
	//	@UiField
	//	Label uploadLabel;

	Uploader uploader;

	public AdminView() {

		initWidget(uiBinder.createAndBindUi(this));
		sUploadPanel.add(new JsFileUpload());

	}

	@Override
	public HasClickHandlers getUserExecute() {
		return this.userExecute;
	}

	@Override
	public HasClickHandlers getSlotExecute() {
		return this.slotExecute;
	}

	@Override
	public int getNoUsers() {
		return Integer.parseInt(this.noUsersRecords.getText());
	}

	@Override
	public boolean cleanUsers() {
		return delUserTable.getValue();
	}

	@Override
	public int getNoSlots() {
		return Integer.parseInt(this.noSlotRecords.getText());
	}

	@Override
	public boolean cleanSlots() {
		return delSlotTable.getValue();
	}

}
