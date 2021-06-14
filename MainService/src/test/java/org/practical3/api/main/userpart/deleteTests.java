package org.practical3.api.main.userpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.User;
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
       // cleanData();
        prepareData();
    }

    @AfterAll
    public static void do_final(){
        cleanData();
    }

    @Test
    public void deleteUserThatDontExistsdoNothing() throws IOException {


        int affectedRows = MainServiceAPI.deleteUser("454");
        assertEquals(0, affectedRows);

    }

    @Test
    public void deleteUserByIdReturnNotZero() throws IOException {


        int affectedRows = MainServiceAPI.deleteUser("451");
        assertEquals(1, affectedRows);

    }

    @Test
    public void deleteUserByLoginReturnNotZero() throws IOException {


        int affectedRows = MainServiceAPI.deleteUser("user452");
        assertEquals(1, affectedRows);

    }

    @Test
    public void deleteUserActuallyDelete() throws IOException {

        MainServiceAPI.deleteUser("453");
        Collection<User> shouldBeEmptyArray = UserServiceAPI.getUsers("453");
        assertTrue(shouldBeEmptyArray.isEmpty());

    }

    public static void prepareData() {

        Collection<User> users = Arrays.asList(
               //new User(451, "User451", "Pass451"),
               //new User(452, "User452", "Pass452"),
                new User(453, "User453", "Pass453")

        );
        try {
            for (User user : users)
                UserServiceAPI.register(user);
        } catch (Exception ioException) {
            //уже вставлен
        }
    };
    public static void cleanData() {
        Collection<Integer> ids = Arrays.asList(451, 452, 453);
       // Collection<Integer> ids = Arrays.asList(453);
        try {
            for (Integer id : ids)
                UserServiceAPI.delete(id);
        } catch (Exception ioException) {
            //уже вставлен
        }
    }

}
