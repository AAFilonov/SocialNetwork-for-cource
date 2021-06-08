DROP TABLE  IF EXISTS db.posts;

CREATE TABLE db.posts(
	"post_id" serial PRIMARY KEY,
	"owner_id" integer NOT NULL,
	"content" text NOT NULL,
	"timestamp" timestamp NOT NULL DEFAULT now(),
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



insert into db.posts(owner_id,"content",) VALUES(0,'First post',now()), (0,'Second post');
select * FROM db.posts;

select * from db.posts where post_id IN ('1')
select post_id,owner_id,content from db.posts WHERE post_id IN ('1','2') LIMIT 10 OFFSET 0



update db.posts as t set
                         "content" = COALESCE("content" ,  t."content" ) ,
                         "CountLikes" = COALESCE("CountLikes",  t."CountLikes" )
    from (values
  (27517, 'a1111' ,Null),
  (27516, Null, 222)
) as data(post_id, "content", "CountLikes")
where t.post_id = post_id

select *from db.posts where posts.owner_id=400