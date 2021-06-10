package com.github.michael_sharko.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StaticGson {
    private static final GsonBuilder builder = new GsonBuilder();
    public static Gson gson = builder.create();
}
