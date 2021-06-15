package com.github.michael_sharko.handlers.servlets.UserService;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.data.SubscriptionRequest;
import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.SeparatorForUserIdsAndUsernames;
import com.github.michael_sharko.utils.StaticGson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

public class SubscriptionService {
    private static String generateInsertQuery(SubscriptionRequest[] subscriptions, boolean isReverse) {
        String generateValue = "";
        for (SubscriptionRequest s : subscriptions) {
            String user = s.UserId;
            String follower = s.FollowerId;

            if (!SeparatorForUserIdsAndUsernames.isNumeric(user))
                user = "(SELECT userid FROM users WHERE username = '" + user + "')";
            if (!SeparatorForUserIdsAndUsernames.isNumeric(follower))
                follower = "(SELECT userid FROM users WHERE username = '" + follower + "')";

            generateValue += ",(" + (isReverse ? follower : user) + "," + (isReverse ? user : follower) + ")";
        }
        generateValue = generateValue.substring(1);
        return "INSERT INTO subscriptions(userid, followerid) VALUES" + generateValue;
    }

    private static String generateDeleteQuery(SubscriptionRequest subscription, boolean isReverse) {
        String user = subscription.UserId;
        String follower = subscription.FollowerId;

        if (!SeparatorForUserIdsAndUsernames.isNumeric(user))
            user = "(SELECT userid FROM users WHERE username = '" + user + "')";
        if (!SeparatorForUserIdsAndUsernames.isNumeric(follower))
            follower = "(SELECT userid FROM users WHERE username = '" + follower + "')";

        String generateValue = "userid = " + (isReverse ? follower : user) + " AND followerid = " + (isReverse ? user : follower);
        return "DELETE FROM subscriptions WHERE " + generateValue;
    }

    public static Answer subscribe(SubscriptionRequest[] subscriptions, boolean calledFollowers) throws Exception {
        int result = DatabaseManager.executeSimpleUpdate(generateInsertQuery(subscriptions, calledFollowers));
        if (result > 0)
            return new Answer("Success: subscriptions were successfully made!", null, result);
        //костыль  на 400 при попытке подписать   подписанного пользователя
        throw new InvalidParameterException("User already subscribed");
    }

    public static Answer unsubscribe(SubscriptionRequest[] subscriptions, boolean calledFollowers) throws Exception {
        int result = 0;
        for (SubscriptionRequest s : subscriptions)
            result += DatabaseManager.executeSimpleUpdate(generateDeleteQuery(s, calledFollowers));

        if (result > 0)
            return new Answer("Success: subscriptions were successfully made!", null, result);
        //костыль  на 400 при попытке отписать   неподписанного пользователя
        throw new InvalidParameterException("User not subscribed");
    }

    public static Answer getSubscriptions(String user_ids, boolean calledFollowers) {
        SeparatorForUserIdsAndUsernames separator = new SeparatorForUserIdsAndUsernames(user_ids);
        String query = String.format( "SELECT DISTINCT " + (calledFollowers ? "userid" : "followerid")
                + " FROM subscriptions WHERE " + (calledFollowers ? "followerid" : "userid") + " IN (%s)",user_ids);
        Integer[] result = DatabaseManager.executeQueryToArray(query, Integer.class, (calledFollowers ? "userid" : "followerid"));
        return new Answer("Success: subscriptions were successfully made!", StaticGson.toJson(result));
    }

    public static Answer subscribe(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return subscribe(subscriptions, false);
    }

    public static Answer getSubscriptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user_ids = request.getParameter("user_ids");
        return getSubscriptions(user_ids, false);
    }

    public static Answer unsubscribe(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return unsubscribe(subscriptions, false);
    }

    public static Answer becomeFollower(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return subscribe(subscriptions, true);
    }

    public static Answer getFollowers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user_ids = request.getParameter("user_ids");
        return getSubscriptions(user_ids, true);
    }

    public static Answer stopBeingFollower(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SubscriptionRequest[] subscriptions = StaticGson.readObjectFrom(request, SubscriptionRequest[].class);
        return unsubscribe(subscriptions, true);
    }
}
