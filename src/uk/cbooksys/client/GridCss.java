package uk.cbooksys.client;

import com.google.gwt.user.cellview.client.DataGrid;

public interface GridCss extends DataGrid.Resources {
	public interface GridStyle extends DataGrid.Style {}
	//@Source({ DataGrid.Style.DEFAULT_CSS, "data_grid.css" })
	@Override
	@Source("data_grid.css")
	GridStyle dataGridStyle();
};