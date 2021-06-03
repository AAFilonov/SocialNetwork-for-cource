package org.practical3.utils;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Commons {
    public HttpClient httpClient ;
    public Gson gson;
    public Commons(){
        httpClient = HttpClientBuilder.create().build();
        gson = new Gson();
    }
}
