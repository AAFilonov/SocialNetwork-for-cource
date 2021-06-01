DROP TABLE  IF EXISTS db.posts;
CREATE TABLE db.posts(
	"post_id" serial PRIMARY KEY,
	"ownDROP TABLE  IF EXISTS db.posts;
CREATE TABLE db.posts(
	"post_id" serial PRIMARY KEY,
	"owner_id" integer NOT NULL,
	"content" text NOT NULL,
	"isRemoved" boolean DEFAULT false,
	"isRedated" boolean DEFAULT false,
	"isCommentable" boolean DEFAULT false,
	"likeCount" integer DEFAULT 0,
	"repostCount" integer DEFAULT 0
);

SELECT
   table_name,
   column_name,
   data_type
FROM
   information_schema.columns
WHERE
   table_name = ''posts'';

insert into db.posts(owner_id,"content") VALUES(0,''First post''), (0,''Second post'');
select * FROM db.posts;er_id" integer NOT NULL,
	"content" text NOT NULL,
	"isRemoved" boolean DEFAULT false,
	"isRedated" boolean DEFAULT false,
	"isCommentable" boolean DEFAULT false,
	"likeCount" integer DEFAULT 0,
	"repostCount" integer DEFAULT 0
);

SELECT
   table_name,
   column_name,
   data_type
FROM
   information_schema.columns
WHERE
   table_name = 'posts';

insert into db.posts(owner_id,"content") VALUES(0,'First post'), (0,'Second post');
select * FROM db.posts;