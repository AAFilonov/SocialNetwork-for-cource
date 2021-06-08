package com.github.michael_sharko.handlers.UserService.SubscriptionsUserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveInSubscriptionsUserService extends SubscriptionsUserService {
    public RemoveInSubscriptionsUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super(req, resp);
    }

    private String generateQuery() {
        String query = "call unsubscribe(%d, %d)";
        query = String.format(query, subscription.followerid, subscription.userid);

        return query;
    }

    @Override
    public void execute() throws IOException {
        if (DatabaseManager.executeSimpleUpdate(generateQuery()) > 0)
            sendMessage(HttpServletResponse.SC_OK, new Answer("Success: User was updated successfully", null));
    }
}
