package com.github.michael_sharko.handlers.servlets.UserService;

import com.github.michael_sharko.models.Answer;
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
            query += "OR ";
        if (separator.hasNames())
            query += "username IN ('" + separator.getNamesString("','") + "') ";

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
            query += " username IN ('" + separator.getNamesString("','") + "') ";

        return query + "ORDER BY userid";
    }

    public static Answer register(User[] newUsers) throws Exception {
        int result = 0;
        for (User u : newUsers) {
            if (!u.hasUsername())
                throw new InvalidParameterException("UserService: Parameter 'username' must be specified!");
            else if (!u.hasPassword())
                throw new InvalidParameterException("UserService: Parameter 'password' must be specified!");

            try {
                result += DatabaseManager.executeInsertObject("users", u);
            } catch (Exception e) {
                //костыльная проверка на фейл вставки юезров с одинаковым id
                throw new InvalidParameterException("User with username" + u.username + " already exists");
            }
        }
        if (result > 0)
            return new Answer("Success: users have been registered successfully!", null, result);
        throw new Exception("UserService: Unknown exception on a register attempt");
    }

    public static Answer update(User[] updatedUsers) throws Exception {
        int result = 0;
        for (User u : updatedUsers) {
            if (!u.hasUserId())
                throw new InvalidParameterException("UserService: Parameter 'userid' must be specified!");


            result += DatabaseManager.executeUpdateObject("users", u, "WHERE userid = " + u.userid);

        }
        if (result > 0)
            return new Answer("Success: users have been successfully updated!", null, result);
        throw new Exception("UserService.update send that exception, result: " + result);
    }

    public static Answer getUsers(String user_ids) throws Exception {
        User[] result = DatabaseManager.executeQueryToArray(generateSelectQuery(user_ids), User.class);
        return new Answer("Success: users were found successfully!", StaticGson.toJson(result));
    }

    public static Answer remove(String user_ids) throws Exception {
        int result = DatabaseManager.executeSimpleUpdate(generateDeleteQuery(user_ids));
        if (result > 0)
            return new Answer("Success: users were deleted successfully!", null, result);
        throw new Exception("UserService.remove send that exception, result: " + result);
    }

    public static Answer register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User[] newUsers = StaticGson.readObjectFrom(request, User[].class);
        return register(newUsers);
    }

    public static Answer update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User[] updatedUsers = StaticGson.readObjectFrom(request, User[].class);
        return update(updatedUsers);
    }

    public static Answer getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_ids = request.getParameter("user_ids");
        return getUsers(user_ids);
    }

    public static Answer remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_ids = StaticGson.readObjectFrom(request, String.class);
        return remove(user_ids);
    }
}
