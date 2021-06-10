package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
<<<<<<< Updated upstream
import org.practical3.handlers.MainServlet;

=======
import org.eclipse.jetty.servlet.ServletHolder;
import org.practical3.handlers.FeedServlet;
import org.practical3.handlers.PostsServlet;
import org.practical3.handlers.UserService.GetFollowersUserServiceServlet;
import org.practical3.handlers.UserService.GetSubscriptionsUserServiceServlet;
import org.practical3.handlers.UserService.SubscriptionsUserServiceServlet;
import org.practical3.handlers.UserService.UserServiceServlet;
import org.practical3.handlers.WallServlet;
import org.practical3.utils.Commons;
import org.practical3.utils.HttpClientManager;
import org.practical3.utils.PropertyManager;
>>>>>>> Stashed changes



public class Main {


    private static Server server;

    public static void main(String[] args) throws Exception
    {

        runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {

                stopServer();

            }
        },"Stop Jetty Hook"));
    }

    public static void runServer(int port, String contextStr)
    {
        server = new Server(port);
        //регистрирует класс по пути /path
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(MainServlet.class, "/path");

        try
        {
            server.start();
          //  log.error("Server has started at port: " + port);
            //server.join();
        }catch(Throwable t){
            //log.error("Error while starting server", t);
        }
    }

    private static void runServer() {
        int port = 8026;
        String contextStr = "/";

<<<<<<< Updated upstream
        runServer(port, contextStr);
=======


    private static void setContext(String contextStr ) {

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

    }

    private static void setServlets()
    {
        setServlet(new PostsServlet(),"/posts/*");
        setServlet(new FeedServlet(),"/feed/*");
        setServlet(new WallServlet(),"/wall/*");

        setServlet(new UserServiceServlet(), "/users/*");
        setServlet(new GetFollowersUserServiceServlet(), "/getfollowers/*");
        setServlet(new GetSubscriptionsUserServiceServlet(), "/getsubscriptions/*");
        setServlet(new SubscriptionsUserServiceServlet(), "/subscriptions/*");
    }


    private static void setServlet(Servlet servlet, String path ) {

        ServletHolder servletHolder = new ServletHolder(servlet);
        context.addServlet(servletHolder,path);

>>>>>>> Stashed changes
    }

    public static void stopServer() {
        try {
            if(server.isRunning()){
                server.stop();
            }
        } catch (Exception e) {
         //   log.error("Error while stopping server", e);
        }
    }
}
