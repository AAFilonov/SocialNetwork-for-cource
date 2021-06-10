package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.practical3.utils.Commons;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.handlers.PostsServlet;

import org.practical3.utils.PropertyManager;

import javax.servlet.Servlet;


public class Main {


    private static Server server;

    private static ServletContextHandler context;

    public static void main(String[] args)
    {
      PropertyManager.load("./post.props");
      initDB();
        if( Commons.dataBaseManager !=null)  runServer();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stopServer();

            }
        },"Stop Jetty Hook"));
    }


    public static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8027);
        String contextPath = PropertyManager.getPropertyAsString("server.contextPath", "/");

        initServer(port,contextPath);

        try
        {
            server.start();
            System.out.println( String.format("PostService server start on port %d", port ));

            server.join();
        }catch(Throwable t){
            System.out.println( String.format("Error while stopping server: %s", t.getMessage()));
        }

    }

    public static void initServer(int port, String contextStr){
        server = new Server(port);

        setContext(contextStr);
        setServlets();

    }


    private static void initDB() {
        try {
            String DB_URL =   PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
            String DB_Name =   PropertyManager.getPropertyAsString("database.database", "JavaPractice");
            String User =   PropertyManager.getPropertyAsString("database.user", "postgres");
            String Password =   PropertyManager.getPropertyAsString("database.password", "1");

            Commons.dataBaseManager = new PostsDataBaseManager(DB_URL,DB_Name,User,Password);

        }
       catch (Exception ex){
           System.out.println( String.format("Error while connect to database: %s", ex.getMessage()));
       }

    }



    private static void setContext(String contextStr ) {

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

    }

    private static void setServlets()
    {
        setServlet(new PostsServlet(),"/posts/*");

    }


    private static void setServlet(Servlet servlet,String path ) {

        ServletHolder servletHolder = new ServletHolder(servlet);
        context.addServlet(servletHolder,path);

    }

    public static void stopServer() {
        try {
            if(server.isRunning()){
                server.stop();
                Commons.dataBaseManager.close();
            }
        } catch (Exception e) {
            System.out.println( String.format("Error while stopping server: %s", e.getMessage()));
        }
    }
}
