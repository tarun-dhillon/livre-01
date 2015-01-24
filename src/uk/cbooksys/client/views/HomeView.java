package uk.cbooksys.client.views;

import uk.cbooksys.client.presenter.HomePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
public class HomeView extends Composite  implements HomePresenter.Display {


	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}
	@UiField 
	DockLayoutPanel dockPanel;
	@UiField 
	FlowPanel headerPanel, footerPanel, leftPanel, centerPanel;

	public HomeView() {

		initWidget(uiBinder.createAndBindUi(this));


		//		headerPanel.add(Note.attach());
		//		Note.log("Note is now attached");
	}




	@Override
	public Panel getHeader() {
		return headerPanel;
	}

	@Override
	public Panel getLeft() {
		return leftPanel;
	}

	@Override
	public Panel getContent() {
		return centerPanel;
	}

	@Override
	public Panel getFooter() {
		return footerPanel;
	}

	@Override
	public DockLayoutPanel getDockPanel() {
		return dockPanel;
	}





}
