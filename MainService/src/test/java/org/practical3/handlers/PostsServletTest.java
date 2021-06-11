package org.practical3.handlers;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.practical3.Main;

import org.practical3.utils.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class PostsServletTest {
    @Before
    public void init() throws Exception {
        Thread newThread = new Thread(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newThread.start();

    }


}