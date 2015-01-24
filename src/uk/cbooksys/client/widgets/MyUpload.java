package uk.cbooksys.client.widgets;

import uk.cbooksys.client.services.CBSService;
import uk.cbooksys.client.services.CBSServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MyUpload extends Composite {

	private static MyUploadUiBinder uiBinder = GWT.create(MyUploadUiBinder.class);

	interface MyUploadUiBinder extends UiBinder<Widget, MyUpload> {
	}

	@UiField
	FormPanel uploadForm;
	@UiField
	TextBox uploadBox;
	@UiField
	FileUpload fileUpload, rFile;
	@UiField
	Button submitButton;

	public MyUpload() {

		initWidget(uiBinder.createAndBindUi(this));

		CBSServiceAsync rpcService = GWT.create(CBSService.class);
		String url;
		rpcService.getFileUploadUrl(GWT.getModuleBaseURL() + "fileupload", new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				uploadForm.setAction(result);
				GWT.log("form upload URL ::" + result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("form upload URL faild");
			}
		});

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				uploadForm.submit();
			}
		});

		uploadForm.addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				if (uploadBox.getText().length() == 0) {
					Window.alert("Please specify a file to upload");
					event.cancel();
				}
			}
		});

		uploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
			}
		});

	}

}
