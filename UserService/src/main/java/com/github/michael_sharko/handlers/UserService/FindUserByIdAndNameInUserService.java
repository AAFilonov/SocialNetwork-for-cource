package com.github.michael_sharko.handlers.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.User;
import com.github.michael_sharko.utils.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FindUserByIdAndNameInUserService extends UserService {
    public FindUserByIdAndNameInUserService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        this.req = req;
        this.resp = resp;
    }

    private static String generateQuery(InputParameters inputs) {
        String query = "SELECT * FROM users WHERE %s %s %s ORDER BY userid";
        query = String.format(query,
                "".equals(inputs.userid) ? "" : ("userid IN (" + inputs.userid + ")"),
                "".equals(inputs.userid) || "".equals(inputs.username) ? "" : "OR",
                "".equals(inputs.username) ? "" : ("username IN ('" + inputs.username.replace(", ", "', '") + "')"));

        return query;
    }

    public InputParameters readParametersFrom(HttpServletRequest req) throws IOException {
        InputParameters inputs = new InputParameters(req.getParameter("user_ids"));
        if (inputs.isBlank())
            sendMessage(HttpServletResponse.SC_BAD_REQUEST, new Answer("The userid or username fields must be set", null));

        return inputs;
    }

    @Override
    public void execute() throws IOException {
        InputParameters inputs = readParametersFrom(req);

        ArrayList<User> users = DatabaseManager.executeQueryToArrayList(generateQuery(inputs), User.class);
        sendMessage(HttpServletResponse.SC_OK, new Answer("Success", users));
    }

    public static class InputParameters {
        public String userid;
        public String username;

        public InputParameters(String request) {
            String[] data = (request + ",A").split(",");
            Arrays.sort(data);

            int index = Arrays.binarySearch(data, "A");
            ArrayList<String> listOfIds = new ArrayList<>(Arrays.asList(Arrays.copyOf(data, index)));
            for (int i = 0; i < listOfIds.size(); ++i)
                try {
                    Integer.parseInt(listOfIds.get(i));
                } catch (NumberFormatException nfe) {
                    listOfIds.remove(i);
                }

            userid = listOfIds
                    .toString()
                    .replace("[", "")
                    .replace("]", "");
            username = Arrays.toString((Arrays.copyOfRange(data, index + 1, data.length)))
                    .replace("[", "")
                    .replace("]", "");
        }

        public boolean isBlank() {
            return "".equals(userid) && "".equals(username) || userid == null && username == null;
        }
    }
}
