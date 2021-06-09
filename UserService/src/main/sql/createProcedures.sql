CREATE OR REPLACE PROCEDURE subscribe(follower INTEGER, toSubscribe INTEGER) LANGUAGE SQL AS 
$$
	UPDATE users SET subscriptions = (array_append((SELECT subscriptions FROM users WHERE userid = follower), toSubscribe)) WHERE userid = follower;
	UPDATE users SET followers = (array_append((SELECT followers FROM users WHERE userid = toSubscribe), follower)) WHERE userid = toSubscribe;
$$;

CREATE OR REPLACE PROCEDURE unsubscribe(follower INTEGER, toUnsubscribe INTEGER) LANGUAGE SQL AS 
$$
	UPDATE users SET subscriptions = (array_remove((SELECT subscriptions FROM users WHERE userid = follower), toUnsubscribe)) WHERE userid = follower;
	UPDATE users SET followers = (array_remove((SELECT followers FROM users WHERE userid = toUnsubscribe), follower)) WHERE userid = toUnsubscribe;
$$;