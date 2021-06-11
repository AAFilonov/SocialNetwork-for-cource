CREATE OR REPLACE PROCEDURE subscribe(follower INTEGER, toSubscribe INTEGER) AS
$$
	UPDATE users SET subscriptions = (array_append((SELECT subscriptions FROM users WHERE userid = follower), toSubscribe)) WHERE userid = follower;
	UPDATE users SET followers = (array_append((SELECT followers FROM users WHERE userid = toSubscribe), follower)) WHERE userid = toSubscribe;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE unsubscribe(follower INTEGER, toUnsubscribe INTEGER) AS
$$
	UPDATE users SET subscriptions = (array_remove((SELECT subscriptions FROM users WHERE userid = follower), toUnsubscribe)) WHERE userid = follower;
	UPDATE users SET followers = (array_remove((SELECT followers FROM users WHERE userid = toUnsubscribe), follower)) WHERE userid = toUnsubscribe;
$$ LANGUAGE plpgsql;