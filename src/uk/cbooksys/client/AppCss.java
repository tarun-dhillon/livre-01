package uk.cbooksys.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AppCss extends ClientBundle {
	@Source("main_ui.css")
	Style style();

	public interface Style extends CssResource {

		String myPopUpWindow();

		// Grid
		String dataCell();
		String inFocus();
		String outFocus();
		String dataCellNotAvail();

		//Label
		String bgLabel();
		String myLabel();
		String mySmallLabel();
		String myTinyLabel();
		String smallLabel();
		String tinyLabel();
		String lAvail();
		String lNonAvail();
		String lPartnerUp();

		String textOutFocus();
		String textInFocus();


		//SLOT status
		String available();
		String notAvailable();
		String partnerUp();

		// Icons
		String microIcon();
		String tinyIcon();
		String smallIcon();
		String mediumIcon();

		String clickIcon();

		//Table
		String myTable();

		//Links 

		String aLink();
		String cLink(); // clicked or selected link

		// Input fields
		String inputField();

		// Diver
		String oDivider(); // orange
		String gDivider(); // gray


	}

}