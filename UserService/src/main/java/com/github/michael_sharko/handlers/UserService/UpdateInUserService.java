package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateInUserService extends UserService {
    public UpdateInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super(req, resp);
    }

    @Override
    public void execute() throws IOException {
        DatabaseManager.executeUpdateObject("users", user, "WHERE userid = " + user.userid);
        sendMessage(HttpServletResponse.SC_OK, new Answer("Success: User was updated successfully", null));
    }
}
