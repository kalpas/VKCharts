package kalpas.VKCharts.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("get_data")
public interface DataService extends RemoteService {
    String getData(String param);
}
