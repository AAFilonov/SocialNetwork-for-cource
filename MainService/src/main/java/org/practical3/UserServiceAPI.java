package org.practical3;

import org.apache.http.HttpResponse;
import org.practical3.model.data.User;

import java.io.IOException;
import java.util.Collection;

public class UserServiceAPI {

    public static Collection<Integer> getSubscriptions(String user) throws IOException {
        //HttpResponse response = HttpManager.sendPost("/getsubscriptions", user);
        //return (Collection<Integer>) HttpManager.readResponse(response).getData();
        return null;
    }


}
