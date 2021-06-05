package org.practical3;

import org.practical3.utils.PropertyManager;

public class UserServiceAPI {
    static String baseURL = PropertyManager.getPropertyAsString("service.users.addr","http://localhost:8080");
}
