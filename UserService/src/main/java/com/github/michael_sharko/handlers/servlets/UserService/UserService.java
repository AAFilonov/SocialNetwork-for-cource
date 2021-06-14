package com.github.michael_sharko.handlers.servlets.UserService;

import com.github.michael_sharko.models.data.User;
import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.SeparatorForUserIdsAndUsernames;
import com.github.michael_sharko.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;

public class UserService {
    private static String generateDeleteQuery(String user_ids) throws Exception {
        SeparatorForUserIdsAndUsernames separator = new SeparatorForUserIdsAndUsernames(user_ids);

        if (separator.isEmpty())
            throw new InvalidParameterException("UserService: Parameter 'userid' or parameter 'username' must be specified!");

        String query = "DELETE FROM users WHERE ";
        if (separator.hasIdentifiers())
            query += "userid IN (" + separator.getIdentifiersString(",") + ") ";
        if (separator.hasIdentifiersAndNames())
            query += "OR";
        if (separator.hasNames())
            query += "username IN (" + separator.getNamesString(",") + ") ";

        return query;
    }

    private static String generateSelectQuery(String user_ids) throws Exception {
        SeparatorForUserIdsAndUsernames separator = new SeparatorForUserIdsAndUsernames(user_ids);

        if (separator.isEmpty())
            throw new InvalidParameterException("UserService: Parameter 'userid' or parameter 'username' must be specified!");

        String query = "SELECT * FROM users WHERE ";
        if (separator.hasIdentifiers())
            query += "userid IN (" + separator.getIdentifiersString(",") + ") ";
        if (separator.hasIdentifiersAndNames())
            query += "OR";
        if (separator.hasNames())
            query += "username IN (" + separator.getNamesString(",") + ") ";

        return query + "ORDER BY userid";
    }

    public static int register(User[] newUsers) {
        int result = 0;
        for (User u : newUsers) {
            if (!u.hasUsername())
                throw new InvalidParameterException("UserService: Parameter 'username' must be specified!");
            else if (!u.hasPassword())
                throw new InvalidParameterException("UserService: Parameter 'password' must be specified!");

            result += DatabaseManager.executeInsertObject("users", u);
        }
        return result;
    }

    public static int update(User[] updatedUsers) {
        int result = 0;
        for (User u : updatedUsers) {
            if (!u.hasUserId())
                throw new InvalidParameterException("UserService: Parameter 'userid' must be specified!");

            result += DatabaseManager.executeUpdateObject("users", u, "WHERE userid = " + u.userid);
        }
        return result;
    }

    public static User[] getUsers(String user_ids) throws Exception {
        return (User[]) DatabaseManager.executeQueryToArrayList(generateSelectQuery(user_ids), User.class).toArray();
    }

    public static int remove(String user_ids) throws Exception {
        return DatabaseManager.executeSimpleUpdate(generateDeleteQuery(user_ids));
    }

    public static int register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User[] newUsers = StaticGson.readObjectFrom(request, User[].class);
        return register(newUsers);
    }

    public static int update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User[] updatedUsers = StaticGson.readObjectFrom(request, User[].class);
        return update(updatedUsers);
    }

    public static User[] getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // String user_ids = StaticGson.readObjectFrom(request, String.class);
        String user_ids = request.getParameter("user_ids");
        return getUsers(user_ids);
    }

    public static int remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_ids = StaticGson.readObjectFrom(request, String.class);
        return remove(user_ids);
    }
}
