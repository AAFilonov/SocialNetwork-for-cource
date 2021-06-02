/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.michael_sharko.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author A.Khalaev
 */
public class PropertyManager
{
    private static final Properties properties = new Properties();

    public static Map<String, String> getPropertiesByPrefix(String prefix){
        Map<String, String> map = new HashMap<>();
        
        for(String name: properties.stringPropertyNames()){
            if(name.startsWith(prefix)){
                map.put(name.substring(prefix.length()), properties.getProperty(name));
            }
        }
        
        return map;
    }

    public static void load(String propsPath)
    {
        try {
            properties.load(new FileInputStream(propsPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //PropertyConfigurator.configure(properties);
    }
    public static Integer getPropertyAsInteger(String name, Integer defaultVal) throws PropertyNotFindException {
        return Integer.decode(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    public static Boolean getPropertyAsBoolean(String name, Boolean defaultVal) throws PropertyNotFindException {
        return toBoolean(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    private static boolean toBoolean(String val) {
	return ((val != null) && val.equalsIgnoreCase("true"));
    }

    public static Long getPropertyAsLong(String name, Long defaultVal) throws PropertyNotFindException {
        return Long.decode(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    public static String getPropertyAsString(String name, String defaultVal) throws PropertyNotFindException {
        String value = properties.getProperty(name);
        if (value == null) {
            System.out.println("Can't find property " + name + ", use default value " +  defaultVal);
            return defaultVal;
        }
        return value;
    }

    public static boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    public static final class PropertyNotFindException extends RuntimeException
    {
        public PropertyNotFindException(String message) {
            super(message);
        }
    }
}
