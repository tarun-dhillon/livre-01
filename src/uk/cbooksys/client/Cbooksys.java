package uk.cbooksys.client;

import java.util.List;

import uk.cbooksys.client.services.CBSService;
import uk.cbooksys.client.services.CBSServiceAsync;
import uk.cbooksys.client.widgets.Show;
import uk.cbooksys.shared.AppUtil;
import uk.cbooksys.shared.USER;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cbooksys implements EntryPoint {
	//	public static NavBarView navBar = new NavBarView();

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {


		CBSServiceAsync rpcService = GWT.create(CBSService.class);
		HandlerManager eventBus = new HandlerManager(null);
		AppController appViewer = new AppController(rpcService, eventBus);

		appViewer.go(null);

		//RootLayoutPanel.get().add(child)
		//RootPanel.get().add(navBar);



		// load and store 
		rpcService.getAllUsers(new AsyncCallback<List<USER>>() {
			@Override
			public void onSuccess(List<USER> userList) {
				Show.message("load "+userList.size()+" users" );
				AppController.storage.setItem("userAll", AppUtil.toJson(userList));
			}
			@Override
			public void onFailure(Throwable caught) {
				Show.message("Something went wrong while fetching users !");				
			}
		});



	}



}
