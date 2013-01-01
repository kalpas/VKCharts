package kalpas.VKCharts.server;

import kalpas.VKCharts.client.DataService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    public String getData(String param) {
        return "smth";
    }

}
