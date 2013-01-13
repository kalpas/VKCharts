package kalpas.VKCharts.server;

import java.util.List;

import kalpas.VKCharts.client.DataService;
import kalpas.VKCore.VKModule;
import kalpas.VKCore.simple.DO.User;
import kalpas.VKCore.simple.VKApi.Friends;
import kalpas.VKCore.simple.helper.AuthHelper;
import kalpas.VKCore.simple.helper.HttpClientContainer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    private Injector injector = Guice.createInjector(new VKModule());

    public String getData(String param) {
        return "smth";
    }

    public List<User> getUsers() {
        HttpClientContainer container=null;
        List<User> friendsList;
        try{
            container = injector.getInstance(HttpClientContainer.class);
            Friends friends = injector.getInstance(Friends.class);
            String selfUid = injector.getInstance(AuthHelper.class).getSelfUid();
            friendsList = friends.get(selfUid);
        }finally {
            try {
                container.shutdown();
            } catch (Exception e) {
            }
        }
        return friendsList;
    }
}
