package uk.cbooksys.client.views;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.cbooksys.client.AppCss;
import uk.cbooksys.client.presenter.NavBarPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavBarView extends Composite implements NavBarPresenter.Display {

	private static NavBarUiBinder uiBinder = GWT.create(NavBarUiBinder.class);

	interface NavBarUiBinder extends UiBinder<Widget, NavBarView> {
	}

	public NavBarView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public NavBarView(boolean login) {
		initWidget(uiBinder.createAndBindUi(this));
		doLogin(login);
		anchorList.addAll(Arrays.asList(squashLink,tennisLink,homeLink,adminLink));
	}

	@UiField
	public Anchor loginLink,logoutLink,squashLink,tennisLink, homeLink,adminLink;
	List<Anchor> anchorList = new ArrayList<Anchor>();
	//Anchor []links = new Anchor[]{loginLink,logoutLink,squashLink,tennisLink, homeLink,adminLink};

	@UiField public HTMLPanel actionPanel;

	@UiField AppCss appCss;

	//	public NavBarView(String firstName) {
	//		initWidget(uiBinder.createAndBindUi(this));
	//		appCss.style().ensureInjected();
	//
	//
	//	}

	@Override
	public HasClickHandlers getLoginLink() {
		return loginLink;
	}

	@Override
	public HasClickHandlers getLogoutLink() {
		return logoutLink;
	}

	@Override
	public HTMLPanel getActionPanel() {
		return actionPanel;
	}

	@Override
	public void doLogin(boolean login){
		loginLink.setVisible(!login);
		logoutLink.setVisible(login);
		actionPanel.setVisible(login);


	}

	@Override
	public HasClickHandlers getSquashLink() {
		return this.squashLink;
	}

	@Override
	public HasClickHandlers getHomeLink() {
		return this.homeLink;
	}

	@Override
	public HasClickHandlers getAdminLink() {
		return this.adminLink;
	}

	@Override
	public HasClickHandlers getTennisLink() {
		return this.tennisLink;
	}

	@Override
	public void markSelected(Anchor link) {

		for(Anchor anchor: anchorList){
			anchor.removeStyleName(appCss.style().cLink());
		}
		link.addStyleName(appCss.style().cLink());
	}

}
