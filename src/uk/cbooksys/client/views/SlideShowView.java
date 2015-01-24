package uk.cbooksys.client.views;

import uk.cbooksys.client.presenter.SlideShowPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SlideShowView extends Composite implements SlideShowPresenter.Display {

	private static SlideShowViewUiBinder uiBinder = GWT.create(SlideShowViewUiBinder.class);

	interface SlideShowViewUiBinder extends UiBinder<Widget, SlideShowView> {
	}

	private final String path = "/images/club_1/";
	private final String[] images = { "squash.jpg", "tennis.jpg", "gym.jpg", "classes.jpg", "bar.jpg" };
	@UiField
	HTMLPanel slidePanel;

	// @UiField
	Image slideImage;
	// Index of the image currently being displayed
	private int currentImage = 0;

	public SlideShowView() {
        // Prefetching the list of images.
//        for (String url : images){
//            Image.prefetch(path+url);
//            GWT.log(path+url);
//        }
//        GWT.log("before exceptions 1/2...");
//        // Start by displaying the first image.

		
		initWidget(uiBinder.createAndBindUi(this));
		
//		slideImage.setUrl(path+images[0]);
//		
//		
//		playAnimation();
//        GWT.log("before exceptions 2/2...");
		
		
		  //Window.alert("jquery loaded ? "+String.valueOf(isjQueryLoaded()));
		 // ScriptInjector.fromString("alert($wnd.jQuery)").inject();
		//ScriptInjector.fromString("$(document).ready(function(){ $('.banner').unslider({speed: 500,delay: 3000,complete: function() {},keys: true,dots: true,fluid: true });});").setWindow(ScriptInjector.TOP_WINDOW).inject();

		
		  Timer timer = new Timer()
	        {
	            @Override
	            public void run()
	            {
//	            	ScriptInjector.fromString("$(document).ready(function(){ alert($('.banner').unslider()); });").setWindow(ScriptInjector.TOP_WINDOW).inject();
	            	ScriptInjector.fromString("$(document).ready(function(){ $('.banner').unslider({speed: 1000,delay: 5000,complete: function() {},keys: true,dots: true,fluid: true });});").setRemoveTag(false).setWindow(ScriptInjector.TOP_WINDOW).inject();
	            	GWT.log("unslider script injected and executed");
	            }
	        };

	        timer.schedule(500);
		
		//ScriptInjector.fromString("$wnd.$(document).ready(function(){ $('.banner').unslider({speed: 500,delay: 3000,complete: function() {},keys: true,dots: true,fluid: true });});").inject();

//		ScriptInjector.fromUrl("/scripts/jquery.min.js").setWindow(ScriptInjector.TOP_WINDOW).setCallback(new Callback<Void, Exception>() {
//			
//			@Override
//			public void onSuccess(Void result) {
//				Window.alert(result+ " jquery loaded");
//				ScriptInjector.fromString("alert(window.jQuery)").inject();			
//				ScriptInjector.fromUrl("/scripts/unislider.min.js").setCallback(new Callback<Void, Exception>() {
//					@Override
//					public void onSuccess(Void result) {
//						Window.alert(result+ " unislider loaded");
//						ScriptInjector.fromString("$(document).ready(function(){ $('.banner').unslider({speed: 500,delay: 3000,complete: function() {},keys: true,dots: true,fluid: true });});").inject();				
//						Window.alert(" unislider command executed");
//					}
//					@Override
//					public void onFailure(Exception reason) {
//						Window.alert(reason+ "unslider failed");
//					}
//				}).inject();
//		
//			
//			}
//			
//			@Override
//			public void onFailure(Exception reason) {
//				Window.alert(reason+ "jquery failed");
//			}
//		}).inject();
				
				

	

	}
	
	/**
     * Check to see if jQuery is loaded already
     *
     * @return true is jQuery is loaded, false otherwise
     */
    private native boolean isjQueryLoaded() /*-{
        return (typeof $wnd['jQuery'] !== 'undefined');
    }-*/;

	// The Timer provides a means to execute arbitrary
	// code after a delay or at regular intervals
	private final Timer imageUpdateTimer = new Timer() {
		public void run() {
			currentImage = (currentImage + 1) % images.length;
			slideImage.setUrl(path + images[currentImage]);
		}
	};

	// Call this method to start the animation
	public void playAnimation() {
		// Update the image every two seconds
		imageUpdateTimer.scheduleRepeating(5000);
	}

	// Call this method to stop the animation
	public void stopAnimation() {
		imageUpdateTimer.cancel();
	}

}
