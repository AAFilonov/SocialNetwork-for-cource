package org.practical3;

import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.PropertyManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

public class UserServiceAPI {
    static String baseURL = PropertyManager.getPropertyAsString("service.users.addr","http://localhost:8080");


    public static Collection<Integer> getSubscriptions(String username) throws Exception {
        //вернерт коллекцию id-шников пользовательей, на которых подписан заданный
        throw  new NotImplementedException();
    }
}
