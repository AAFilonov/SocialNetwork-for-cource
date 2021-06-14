CREATE TABLE subscriptions
(
    subscriptionid  SERIAL PRIMARY KEY,

    userid          INTEGER NOT NULL,
    followerid      INTEGER NOT NULL,

    FOREIGN KEY (userid) REFERENCES users (userid) ON DELETE CASCADE,
    FOREIGN KEY (followerid) REFERENCES users (userid) ON DELETE CASCADE
);