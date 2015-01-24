package uk.cbooksys.client.widgets;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


class FileGrid{
	String id,userName,picture;

	public FileGrid(String id, String userName, String picture) {
		super();
		this.id = id;
		this.userName = userName;
		this.picture = picture;
	}

}



public class MyDataGrid extends Composite  {

	private static MyDataGridUiBinder uiBinder = GWT.create(MyDataGridUiBinder.class);

	interface MyDataGridUiBinder extends UiBinder<Widget, MyDataGrid> {
	}

	@UiField (provided = true)
	DataGrid<FileGrid> dataGrid;

	private static final List<FileGrid> CONTACTS = Arrays.asList(
			new FileGrid("John", "aa", "123 Fourth Avenue"),
			new FileGrid("Joe", "aaBB", "22 Lance Ln"),
			new FileGrid("John", "aa", "123 Fourth Avenue"),
			new FileGrid("Joe", "aaBB", "22 Lance Ln"),
			new FileGrid("John", "aa", "123 Fourth Avenue"),
			new FileGrid("Joe", "aaBB", "22 Lance Ln"),
			new FileGrid("John", "aa", "123 Fourth Avenue"),
			new FileGrid("Joe", "aaBB", "22 Lance Ln"),
			new FileGrid("George", "aaBBcc", "1600 Pennsylvania Avenue"));


	public MyDataGrid() {


		dataGrid = new DataGrid<FileGrid>();
		dataGrid.setVisible(true);
		dataGrid.setSize("100%", "200px");

		TextColumn<FileGrid> idCol = new TextColumn<FileGrid>() {
			@Override
			public String getValue(FileGrid object){
				return object.id;
			}
		};
		dataGrid.addColumn(idCol, "IDx");

		TextColumn<FileGrid> pictureCol = new TextColumn<FileGrid>() {
			@Override
			public String getValue(FileGrid object){
				return object.picture;
			}
		};
		dataGrid.addColumn(pictureCol, "Picture");

		TextColumn<FileGrid> userNameCol = new TextColumn<FileGrid>() {
			@Override
			public String getValue(FileGrid object){
				return object.userName;
			}
		};
		dataGrid.addColumn(userNameCol, "Name");


		//GRID grid = new GRID(colList);



		// need to find out why does this do
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a selection model to handle user selection.
		final SingleSelectionModel<FileGrid> selectionModel = new SingleSelectionModel<FileGrid>();
		dataGrid.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				FileGrid selected = selectionModel.getSelectedObject();
				if (selected != null) {
					Window.alert("You selected: " + selected.userName);
				}
			}
		});

		// Set the total row count. This isn't strictly necessary, but it affects
		// paging calculations, so its good habit to keep the row count up to date.
		dataGrid.setRowCount(CONTACTS.size(), true);

		// Push the data into the widget.
		dataGrid.setRowData(0, CONTACTS);

		initWidget(uiBinder.createAndBindUi(this));
		//uiBinder.createAndBindUi(this);
	}



}
