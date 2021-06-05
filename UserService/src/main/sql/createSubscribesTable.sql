CREATE TABLE subscribes
(
	id 				SERIAL PRIMARY KEY,

  	ownerid			INTEGER NOT NULL,
  	subscriberid	INTEGER NOT NULL,

  	FOREIGN KEY (ownerid) REFERENCES users (id) ON DELETE CASCADE,
  	FOREIGN KEY (subscriberid) REFERENCES users (id) ON DELETE CASCADE
);