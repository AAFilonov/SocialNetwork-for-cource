package org.practical3.utils;


import org.practical3.model.PostField;
import org.practical3.model.Post;
import org.practical3.model.RequestWall;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class PostsDataBaseManager extends DataBaseManager {
    public PostsDataBaseManager(String DB_URL, String DB_Name, String user, String password) throws SQLException {
        super(DB_URL, DB_Name, user, password);
    }

    public String getData() throws SQLException {

        String str = super.Connection.nativeSQL("select * from db.posts");

        Statement statement = super.Connection.createStatement();
        ResultSet result = statement.executeQuery(str);
        String output = "";
        while (result.next()) {
            output += result.getString("content") + "\n";
        }
        return output;
    }


    public Collection<Post> getPosts(Collection<Integer> ids, Collection<PostField> postFields, Integer count, Integer offset) throws SQLException {

        String query = String.format("select %s from db.posts WHERE post_id IN (%s) LIMIT %d OFFSET %d",
                getFieldsAsString(postFields),
                getIdsASString(ids),
                count, offset);
        ResultSet result = super.execute(query);
        ArrayList<Post> items = fetchPosts(result, postFields);
        if (items.isEmpty())
           return null;

        else return items;


    }


    public int insertPosts(Collection<Post> posts) throws SQLException {

        String query = String.format("INSERT INTO db.posts(%s) VALUES %s",
                getFieldsAsString(PostField.getAllFields()),
                getPostsAsString(posts)
        );
        return super.executeUpdate(query);
    }

    public int updatePosts(Collection<Post> posts) throws SQLException {
        throw new NotImplementedException();
        //TODO  метод обновления
       /* String query = String.format( "UPDATE db.posts AS old " +
                        "SET %s  a from (VALEUS %s ) as new(%s) " +
                        "where new.post_id = old.post_id;"
                ,getNotNullFieldsAsString(posts)
                ,getPostsAsString(posts)

        );

        return super.executeUpdate(query);*/

    }


    public void removePosts(Collection<Post> posts) {
        throw new NotImplementedException();
    }


    public void deletePosts(Collection<Post> posts) {
        throw new NotImplementedException();
    }

    public Collection<Post> getWall(RequestWall requestWall) throws SQLException {

        String query = String.format("SELECT %s FROM db.posts " +
                        "WHERE owner_id IN (%s) " +
                        "AND timestamp BETWEEN '%s' AND '%s' " +
                        "LIMIT %d OFFSET %d",

                getFieldsAsString(requestWall.Fields),
                getIdsASString(requestWall.OwnerIds),
                requestWall.DateAfter.toString(), requestWall.DateBefore.toString(),
                requestWall.Count, requestWall.Offset);

        ResultSet result = super.execute(query);
        return fetchPosts(result, requestWall.Fields);
    }


    ArrayList<Post> fetchPosts(ResultSet resultSet, Collection<PostField> postFields) throws SQLException {

        ArrayList<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(fetchPost(resultSet, postFields));
        }
        return posts;
    }


    String getFieldsAsString(Collection<PostField> postFields) {
        if (postFields.isEmpty()) return "*";

        ArrayList<String> vals = new ArrayList<>();
        for (PostField postField : postFields) {
            vals.add("\"" + postField.getVal() + "\"");
        }

        return String.join(",", vals);
    }

    String getIdsASString(Collection<Integer> ids) {
        String output;

        ArrayList<String> idsVals = new ArrayList<>();
        for (Integer id : ids) {
            idsVals.add("'" + id.toString() + "'");
        }

        return String.join(",", idsVals);

    }

    private String getPostsAsString(Collection<Post> posts) {
        Collection<String> postsAsString = new ArrayList<>();
        for (Post post : posts) {
            postsAsString.add(post.toSqlValues());
        }
        return String.join(",", postsAsString);
    }

    Post fetchPost(ResultSet resultSet, Collection<PostField> postFields) throws SQLException {
        Post post = new Post();
        if (postFields.contains(PostField.POST_ID)) post.PostId = resultSet.getInt(PostField.POST_ID.getVal());
        if (postFields.contains(PostField.OWNER_ID)) post.OwnerId = resultSet.getInt(PostField.OWNER_ID.getVal());
        if (postFields.contains(PostField.CONTENT)) post.Content = resultSet.getString(PostField.CONTENT.getVal());
        if (postFields.contains(PostField.TIMESTAMP)) post.Timestamp = resultSet.getDate(PostField.TIMESTAMP.getVal());

        if (postFields.contains(PostField.IS_REDACTED))
            post.IsRedacted = resultSet.getBoolean(PostField.IS_REDACTED.getVal());
        if (postFields.contains(PostField.IS_REMOVED))
            post.IsRemoved = resultSet.getBoolean(PostField.IS_REMOVED.getVal());
        if (postFields.contains(PostField.IS_COMMENTABLE))
            post.IsCommentable = resultSet.getBoolean(PostField.IS_COMMENTABLE.getVal());

        if (postFields.contains(PostField.COUNT_LIKES))
            post.CountLikes = resultSet.getInt(PostField.COUNT_LIKES.getVal());
        if (postFields.contains(PostField.COUNT_REPOSTS))
            post.CountReposts = resultSet.getInt(PostField.COUNT_REPOSTS.getVal());


        return post;
    }


}
