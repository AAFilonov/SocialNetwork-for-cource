package org.practical3.logic;


import org.practical3.model.data.Post;
import org.practical3.model.data.PostField;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DataBaseManager {

    Connection Connection;

    public java.sql.Connection getConnection() {
        return Connection;
    }

    public DataBaseManager(String DB_URL, String DB_Name, String User, String Password) throws SQLException {
        this.Connection = DriverManager.getConnection(DB_URL + DB_Name
                , User, Password);
        System.out.println("Connection to " +DB_URL+ DB_Name +" successfully opened");

    }

    public void close() {
        try {
            if (Connection != null) {
                Connection.close();
                System.out.println("DB connection successfully closed");
            }
        } catch (Exception e) {
            System.out.println("Error while close connection: \n" + e.getMessage());
        }
    }
    public int executeAndReturnInt(String sql) throws ClassNotFoundException, SQLException {
        Statement statement = Connection.createStatement();
        try {
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new ClassNotFoundException();
        } finally {
            statement.close();
        }
    }
    Post fetchPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.PostId = resultSet.getInt(PostField.POST_ID.getVal());
        post.OwnerId = resultSet.getInt(PostField.OWNER_ID.getVal());
        post.Content = resultSet.getString(PostField.CONTENT.getVal());
        post.Timestamp = resultSet.getTimestamp(PostField.TIMESTAMP.getVal()).toInstant();
        post.IsRedacted = resultSet.getBoolean(PostField.IS_REDACTED.getVal());
        post.IsRemoved = resultSet.getBoolean(PostField.IS_REMOVED.getVal());
        post.IsCommentable = resultSet.getBoolean(PostField.IS_COMMENTABLE.getVal());
        post.CountLikes = resultSet.getInt(PostField.COUNT_LIKES.getVal());
        post.CountReposts = resultSet.getInt(PostField.COUNT_REPOSTS.getVal());


        return post;
    }

    ArrayList<Post> fetchPosts(ResultSet resultSet) throws SQLException {

        ArrayList<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(fetchPost(resultSet));
        }
        return posts;
    }


    String getIdsASString(Collection<Integer> ids) {
        String output;

        ArrayList<String> idsVals = new ArrayList<>();
        for (Integer id : ids) {
            idsVals.add("'" + id.toString() + "'");
        }

        return String.join(",", idsVals);

    }

    public String getPostsAsString(Collection<Post> posts) {
        Collection<String> postsAsString = new ArrayList<>();
        for (Post post : posts) {
            postsAsString.add(post.toSqlValues());
        }
        return String.join(",", postsAsString);
    }




}
