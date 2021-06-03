package org.practical3.utils;


import org.practical3.model.Field;
import org.practical3.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class PostsDataBaseManager extends DataBaseManager {
    public PostsDataBaseManager(String DB_URL, String DB_Name, String user, String password) throws SQLException {
        super(DB_URL,DB_Name,user,password);
    }

    public String  getData() throws SQLException {

        String str = super.Connection.nativeSQL("select * from db.posts");

        Statement statement = super.Connection.createStatement();
        ResultSet result = statement.executeQuery(str);
        String output = "";
        while (result.next()) {
            output += result.getString("content") + "\n";
        }
        return  output;
    }



    public Collection<Post> getPosts(Collection<Integer> ids, Collection<Field> fields, Integer count, Integer offset) throws SQLException, ClassNotFoundException {

       String query = String.format( "select %s from db.posts WHERE post_id IN (%s) LIMIT %d OFFSET %d" ,
               getFieldsAsString(fields),
               getIdsASString(ids),
               count, offset);
       ResultSet result =  super.execute(query);
        ArrayList<Post> items = fetchPosts(result, fields);
        if( items.isEmpty())
            throw  new ClassNotFoundException();

        else return items;


    }


    public int insertPosts(Collection<Post> posts) throws SQLException {

        String query = String.format( "INSERT INTO db.posts(%s) VALUES %s",
                getFieldsAsString(Field.getAllFields()),
                getPostsAsString(posts)
        );
        return super.executeUpdate(query);
    }
    
    public int updatePosts(Collection<Post> posts) throws SQLException {
        //TODO  метод обновления
       /* String query = String.format( "UPDATE db.posts AS old " +
                        "SET %s  a from (VALEUS %s ) as new(%s) " +
                        "where new.post_id = old.post_id;"
                ,getNotNullFieldsAsString(posts)
                ,getPostsAsString(posts)

        );

        return super.executeUpdate(query);*/
        return 0;
    }




    public void removePosts(Collection<Post> posts){

    }


    public void deletePosts(Collection<Post> posts){

    }




    ArrayList<Post>  fetchPosts(ResultSet resultSet, Collection<Field> fields) throws SQLException {

        ArrayList<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(fetchPost(resultSet,fields));
        }
        return posts;
    }



    String getFieldsAsString(Collection<Field> fields){
        if(fields.isEmpty()) return "*";

        ArrayList<String> vals = new ArrayList<>();
        for (Field field:fields) {
            vals.add("\""+field.getVal()+"\"");
        }

        return String.join(",", vals);
    }
    String getIdsASString(Collection<Integer> ids){
        String output;

        ArrayList<String> idsVals = new ArrayList<>();
        for (Integer id:ids) {
            idsVals.add("'" +id.toString()+"'");
        }

        return String.join(",", idsVals);

    }

    private String getPostsAsString(Collection<Post> posts) {
        Collection<String> postsAsString = new ArrayList<>();
        for (Post post:posts) {
            postsAsString.add(post.toSqlValues());
        }
        return String.join(",", postsAsString);
    }

    Post fetchPost (ResultSet resultSet, Collection < Field > fields) throws SQLException {
        Post post = new Post();
        if (fields.contains(Field.POST_ID)) post.PostId = resultSet.getInt(Field.POST_ID.getVal());
        if (fields.contains(Field.OWNER_ID)) post.OwnerId = resultSet.getInt(Field.OWNER_ID.getVal());
        if (fields.contains(Field.CONTENT)) post.Content = resultSet.getString(Field.CONTENT.getVal());
        if (fields.contains(Field.TIMESTAMP)) post.Timestamp = resultSet.getDate(Field.TIMESTAMP.getVal());

        if (fields.contains(Field.IS_REDACTED)) post.IsRedacted = resultSet.getBoolean(Field.IS_REDACTED.getVal());
        if (fields.contains(Field.IS_REMOVED)) post.IsRemoved = resultSet.getBoolean(Field.IS_REMOVED.getVal());
        if (fields.contains(Field.IS_COMMENTABLE)) post.IsCommentable = resultSet.getBoolean(Field.IS_COMMENTABLE.getVal());

        if (fields.contains(Field.COUNT_LIKES)) post.CountLikes = resultSet.getInt(Field.COUNT_LIKES.getVal());
        if (fields.contains(Field.COUNT_REPOSTS)) post.CountReposts = resultSet.getInt(Field.COUNT_REPOSTS.getVal());



        return post;
    }


}
