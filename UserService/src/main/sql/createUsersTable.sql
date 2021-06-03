CREATE TABLE users
(
	id			SERIAL PRIMARY KEY,

  	username	VARCHAR(16) UNIQUE,
  	password	VARCHAR(32),
  	birthday	DATE,

  	fullname	TEXT,
  	sex			SMALLINT DEFAULT 0,

  	country		TEXT,
  	city		TEXT,
  	school		TEXT,
  	university	TEXT
);