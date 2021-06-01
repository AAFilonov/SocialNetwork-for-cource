package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.practical3.common.DataBaseManager;
import org.practical3.common.PostsDataBaseManager;
import org.practical3.handlers.MainServlet;

import org.practical3.utils.PropertyManager;

import javax.servlet.Servlet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main {


    private static Server server;
    private static DataBaseManager db;
    private static ServletContextHandler context;
    public static void main(String[] args) throws Exception
    {
      PropertyManager.load("./main.props");
      runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stopServer();

            }
        },"Stop Jetty Hook"));
    }


    private static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8080);
        String contextPath = PropertyManager.getPropertyAsString("server.contextPath", "/");

        runServer(port,contextPath);

    }



    private static void setConnection() {
        try {
            db = new PostsDataBaseManager();
        }
       catch (Exception ex){
           System.out.println( String.format("Error while connect to database: %s", ex.getMessage()));
       }

    }

    public static void runServer(int port, String contextStr)
    {
        server = new Server(port);
        setContext(contextStr);
        setConnection();
        try
        {
            server.start();
            System.out.println( String.format("PostService server start on port %d", port ));

            server.join();
        }catch(Throwable t){
            System.out.println( String.format("Error while stopping server: %s", t.getMessage()));
        }
    }



    private static void setContext(String contextStr ) {

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

        setServlets();
    }

    private static void setServlets() {
        setServlet(new MainServlet((PostsDataBaseManager) db),"/posts/*");
    }


    private static void setServlet(Servlet servlet,String path ) {

        ServletHolder servletHolder = new ServletHolder(servlet);
        context.addServlet(servletHolder,path);

    }

    public static void stopServer() {
        try {
            if(server.isRunning()){
                server.stop();
                db.close();
            }
        } catch (Exception e) {
            System.out.println( String.format("Error while stopping server: %s", e.getMessage()));
        }
    }
}
