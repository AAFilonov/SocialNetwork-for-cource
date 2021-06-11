package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegistrationInUserService extends UserService {
    public RegistrationInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super(req, resp);
    }

    @Override
    public void execute() throws IOException {
        if (DatabaseManager.executeInsertObject("users", user, null) > 0)
            sendMessage(HttpServletResponse.SC_OK, new Answer("Success: New user was registered successfully", null));
    }
}
