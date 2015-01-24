package uk.cbooksys.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyPopPanel extends PopupPanel implements HasText {

	private static MyPopPanelUiBinder uiBinder = GWT.create(MyPopPanelUiBinder.class);

	interface MyPopPanelUiBinder extends UiBinder<Widget, MyPopPanel> {
	}

	@UiField
	HTML iconType;
	@UiField
	Label labelTitle;
	@UiField
	Anchor closeWindow;
	@UiField
	HTMLPanel contentPanel;

	@UiField
	PopupPanel popupPanel;
	public MyPopPanel(){}
	public MyPopPanel(String title, String iconType) {
		setWidget(uiBinder.createAndBindUi(this));
		//		popupPanel = (MyPopPanel) uiBinder.createAndBindUi(this);
		//		setWidget(popupPanel);
		popupPanel.setAnimationType(AnimationType.ROLL_DOWN);
		labelTitle.setText(title);
		setTitleIcon(iconType);

		// NOTE - Using this approach because the popup panel.centre() command
		// is not working.
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				popupPanel.center();
			}
		});
	}


	public void addContent(Widget widget){
		contentPanel.add(widget);
	}
	@Override
	public void hide(boolean withHistoy) {
		popupPanel.hide();
		if(withHistoy) History.back();
	}

	/**
	 * Set the icon in the Title - refer to http://typicons.com/ for icon types
	 * 
	 * @param iconType
	 */
	public void setTitleIcon(String icon) {
		iconType.addStyleName("typcn " + icon);
	}

	@UiHandler("closeWindow")
	void onClick(ClickEvent event) {
		popupPanel.hide(true);
		//History.back();
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}

}
