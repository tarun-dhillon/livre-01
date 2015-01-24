package uk.cbooksys.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

class IndexedColumn extends Column<List<String>, String> {
	private final int index;

	public IndexedColumn(int index) {
		super(new EditTextCell());
		this.index = index;
	}

	@Override
	public String getValue(List<String> object) {
		return object.get(this.index);
	}
}

public class DynamicGrid extends Composite {

	private static DynamicGridUiBinder uiBinder = GWT.create(DynamicGridUiBinder.class);

	interface DynamicGridUiBinder extends UiBinder<Widget, DynamicGrid> {
	}

	@UiField(provided = true)
	DataGrid<List<String>> dataGrid;
	@UiField
	VerticalPanel dataGridPanel;

	public DynamicGrid(List<HashMap<String, String>> gridList, Set<String> keys) {

		dataGrid = new DataGrid<List<String>>();

		initCols(dataGrid, gridList, keys);

		initWidget(uiBinder.createAndBindUi(this));

		SimplePager pager = new SimplePager();
		pager.setDisplay(dataGrid);
		dataGridPanel.add(pager);

	}

	private void initCols(DataGrid<List<String>> dataGrid, List<HashMap<String, String>> gridList, Set<String> keys) {

		dataGrid.setSize("100%", "200px");

		dataGrid.setPageSize(30);
		int i = 0;
		// add column headers
		for (String key : keys) {
			dataGrid.addColumn(new IndexedColumn(i++), new TextHeader(key));
		}

		GWT.log("gridList size " + gridList.size() + " " + gridList.toString());

		ArrayList<String> rowList;
		List<List<String>> rows = new ArrayList<List<String>>();
		for (HashMap<String, String> rowMap : gridList) {
			GWT.log("rowMap size" + rowMap.size() + " " + rowMap.toString());
			rowList = new ArrayList<String>();
			for (String key : keys) {
				rowList.add(rowMap.get(key));
				GWT.log("Added - " + rowMap.get(key));
			}
			rows.add(rowList);
		}

		final ListDataProvider<List<String>> provider = new ListDataProvider<List<String>>(rows);
		provider.addDataDisplay(dataGrid);

		dataGrid.setRowCount(gridList.size(), true);



	}

}
