package org.practical3.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.practical3.Main;
import org.practical3.utils.PropertyManager;

import static org.junit.jupiter.api.Assertions.*;

public class PostsServletTest {
    @BeforeEach
    public void init()
    {

        PropertyManager.load("./main.props");
        Main.runServer(8090, "/");
    }

    @Disabled
    @Test
    public void doGet() throws Exception
    {
        String url = "http://localhost:8090/posts?post_ids=1,2&fields=post_id,owner_id,content&count=10&offset=0";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        org.junit.Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }


}