package org.practical3.api;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.practical3.Main;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainServiceAPITest {
    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
    }




}