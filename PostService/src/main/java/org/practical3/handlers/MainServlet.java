package org.practical3.handlers;

import org.practical3.common.DataBase;
import org.practical3.common.PostsDataBase;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.*;

@WebServlet("/hello")
public class MainServlet extends HttpServlet {

    //  Database credentials

    private PostsDataBase DB;

    public MainServlet(PostsDataBase db){
        DB =db;
    }



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Method service enter\n");
        super.service(req, resp);
        resp.getWriter().write("Method service exit\n");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String str  = DB.getData();
            resp.getWriter().write("data from db:\n" + str);

        } catch (SQLException ex) {
            log("Get fail: "+ex.getMessage());
            resp.getWriter().write("Error while getting data:\n" );
        }

    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }
}