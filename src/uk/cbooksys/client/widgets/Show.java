package uk.cbooksys.client.widgets;

import uk.cbooksys.shared.DateUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class Show extends PopupPanel {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, Show> {
	}

	@UiField Label showText;
	static StringBuffer historyBuffer = new StringBuffer();
	static boolean showHistory = false;

	static Show show;

	Show(){}

	public static void message(String text) {
		if(show == null) show = new Show();

		//super(autoHide, modal);
		show.setAutoHideEnabled(true);
		show.setModal(true);
		show.setWidget(binder.createAndBindUi(show));
		show.removeStyleName("gwt-PopupPanel");
		historyBuffer.append("["+DateUtil.getLogDate()+"] "+text );
		show.showText.setText(text);
		show.show(3000);


		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				show.center();
				if(!showHistory)show.setPopupPosition(show.getPopupLeft(), 10);
				showHistory = false;
			}
		});

		show.addDomHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				showHistory = true;
				show.showText.setText(historyBuffer.substring(0));
			}
		}, DoubleClickEvent.getType());
	}

	void show(int delayMilliseconds) {
		show();
		Timer t = new Timer() {
			@Override
			public void run() {
				Show.this.hide();
			}
		};

		// Schedule the timer to close the popup in 3 seconds.
		t.schedule(delayMilliseconds);
	}

}