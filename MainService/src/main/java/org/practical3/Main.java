package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.practical3.handlers.PostService.*;
import org.practical3.handlers.UserService.FollowersServlet;
import org.practical3.handlers.UserService.SubscriptionServlet;
import org.practical3.handlers.UserService.SubscriptionsUserServiceServlet;
import org.practical3.handlers.UserService.UserServiceServlet;

import org.practical3.utils.PropertyManager;

import javax.servlet.Servlet;


public class Main {


    private static Server server;

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


    public static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8026);
        String contextPath = PropertyManager.getPropertyAsString("server.contextPath", "/");

        init(port,contextPath);

        try
        {
            server.start();
            System.out.println( String.format("MainService server start on port %d", port ));

            server.join();
        }catch(Throwable t){
            System.out.println( String.format("Error while stopping server: %s", t.getMessage()));
        }

    }

    public static void init(int port, String contextStr){
        server = new Server(port);

        setContext(contextStr);
        setServlets();

    }




    private static void setContext(String contextStr ) {

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

    }

    private static void setServlets()
    {
        setServlet(new PostsServlet(),"/posts/");
        setServlet(new DoLikeServlet(),"/posts/like");
        setServlet(new RestoreServlet(),"/posts/restore");
        setServlet(new RemoveServlet(),"/posts/remove");
        setServlet(new DoRepostServlet(),"/posts/repost");
        setServlet(new SearchPostsServlet(),"/posts/search");
        setServlet(new GetOwnerServlet(),"/posts/get_owner");
        setServlet(new FeedServlet(),"/feed/");
        setServlet(new WallServlet(),"/wall/");


        setServlet(new UserServiceServlet(), "/users");
        setServlet(new FollowersServlet(), "/getfollowers/*");
        setServlet(new SubscriptionServlet(), "/getsubscriptions/*");

    }


    private static void setServlet(Servlet servlet, String path ) {

        ServletHolder servletHolder = new ServletHolder(servlet);
        context.addServlet(servletHolder,path);

    }

    public static void stopServer() {
        try {
            if(server.isRunning()){
                server.stop();
            }
        } catch (Exception e) {
            System.out.println( String.format("Error while stopping server: %s", e.getMessage()));
        }
    }
}
