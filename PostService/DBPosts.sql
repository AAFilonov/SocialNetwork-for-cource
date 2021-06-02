DROP TABLE  IF EXISTS db.posts;

CREATE TABLE db.posts(
	"post_id" serial PRIMARY KEY,
	"owner_id" integer NOT NULL,
	"content" text NOT NULL,
	"timestamp " date NOT NULL,
	"isRemoved" boolean DEFAULT false,
	"isRedated" boolean DEFAULT false,
	"isCommentable" boolean DEFAULT false,
	"CountLikes" integer DEFAULT 0,
	"CountReposts" integer DEFAULT 0
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

select * from db.posts where post_id IN ('1')
select post_id,owner_id,content from db.posts WHERE post_id IN ('1','2') LIMIT 10 OFFSET 0