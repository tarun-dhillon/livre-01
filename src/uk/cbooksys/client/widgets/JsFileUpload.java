package uk.cbooksys.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.Widget;



class COLS{
	String colName;
	public COLS(String colName){
		this.colName = colName;
	}
}


public class JsFileUpload extends Composite {

	private static JsFileUploadUiBinder uiBinder = GWT.create(JsFileUploadUiBinder.class);

	interface JsFileUploadUiBinder extends UiBinder<Widget, JsFileUpload> {
	}

	String jsonText;
	@UiField
	FileUpload uploadFile;
	@UiField static HTMLPanel fileUploadPanel;

	@UiField static NotificationMole notifyMole;

	static long startTime;


	public JsFileUpload() {

		ScriptInjector.fromUrl("/scripts/xlsx/jszip.js").setWindow(ScriptInjector.TOP_WINDOW).setRemoveTag(false).inject();
		ScriptInjector.fromUrl("/scripts/xlsx/xlsx.js").setWindow(ScriptInjector.TOP_WINDOW).setRemoveTag(false).inject();
		ScriptInjector.fromUrl("/scripts/xlsx/my.xlsx.js").setWindow(ScriptInjector.TOP_WINDOW).setRemoveTag(false).inject();
		exportMyFunction();

		initWidget(uiBinder.createAndBindUi(this));

		uploadFile.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				Show.message("Reading the file ...");
				startTime = System.currentTimeMillis();
				onFileReadClick(event.getNativeEvent());

			}
		});
	}




	private static void initTable(ArrayList<HashMap<String,String>> rowList,Set<String> cols) {
		fileUploadPanel.clear();
		fileUploadPanel.add(new DynamicGrid(rowList, cols));
		Show.message("Load Time "+(System.currentTimeMillis()-startTime)+" ms");
	}

	public static native void onFileReadClick(NativeEvent e) /*-{
		$wnd.handleFile(e);
	}-*/;

	public static native void exportMyFunction()/*-{
	  $wnd.handleJson = @uk.cbooksys.client.widgets.JsFileUpload::handleJson(Ljava/lang/String;);
	}-*/;

	public static void handleJson(String json) {

		JSONObject jObject;
		GWT.log(json);
		JSONValue jsonValue = JSONParser.parseStrict(json);
		if ((jObject = jsonValue.isObject()) == null) {
			GWT.log("Error parsing json" + json);
			return;
		}
		JSONObject childObject=null;
		JSONArray childObjects;
		Iterable<String> keys = jObject.keySet();

		ArrayList<HashMap<String,String>> rowList = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> valMap=null;
		Iterable<String> childKeys;
		String val;
		for (String key : keys) {
			GWT.log(key);

			childObjects = jObject.get(key).isArray();
			for (int i = 0; i < childObjects.size(); i++) {
				childObject = childObjects.get(i).isObject();
				childKeys = childObject.keySet();
				valMap = new HashMap<String, String>();
				for (String childKey : childKeys) {

					valMap.put(childKey, val=childObject.get(childKey).isString().stringValue());
					GWT.log(i+" childKey = "+childKey+" value = "+val);
				}
				rowList.add(valMap);
			}
		}
		GWT.log("rows added :: " + rowList.size());

		Show.message("Rendering table..");
		initTable(rowList,childObject.keySet());


	}
}
