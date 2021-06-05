package org.practical3.utils;


import org.practical3.model.PostField;
import org.practical3.model.Post;
import org.practical3.model.RequestWall;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDate;
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


    public Collection<Post> getPosts(Collection<Integer> ids, Integer count, Integer offset) throws SQLException, ClassNotFoundException {

        String query = String.format("select * from db.posts WHERE post_id IN (%s) LIMIT %d OFFSET %d",

                getIdsASString(ids),
                count, offset);
        ResultSet result = super.execute(query);
        ArrayList<Post> items = fetchPosts(result);
        if (items.isEmpty())
            throw new ClassNotFoundException();

        else return items;


    }


    public int insertPosts(Collection<Post> posts) throws SQLException {

        String query = String.format("INSERT INTO db.posts VALUES %s",
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


    public int deletePosts(Collection<Integer> ids) throws SQLException {

        String query = String.format("DELETE from db.posts WHERE post_id IN (%s)",
                getIdsASString(ids));

        return super.executeUpdate(query);

    }

    public Collection<Post> getWall(RequestWall requestWall) throws SQLException {


        PreparedStatement statement = super.Connection.prepareStatement("SELECT * FROM db.posts " +
                "WHERE owner_id IN (?) " +
                "AND timestamp BETWEEN ? AND ?" +
                "LIMIT %d OFFSET %d");

        statement.setString(1,getIdsASString(requestWall.OwnerIds));
        statement.setDate(2, (LocalDate) requestWall.DateAfter);
        statement.setDate(3, (Date) requestWall.DateBefore);
        statement.setInt(3,   requestWall.Count);
        statement.setInt(3, requestWall.Offset);


        ResultSet result = statement.executeQuery();
        statement.close();
        return fetchPosts(result);
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

    private String getPostsAsString(Collection<Post> posts) {
        Collection<String> postsAsString = new ArrayList<>();
        for (Post post : posts) {
            postsAsString.add(post.toSqlValues());
        }
        return String.join(",", postsAsString);
    }

    Post fetchPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.PostId = resultSet.getInt(PostField.POST_ID.getVal());
        post.OwnerId = resultSet.getInt(PostField.OWNER_ID.getVal());
        post.Content = resultSet.getString(PostField.CONTENT.getVal());
        post.Timestamp = resultSet.getDate(PostField.TIMESTAMP.getVal());
        post.IsRedacted = resultSet.getBoolean(PostField.IS_REDACTED.getVal());
        post.IsRemoved = resultSet.getBoolean(PostField.IS_REMOVED.getVal());
        post.IsCommentable = resultSet.getBoolean(PostField.IS_COMMENTABLE.getVal());
        post.CountLikes = resultSet.getInt(PostField.COUNT_LIKES.getVal());
        post.CountReposts = resultSet.getInt(PostField.COUNT_REPOSTS.getVal());


        return post;
    }


}
