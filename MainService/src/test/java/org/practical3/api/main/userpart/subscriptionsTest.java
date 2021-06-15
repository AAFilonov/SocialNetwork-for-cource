package org.practical3.api.main.userpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


//очень медленные тесты
public class subscriptionsTest {
    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        Collection<User> users = Arrays.asList(
                new User(461, "User461", "Pass481"),
                new User(462, "User462", "Pass482"),
                new User(463, "User463", "Pass483"),
                new User(464, "User464", "Pass483"),
                new User(465, "User465", "Pass483"),
                new User(466, "User466", "Pass483")

        );
        TestUtils.createUsers(users);

    }

    @AfterAll
    public static void cleanup() {
        TestUtils.cleanUsers("461,462,463,464,465,466");
    }

    @Test
    public void subscribe_WhenUnsubscribedUser_ShouldReturn200() throws IOException {
        SubscriptionRequest request = new SubscriptionRequest(462,461);
        HttpResponse response = MainServiceAPI.subscribeUser(request);

        assertEquals(200, response.getStatusLine().getStatusCode());
    }



    @Test
    public void subscribeSubscribedUserShouldReturnFalse() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(464,463));
        HttpResponse response = MainServiceAPI.subscribeUser(new SubscriptionRequest(464,463));
        //на самом деле создат еще одну звупись
        assertEquals(200, response.getStatusLine().getStatusCode());
    }


    @Test
    public void unsubscribe_SubscribedUser_ShouldReturnTrue() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(463,462));
        HttpResponse response =  MainServiceAPI.unsubscribeUser(new SubscriptionRequest(463,462));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void unsubscribe_UnsubscribedUser_ShouldReturn400() throws IOException {
        HttpResponse response =   MainServiceAPI.unsubscribeUser(new SubscriptionRequest(465,461));

        assertEquals(400, response.getStatusLine().getStatusCode());
    }


    @Test
    public void getSubscriptions_WhenReallySubscribed_ShouldReturn200() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(465,462));
        HttpResponse response =  MainServiceAPI.getSubscriptions("465");
        Collection<Integer> ids = ResponseReader.getIntegerCollection(response);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }



    @Test
    public void getSubscriptions_WhenNotSubscribed_ShouldReturn404() throws IOException {
        HttpResponse response =  MainServiceAPI.getSubscriptions("461");

        assertEquals(404, response.getStatusLine().getStatusCode());

    }



    @Test
    public void getSubscriptionsReturnActuallySubscribedUsers() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(466,461));
        MainServiceAPI.subscribeUser(new SubscriptionRequest(466,462));
        HttpResponse response =  MainServiceAPI.getSubscriptions("466");

        ArrayList<Integer> ids =(ArrayList<Integer>) ResponseReader.getIntegerCollection(response);
        assertEquals(461, ids.get(0));
        assertEquals(462, ids.get(1));
    }


}
