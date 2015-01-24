package uk.cbooksys.client.views;

import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MetricView extends Composite implements HasText {

	private static MetricViewUiBinder uiBinder = GWT.create(MetricViewUiBinder.class);

	interface MetricViewUiBinder extends UiBinder<Widget, MetricView> {
	}

	@UiField
	HorizontalPanel userMemType, userSquash, userTennis, userGym;

	public MetricView(USER user, int viewType) {
		initWidget(uiBinder.createAndBindUi(this));

		switch (viewType) {
		case 0:
			userMemType.setVisible(true);
			break;
		case 1:
			userSquash.setVisible(true);
			break;
		case 2:
			userTennis.setVisible(true);
			break;
		case 3:
			userGym.setVisible(true);
			break;
		default:
			userMemType.setVisible(true);
			break;
		}

	}

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
