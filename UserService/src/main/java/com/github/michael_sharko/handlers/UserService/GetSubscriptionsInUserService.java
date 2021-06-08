package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class GetSubscriptionsInUserService extends UserService {
    public GetSubscriptionsInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super(req, resp);
    }

    String generateQuery() {
        return "SELECT userid, username FROM users WHERE userid IN " +
                Arrays.toString(user.followers).replace("(", "").replace("]", ")");
    }

    @Override
    public void execute() throws IOException {
        DatabaseManager.executeSimpleUpdate(generateQuery());
    }
}
