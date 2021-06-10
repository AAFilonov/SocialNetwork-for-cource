
package org.practical3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.practical3.model.data.User;
import org.practical3.utils.PropertyManager;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

public class UserServiceAPI {


    public static Collection<Integer> getSubscriptions(String user) throws IOException {
        //HttpResponse response = HttpManager.sendPost("/getsubscriptions", user);
        //return (Collection<Integer>) HttpManager.readResponse(response).getData();
        return null;
    }


}
