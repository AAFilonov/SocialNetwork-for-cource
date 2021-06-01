package org.practical3.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.practical3.Main;

import static org.junit.jupiter.api.Assertions.*;

public class MainServletTest {
    @Before
    public void init()
    {
        Main.runServer(8090, "/");
    }

    @Disabled
    @Test
    public void doGet() throws Exception
    {
        String url = "http://localhost:8090/path";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        org.junit.Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }


}