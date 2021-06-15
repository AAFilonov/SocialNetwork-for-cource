DROP TABLE  IF EXISTS db.posts;

CREATE TABLE db.posts(
	"post_id" serial PRIMARY KEY,
	"owner_id" integer NOT NULL,
	"content" text NOT NULL,
	"timestamp" timestamp NOT NULL DEFAULT now(),
	"isRemoved" boolean DEFAULT false,
	isRedacted boolean DEFAULT false,
	"isCommentable" boolean DEFAULT false,
	"CountLikes" integer DEFAULT 0,
	"CountReposts" integer DEFAULT 0
);

