package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveInUserService extends UserService {
    public RemoveInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super(req, resp);
    }

    private static String generateQuery(Integer userid, String username) {
        String query = "DELETE FROM users WHERE %s %s %s";
        query = String.format(query,
                userid != null ? ("userid =" + userid) : "",
                userid != null && username != null ? "AND" : "",
                username != null ? ("username ='" + username + "'") : "");

        return query;
    }

    @Override
    public void execute() throws IOException {
        if (DatabaseManager.executeSimpleUpdate(generateQuery(user.userid, user.username)) > 0)
            sendMessage(HttpServletResponse.SC_OK, new Answer("Success: User was deleted successfully", null));
    }
}
