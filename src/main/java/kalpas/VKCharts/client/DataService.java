package kalpas.VKCharts.client;


import java.util.List;

import kalpas.VKCore.simple.DO.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("get_data")
public interface DataService extends RemoteService {
    String getData(String param);

    List<User> getUsers();
}
