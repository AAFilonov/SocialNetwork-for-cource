package org.practical3.api;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.practical3.model.data.User;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



@Disabled
public class UserServiceAPITest {
    @Disabled
    @Test
    public void getUserReturnNotHashMap() throws IOException {


        ArrayList<User> users = (ArrayList<User> ) UserServiceAPI.getUsers("7");
        assertEquals("java_username",users.get(0).username);

    }
    @Disabled
    @Test
    public void getUserReturnElements() throws IOException {


        ArrayList<User> users = (ArrayList<User> ) UserServiceAPI.getUsers("7");
        assertEquals(1,users.size());

    }
}