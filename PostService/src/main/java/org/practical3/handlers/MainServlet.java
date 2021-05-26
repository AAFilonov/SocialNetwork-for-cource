package org.practical3.handlers;

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
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/JavaPractice";
    static final String USER = "postgres";
    static final String PASS = "1";
    Connection db;




    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init =)");
        try{
            this.db = DriverManager.getConnection(DB_URL,USER,PASS);
            log("Db init sucess");

        } catch (SQLException ex) {
            System.out.println( ex.getMessage());
            log("Db init fail: "+ex.getMessage());
        }

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
            String str =  db.nativeSQL("select * from db.posts");

            Statement statement = db.createStatement();
            ResultSet result =  statement.executeQuery(str);
            String output="";
            while (result.next()){
                output += result.getString("text")+"\n";
            }

            resp.getWriter().write("data from db:\n" + output);

        } catch (SQLException ex) {
            log("Get fail: "+ex.getMessage());
        }


    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }
}