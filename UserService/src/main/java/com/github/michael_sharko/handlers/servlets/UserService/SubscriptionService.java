package com.github.michael_sharko.handlers.servlets.UserService;

import com.github.michael_sharko.models.data.SubscriptionRequest;
import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.SeparatorForUserIdsAndUsernames;
import com.github.michael_sharko.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubscriptionService {
    private static String generateInsertQuery(SubscriptionRequest[] subscriptions, boolean isReverse) {
        String generateValue = "";
        for (SubscriptionRequest s : subscriptions) {
            String user = s.user;
            String follower = s.follower;

            if (!SeparatorForUserIdsAndUsernames.isNumeric(user))
                user = "(SELECT userid FROM users WHERE username = '" + user + "')";
            if (!SeparatorForUserIdsAndUsernames.isNumeric(follower))
                follower = "(SELECT userid FROM users WHERE username = '" + follower + "')";

            generateValue += ",(" + (isReverse ? user : follower) + "," + (isReverse ? follower : user) + ")";
        }
        generateValue = generateValue.substring(1);
        return "INSERT INTO subscriptions(userid, followerid) VALUES" + generateValue;
    }

    private static String generateDeleteQuery(SubscriptionRequest subscription, boolean isReverse) {
        String user = subscription.user;
        String follower = subscription.follower;

        if (!SeparatorForUserIdsAndUsernames.isNumeric(user))
            user = "(SELECT userid FROM users WHERE username = '" + user + "')";
        if (!SeparatorForUserIdsAndUsernames.isNumeric(follower))
            follower = "(SELECT userid FROM users WHERE username = '" + follower + "')";

        String generateValue = "userid = " + (isReverse ? user : follower) + " AND followerid = " + (isReverse ? follower : user);
        return "DELETE FROM users WHERE " + generateValue;
    }

    public static Integer subscribe(SubscriptionRequest[] subscriptions, boolean calledFollowers) {
        return DatabaseManager.executeSimpleUpdate(generateInsertQuery(subscriptions, calledFollowers));
    }

    public static Integer unsubscribe(SubscriptionRequest[] subscriptions, boolean calledFollowers) {
        int result = 0;
        for (SubscriptionRequest s : subscriptions)
            result += DatabaseManager.executeSimpleUpdate(generateDeleteQuery(s, calledFollowers));

        return result;
    }

    public static Integer[] getSubscriptions(String user_ids, boolean calledFollowers) {
        SeparatorForUserIdsAndUsernames separator = new SeparatorForUserIdsAndUsernames(user_ids);
        String query = "SELECT DISTINCT " + (calledFollowers ? "followerid" : "userid") + "FROM subscriptions WHERE " + (calledFollowers ? "followerid" : "userid") + " IN (";

        if (separator.hasIdentifiers())
            query += separator.getIdentifiersString(",");
        if (separator.hasIdentifiersAndNames())
            query += ",";
        if (separator.hasNames())
            query += "(SELECT userid FROM users WHERE username IN (" + separator.getIdentifiersString(",") + "))";

        return (Integer[]) DatabaseManager.executeQueryToArrayList(query, Integer[].class).toArray();
    }

    public static Integer subscribe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return subscribe(subscriptions, false);
    }

    public static Integer[] getSubscriptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user_ids = StaticGson.readObjectFrom(request, String.class);
        return getSubscriptions(user_ids, false);
    }

    public static Integer unsubscribe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return unsubscribe(subscriptions, false);
    }

    public static Integer becomeFollower(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return subscribe(subscriptions, true);
    }

    public static Integer[] getFollowers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user_ids = StaticGson.readObjectFrom(request, String.class);
        return getSubscriptions(user_ids, true);
    }

    public static Integer stopBeingFollower(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return unsubscribe(subscriptions, true);
    }
}
