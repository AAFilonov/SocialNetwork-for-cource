package org.practical3.api.main.userpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.utils.ServiceException;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class deleteTests {
    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        Collection<User> users = Arrays.asList(
                new User(451, "User451", "Pass451"),
                new User(452, "User452", "Pass452"),
                new User(453, "User453", "Pass453")

        );
        TestUtils.createUsers(users);

    }

    @AfterAll
    public static void cleanup() {
        TestUtils.cleanUsers("451,452,453");
    }


    @Test
    public void deleteUser_NotrRealUser_Return400() throws IOException {
        HttpResponse response =  MainServiceAPI.deleteUser("454");
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void delete_RealUser_Return200() throws IOException {
        HttpResponse response =  MainServiceAPI.deleteUser("451");
        assertEquals(200, response.getStatusLine().getStatusCode());

    }



    @Test
    public void deleteUserActuallyDelete() throws IOException, ServiceException {

        MainServiceAPI.deleteUser("453");
        Collection<User> shouldBeEmptyArray = UserServiceAPI.getUsers("453");
        assertTrue(shouldBeEmptyArray.isEmpty());

    }



}
