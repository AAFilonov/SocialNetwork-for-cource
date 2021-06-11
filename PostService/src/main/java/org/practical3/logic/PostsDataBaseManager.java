package org.practical3.logic;


import org.practical3.model.data.PostField;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.WallRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PostsDataBaseManager extends DataBaseManager {
    public PostsDataBaseManager(String DB_URL, String DB_Name, String user, String password) throws SQLException {
        super(DB_URL, DB_Name, user, password);
    }

    public java.sql.Connection getConnection() {
        return super.Connection;
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


    public Collection<Post> getPosts(Collection<Integer> ids, Integer count, Integer offset) throws SQLException {


        String query = String.format("select * from db.posts WHERE post_id IN (%s) LIMIT %d OFFSET %d",

                getIdsASString(ids),
                count, offset);
        Statement statement = Connection.createStatement();

        ResultSet result = statement.executeQuery(query);
        ArrayList<Post> items = fetchPosts(result);
        statement.close();
        return items;


    }

    public int insertPosts(Collection<Post> posts) throws SQLException {
        PreparedStatement statement = super.Connection.prepareStatement
                (String.format("INSERT INTO db.posts VALUES %s",
                        getPostsAsString(posts))
                );

        return statement.executeUpdate();
    }

    public int updatePosts(Collection<Post> posts) throws SQLException {
        String sql = "update db.posts set " +
                "owner_id = COALESCE(? , owner_id ) ," +
                "content = COALESCE(?,content )," +
                "post_timestamp = COALESCE(?,post_timestamp  ), " +
                "\"isRemoved\" = COALESCE(?,\"isRemoved\") ," +
                "isredacted = COALESCE(?,isredacted) ," +
                "\"isCommentable\"= COALESCE(?,\"isCommentable\") ," +
                "\"CountLikes\" = COALESCE(?,\"CountLikes\"), " +
                "\"CountReposts\"= COALESCE(?,\"CountReposts\") " +
                "where post_id = ?;";
        int affectedRows = 0;
        for (Post post : posts) {
            PreparedStatement statement = super.Connection.prepareStatement(sql);

            if ((post.OwnerId != null)) statement.setInt(1, post.OwnerId);
            else statement.setNull(1, Types.INTEGER);
            if ((post.Content != null)) statement.setString(2, post.Content);
            else statement.setNull(2, Types.LONGVARCHAR);
            if ((post.Timestamp != null)) statement.setTimestamp(3, Timestamp.from(post.Timestamp));
            else statement.setNull(3, Types.TIMESTAMP);
            if ((post.IsRemoved != null)) statement.setBoolean(4, post.IsRemoved);
            else statement.setNull(4, Types.BOOLEAN);
            if ((post.IsRedacted != null)) statement.setBoolean(5, post.IsRedacted);
            else statement.setNull(5, Types.BOOLEAN);
            if ((post.IsCommentable != null)) statement.setBoolean(6, post.IsCommentable);
            else statement.setNull(6, Types.BOOLEAN);
            if ((post.CountLikes != null)) statement.setInt(7, post.CountLikes);
            else statement.setNull(7, Types.INTEGER);
            if ((post.CountReposts != null)) statement.setInt(8, post.CountReposts);
            else statement.setNull(8, Types.INTEGER);
            statement.setInt(9, post.PostId);
            affectedRows += statement.executeUpdate();
            statement.close();
        }
        return affectedRows;

    }


    public int deletePosts(Collection<Integer> ids) throws SQLException {
        PreparedStatement statement = super.Connection.prepareStatement
                (String.format("DELETE from db.posts WHERE post_id IN (%s)",
                        getIdsASString(ids))
                );

        return statement.executeUpdate();

    }

    public Collection<Post> getWall(WallRequest wallRequest) throws SQLException {
        PreparedStatement statement = super.Connection.prepareStatement(String.format("SELECT * FROM db.posts " +
                "WHERE owner_id IN (%s) " +
                "AND post_timestamp BETWEEN ? AND ? " +
                "and \"isRemoved\" = 'false' " +
                "LIMIT ? OFFSET ?", getIdsASString(wallRequest.OwnerIds)));


        statement.setTimestamp(1, Timestamp.from(wallRequest.After));
        statement.setTimestamp(2, Timestamp.from(wallRequest.Before));
        statement.setInt(3, wallRequest.Count);
        statement.setInt(4, wallRequest.Offset);


        ResultSet result = statement.executeQuery();

        ArrayList<Post> posts = fetchPosts(result);
        statement.close();
        return posts;
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
        post.Timestamp = resultSet.getTimestamp(PostField.TIMESTAMP.getVal()).toInstant();
        post.IsRedacted = resultSet.getBoolean(PostField.IS_REDACTED.getVal());
        post.IsRemoved = resultSet.getBoolean(PostField.IS_REMOVED.getVal());
        post.IsCommentable = resultSet.getBoolean(PostField.IS_COMMENTABLE.getVal());
        post.CountLikes = resultSet.getInt(PostField.COUNT_LIKES.getVal());
        post.CountReposts = resultSet.getInt(PostField.COUNT_REPOSTS.getVal());


        return post;
    }


}
