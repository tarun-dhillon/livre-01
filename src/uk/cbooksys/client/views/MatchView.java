package uk.cbooksys.client.views;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.client.presenter.MatchPresenter;
import uk.cbooksys.client.widgets.MyPopPanel;
import uk.cbooksys.shared.DateUtil;
import uk.cbooksys.shared.SLOT;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MatchView extends Composite  implements MatchPresenter.Display{

	private static MatchViewUiBinder uiBinder = GWT.create(MatchViewUiBinder.class);

	interface MatchViewUiBinder extends UiBinder<Widget, MatchView> {
	}

	@UiField AppCss appCss;
	MyPopPanel myPopPanel;
	@UiField Label slotDateLabel,slotTimeLabel;
	@UiField Button cancelBooking, updateBooking;
	@UiField Image userImage;

	SLOT slot;
	public MatchView(SLOT slot) {

		this.slot = slot;

		initWidget(uiBinder.createAndBindUi(this));
		appCss.style().ensureInjected();

		slotDateLabel.setText(DateUtil.getShortDate(slot.getDate()));
		slotTimeLabel.setText(DateUtil.getShortTime(slot.getDate()));
		userImage.setUrl(slot.getUser().getPicture());
		//myPopPanel.show();


	}


	@Override
	public MyPopPanel getMyPopPanel() {
		return myPopPanel;
	}
	public void setMyPopPanel(MyPopPanel myPopPanel) {
		this.myPopPanel = myPopPanel;
	}


	@Override
	public HasClickHandlers cancelBooking() {
		return cancelBooking;
	}


	@Override
	public SLOT getSLOT() {
		return this.slot;
	}

}
