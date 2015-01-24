package uk.cbooksys.client.widgets;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler;
import org.moxieapps.gwt.uploader.client.events.FileQueueErrorEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueueErrorHandler;
import org.moxieapps.gwt.uploader.client.events.FileQueuedEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueuedHandler;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.UploadErrorEvent;
import org.moxieapps.gwt.uploader.client.events.UploadErrorHandler;
import org.moxieapps.gwt.uploader.client.events.UploadProgressEvent;
import org.moxieapps.gwt.uploader.client.events.UploadProgressHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ProgressBar;

public class MyFileUpload extends Composite {

	private static MyFileUploadUiBinder uiBinder = GWT.create(MyFileUploadUiBinder.class);

	interface MyFileUploadUiBinder extends UiBinder<Widget, MyFileUpload> {
	}

	@UiField
	HTMLPanel uploadPanel;
	final VerticalPanel progressBarPanel = new VerticalPanel();
	final Map<String, ProgressBar> progressBars = new LinkedHashMap<String, ProgressBar>();
	final Map<String, Button> cancelButtons = new LinkedHashMap<String, Button>();

	Uploader uploader;
	//HTMLPanel uploadPanel;
	public MyFileUpload(Uploader uploader) {
		this.uploader = uploader;
		initWidget(uiBinder.createAndBindUi(this));
		prepare();

	}

	public void prepare() {
		//.setButtonImageURL(GWT.getModuleBaseURL() + "resources/images/buttons/upload_new_version_button.png")
		uploader.setUploadURL("/DevNullUploadServlet");
		uploader.setButtonText("Upload file");
		uploader.setStyleName("button orange");
		uploader.setButtonWidth(133).setButtonHeight(22)
		.setFileSizeLimit("50 MB").setButtonCursor(Uploader.Cursor.HAND).setButtonAction(Uploader.ButtonAction.SELECT_FILES).setFileQueuedHandler(new FileQueuedHandler() {
			@Override
			public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {
				// Create a Progress Bar for this file
				final ProgressBar progressBar = new ProgressBar(0.0, 1.0, 0.0, new CancelProgressBarTextFormatter());
				progressBar.setTitle(fileQueuedEvent.getFile().getName());
				progressBar.setHeight("18px");
				progressBar.setWidth("200px");
				progressBars.put(fileQueuedEvent.getFile().getId(), progressBar);

				// Add Cancel Button Image
				//final Image cancelButton = new Image(GWT.getModuleBaseURL() + "resources/images/icons/cancel.png");
				final Button cancelButton = new Button("Cancel upload");
				cancelButton.setStyleName("button orange");
				cancelButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						uploader.cancelUpload(fileQueuedEvent.getFile().getId(), false);
						progressBars.get(fileQueuedEvent.getFile().getId()).setProgress(-1.0d);
						cancelButton.removeFromParent();
					}
				});
				cancelButtons.put(fileQueuedEvent.getFile().getId(), cancelButton);

				// Add the Bar and Button to the interface
				HorizontalPanel progressBarAndButtonPanel = new HorizontalPanel();
				progressBarAndButtonPanel.add(progressBar);
				progressBarAndButtonPanel.add(cancelButton);
				progressBarPanel.add(progressBarAndButtonPanel);

				return true;
			}
		}).setUploadProgressHandler(new UploadProgressHandler() {
			@Override
			public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
				ProgressBar progressBar = progressBars.get(uploadProgressEvent.getFile().getId());
				progressBar.setProgress((double) uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal());
				return true;
			}
		}).setUploadCompleteHandler(new UploadCompleteHandler() {
			@Override
			public boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent) {
				cancelButtons.get(uploadCompleteEvent.getFile().getId()).removeFromParent();
				uploader.startUpload();
				return true;
			}
		}).setFileDialogStartHandler(new FileDialogStartHandler() {
			@Override
			public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent) {
				if (uploader.getStats().getUploadsInProgress() <= 0) {
					// Clear the uploads that have completed, if none
					// are in process
					progressBarPanel.clear();
					progressBars.clear();
					cancelButtons.clear();
				}
				return true;
			}
		}).setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
			@Override
			public boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent) {
				if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0) {
					if (uploader.getStats().getUploadsInProgress() <= 0) {
						uploader.startUpload();
					}
				}
				return true;
			}
		}).setFileQueueErrorHandler(new FileQueueErrorHandler() {
			@Override
			public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {
				Window.alert("Upload of file " + fileQueueErrorEvent.getFile().getName() + " failed due to [" + fileQueueErrorEvent.getErrorCode().toString() + "]: "
						+ fileQueueErrorEvent.getMessage());
				return true;
			}
		}).setUploadErrorHandler(new UploadErrorHandler() {
			@Override
			public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {
				cancelButtons.get(uploadErrorEvent.getFile().getId()).removeFromParent();
				Window.alert("Upload of file " + uploadErrorEvent.getFile().getName() + " failed due to [" + uploadErrorEvent.getErrorCode().toString() + "]: " + uploadErrorEvent.getMessage());
				return true;
			}
		});

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(uploader);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final Label dropFilesLabel = new Label("Drop Files Here");
			dropFilesLabel.setStyleName("dropFilesLabel");
			dropFilesLabel.addDragOverHandler(new DragOverHandler() {
				@Override
				public void onDragOver(DragOverEvent event) {
					if (!uploader.getButtonDisabled()) {
						dropFilesLabel.addStyleName("dropFilesLabelHover");
					}
				}
			});
			dropFilesLabel.addDragLeaveHandler(new DragLeaveHandler() {
				@Override
				public void onDragLeave(DragLeaveEvent event) {
					dropFilesLabel.removeStyleName("dropFilesLabelHover");
				}
			});
			dropFilesLabel.addDropHandler(new DropHandler() {
				@Override
				public void onDrop(DropEvent event) {
					dropFilesLabel.removeStyleName("dropFilesLabelHover");

					if (uploader.getStats().getUploadsInProgress() <= 0) {
						progressBarPanel.clear();
						progressBars.clear();
						cancelButtons.clear();
					}

					uploader.addFilesToQueue(Uploader.getDroppedFiles(event.getNativeEvent()));
					event.preventDefault();
				}
			});
			verticalPanel.add(dropFilesLabel);
		}

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(verticalPanel);
		horizontalPanel.add(progressBarPanel);
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(uploader, HorizontalPanel.ALIGN_LEFT);
		horizontalPanel.setCellHorizontalAlignment(progressBarPanel, HorizontalPanel.ALIGN_RIGHT);

		// noinspection GwtToHtmlReferences
		//RootPanelxx.get("MultiUploadWithProgressBarAndDragAndDrop").add(horizontalPanel);
		uploadPanel.add(horizontalPanel);
	}

	protected class CancelProgressBarTextFormatter extends ProgressBar.TextFormatter {
		@Override
		protected String getText(ProgressBar bar, double curProgress) {
			if (curProgress < 0) {
				return "Cancelled";
			}
			return ((int) (100 * bar.getPercent())) + "%";
		}
	}
}
