package kalpas.VKCharts.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VKCharts implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String        SERVER_ERROR    = "An error occurred while "
                                                               + "attempting to contact the server. Please check your network "
                                                               + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final DataServiceAsync dataService  = GWT.create(DataService.class);

    private PieChart               pie;
    private DataTable              data;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        // Create a callback to be called when the visualization API
        // has been loaded.
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                Panel panel = RootPanel.get("testChartConatiner");

                // Create a pie chart visualization.
                pie = new PieChart(createTable(), createOptions());

                // pie.addSelectHandler(createSelectHandler(pie));
                panel.add(pie);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
        
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                getData();
            }
          };
        refreshTimer.scheduleRepeating(1000);
    }

    private Options createOptions() {
        Options options = Options.create();
        options.setWidth(400);
        options.setHeight(240);
        options.setTitle("test chart");
        return options;
    }

    private AbstractDataTable createTable() {
        data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Task");
        data.addColumn(ColumnType.NUMBER, "Hours per Day");
        data.addRows(2);
        data.setValue(0, 0, "Work");
        data.setValue(0, 1, 14);
        data.setValue(1, 0, "Sleep");
        data.setValue(1, 1, 10);
        return data;
    }

    private void getData() {
        
        dataService.getData("some", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(String result) {
                data.addRow();
                data.setValue(data.getNumberOfRows() - 1, 0, result);
                data.setValue(data.getNumberOfRows() - 1, 1, 10);
                pie = new PieChart(createTable(), createOptions());

            }
        });
        
    }

}
