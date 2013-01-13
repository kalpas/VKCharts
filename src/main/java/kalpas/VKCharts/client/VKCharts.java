package kalpas.VKCharts.client;

import java.util.List;

import kalpas.VKCore.simple.DO.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;

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
    final Label                    errorLabel   = new Label();

    private PieChart               pieChart;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("errorLabelContainer").add(errorLabel);

        Window.enableScrolling(false);
        Window.setMargin("0px");

        // RootPanel.get("testChartConatiner").add(getSimpleLayoutPanel());

        // Create the API Loader
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {

            public void run() {
                RootPanel.get("testChartConatiner").add(getPieChart());
                drawPieChart();
            }
        });

        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                getData();
            }
        };
        refreshTimer.scheduleRepeating(1000);
    }

    private Widget getPieChart() {
        if (pieChart == null) {
            pieChart = new PieChart();
        }
        return pieChart;
    }
    
    DataTable data;

    private void drawPieChart() {
        // Prepare the data
        data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Name");
        data.addColumn(ColumnType.NUMBER, "Donuts eaten");
        data.addRows(4);
        data.setValue(0, 0, "Michael");
        data.setValue(1, 0, "Elisa");
        data.setValue(2, 0, "Robert");
        data.setValue(3, 0, "John");
        data.setValue(0, 1, 5);
        data.setValue(1, 1, 7);
        data.setValue(2, 1, 3);
        data.setValue(3, 1, 2);

        // Draw the chart
        pieChart.draw(data);
    }

    private void getData() {

        dataService.getData("some", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(String result) {
                data.addRow();
                data.setValue(data.getNumberOfRows() - 1, 0, result);
                data.setValue(data.getNumberOfRows() - 1, 1, 10);
                pieChart.draw(data);

            }
        });
        
        dataService.getUsers(new AsyncCallback<List<User>>() {

            public void onFailure(Throwable caught) {
                errorLabel.setText(caught.getMessage());
            }

            public void onSuccess(List<User> result) {
                for (User user : result) {
                    errorLabel.setText(errorLabel.getText() + "\n" + user.last_name);
                }
                
            }
        });

    }

}
