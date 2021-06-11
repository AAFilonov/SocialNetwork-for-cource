package com.github.michael_sharko.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static Connection connection;

    public static void connectTo(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static Connection getConnection() {
        try {
            if (connection == null)
                throw new SQLException("The connection was not established!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    private static <T> boolean isFieldOfType(Field field, Class<T> clazz) {
        return clazz.getName().equals(field.getType().getName());
    }

    public static <T> ArrayList<T> executeQueryToArrayList(String sql, Class<T> classOfT) {
        ArrayList<T> result = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            result = new ArrayList<>();
            Field[] fields = classOfT.getFields();

            while (resultSet.next()) {
                T instance = classOfT.newInstance();
                for (Field field : fields) {
                    Object value = null;
                    String fieldName = field.getName();

                    try {
                        if (isFieldOfType(field, String.class)) {
                            if (resultSet.findColumn(fieldName) > 0)
                                value = resultSet.getString(fieldName);
                        } else if (isFieldOfType(field, Integer.class)) {
                            if (resultSet.findColumn(fieldName) > 0)
                                value = resultSet.getInt(fieldName);
                        } else if (isFieldOfType(field, java.sql.Date.class)) {
                            if (resultSet.findColumn(fieldName) > 0)
                                value = resultSet.getDate(fieldName);
                        } else if (isFieldOfType(field, Integer[].class)) {
                            if (resultSet.findColumn(fieldName) > 0) {
                                Array array = resultSet.getArray(fieldName);
                                if (array != null)
                                    value = array.getArray();
                            }
                        } else
                            throw new SQLException(fieldName + "'s field contains an unknown data type");
                    } catch (SQLException e) {
                        continue;
                    }

                    field.set(instance, value);
                }
                result.add(instance);
            }
            statement.close();
        } catch (SQLException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static <T> ArrayList<T> executeQueryToArrayList(String sql, T type, String column) {
        ArrayList<Object> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                if (resultSet.findColumn(column) > 0) {
                    if (type.getClass().getTypeName().equals(String.class.getTypeName()))
                        result.add(resultSet.getString(column));
                    else if (type.getClass().getTypeName().equals(Integer.class.getTypeName()))
                        result.add(resultSet.getInt(column));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return (ArrayList<T>) result;
    }

    public static int executeSimpleUpdate(String sql) {
        int result = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return result;
    }

    public static <T> int executeUpdate(String sql, T object) {
        int result = -1;
        try {
            Class<?> classOfT = object.getClass();
            Field[] fields = classOfT.getFields();
            Object value = null;

            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName();
                value = field.get(object);

                if (value == null)
                    continue;

                if (isFieldOfType(field, String.class))
                    statement.setString(index, (String) value);

                else if (isFieldOfType(field, Integer.class))
                    statement.setInt(index, (Integer) value);

                else if (isFieldOfType(field, java.sql.Date.class))
                    statement.setDate(index, (java.sql.Date) value);

                else if (isFieldOfType(field, Integer[].class)) {
                    Array array = connection.createArrayOf("INTEGER", (Integer[]) value);
                    statement.setArray(index, array);
                } else
                    throw new SQLException(fieldName + "'s field contains an unknown data type");

                ++index;
            }

            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static <T> int executeInsertObject(String table, T object, String where) {
        int result = 0;
        try {
            String query = "INSERT INTO %s(%s) VALUES(%s) %s";

            String vars = "";
            String values = "";

            for (Field field : object.getClass().getFields()) {
                if (field.get(object) != null) {
                    vars += (!vars.equals("") ? ',' : "") + field.getName();
                    values += (!values.equals("") ? ',' : "") + "?";
                }
            }

            query = String.format(query, table, vars, values, where == null ? "" : where);
            result = executeUpdate(query, object);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> int executeUpdateObject(String table, T object, String where) {
        int result = 0;
        try {
            String query = "UPDATE %s SET %s %s";
            String data = "";

            for (Field field : object.getClass().getFields())
                if (field.get(object) != null) // to comment -> set ALL fields including null-elements
                    data += (!data.equals("") ? ',' : "") + field.getName() + "=?";

            query = String.format(query, table, data, where == null ? "" : where);
            result = executeUpdate(query, object);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void disconnect() {
        try {
            if (connection != null)
                connection.close();

        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }
}
