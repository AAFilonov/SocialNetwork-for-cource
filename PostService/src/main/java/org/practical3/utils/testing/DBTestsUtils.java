package org.practical3.utils.testing;

import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;

import org.practical3.utils.PropertyManager;

import java.sql.SQLException;
import java.util.Collection;


public class DBTestsUtils {


    public static void init() {
        PropertyManager.load("./post.props");
        String DB_URL = PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
        String DB_Name = "javaPracticeTest";
        String User = PropertyManager.getPropertyAsString("database.user", "postgres");
        String Password = PropertyManager.getPropertyAsString("database.password", "1");
        PostsDataBaseManager.init(DB_URL, DB_Name, User, Password);

    }

    public static void insertData(Collection<Post> data) {
        try {
            PostsDataBaseManager.insertPosts(data);
        } catch (Exception e) {
            //уже вставлен
        }
    }

    public static void cleanData(Collection<Integer> data) {
        try {
            PostsDataBaseManager.deletePosts(data);
        } catch (Exception e) {
            //уже удален
        }
    }

}