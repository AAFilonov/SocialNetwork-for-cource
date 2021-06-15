package org.practical3.api.main.userpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.utils.TestUtils;
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
    public void subscribeUnsubscribedUserShouldReturnTrue() throws IOException {
        SubscriptionRequest request = new SubscriptionRequest(462,461);
        boolean isOk =  MainServiceAPI.subscribeUser(request);
        assertTrue(isOk);
    }
    @Test
    public void subscribeSubscribedUserShouldReturnFalse() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(464,463));
        boolean isOk =  MainServiceAPI.subscribeUser(new SubscriptionRequest(464,463));
        assertFalse(isOk);
    }

    @Test
    public void ussubscribeSubscribedUserShouldReturnTrue() throws IOException {
         MainServiceAPI.subscribeUser(new SubscriptionRequest(463,462));
        boolean isOk =  MainServiceAPI.unsubscribeUser(new SubscriptionRequest(463,462));
        assertTrue(isOk);
    }

    @Test
    public void ussubscribeUnsubscribedUserShouldReturnTrue() throws IOException {
        boolean isOk =  MainServiceAPI.unsubscribeUser(new SubscriptionRequest(465,461));
        assertFalse(isOk);
    }


    @Test
    public void getSubscriptionsReturnnotEmptyWhenReallySubscribed() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(465,462));
        Collection<Integer> ids=  MainServiceAPI.getSubscriptions("465");
        assertFalse(ids.isEmpty());
    }

    @Test
    public void getSubscriptionsReturnEmptyWhenNotSubscribed() throws IOException {
        Collection<Integer> ids=  MainServiceAPI.getSubscriptions("461");
        assertTrue(ids.isEmpty());
    }

    @Test
    public void getSubscriptionsReturnActuallySubscribedUsers() throws IOException {
        MainServiceAPI.subscribeUser(new SubscriptionRequest(466,461));
        MainServiceAPI.subscribeUser(new SubscriptionRequest(466,462));
        ArrayList<Integer> ids=  (ArrayList)MainServiceAPI.getSubscriptions("466");
        assertEquals(461, ids.get(0));
        assertEquals(462, ids.get(1));
    }


}
