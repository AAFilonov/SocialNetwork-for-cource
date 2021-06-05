package com.github.michael_sharko;

import com.github.michael_sharko.handlers.users.DeleteUserServlet;
import com.github.michael_sharko.handlers.users.RegisterUserServlet;
import com.github.michael_sharko.handlers.users.SearchUserServlet;
import com.github.michael_sharko.handlers.users.UpdateUserServlet;

import com.github.michael_sharko.handlers.SubscribesServlet;

import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.PropertyManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main
{
    private static Server server;

    public static void main(String[] args)
    {
        PropertyManager.load("./main.props");
        runServer();

        Runtime.getRuntime().addShutdownHook(
            new Thread(new Runnable()
            {
                public void run()
                {
                    stopServer();
                }
            })
        );
    }

    private static void runServer()
    {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8080);
        String contextPath = PropertyManager.getPropertyAsString("server.contextPath", "/");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath);

        context.addServlet(new ServletHolder(new RegisterUserServlet()), "/register");
        context.addServlet(new ServletHolder(new SearchUserServlet()), "/search");
        context.addServlet(new ServletHolder(new UpdateUserServlet()), "/update");
        context.addServlet(new ServletHolder(new DeleteUserServlet()), "/delete");
        context.addServlet(new ServletHolder(new SubscribesServlet()), "/subscribes");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });

        runServer(port, handlers);
    }

    private static void runServer(int port, HandlerList handlers)
    {
        server = new Server(port);
        server.setHandler(handlers);

        String dbUrl = PropertyManager.getPropertyAsString("database.server", "localhost");
        String dbUser = PropertyManager.getPropertyAsString("database.user", "postgres");
        String dbPassword = PropertyManager.getPropertyAsString("database.password", "postgres");

        DatabaseManager.connectTo(dbUrl, dbUser, dbPassword);

        try
        {
            server.start();
            System.out.println("Listening port: " + port);
        }
        catch (Exception e)
        {
            System.out.print("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void stopServer()
    {
        try
        {
            if (DatabaseManager.isConnected())
                DatabaseManager.disconnect();

            if (server.isRunning())
                server.stop();

        }
        catch (Exception e)
        {
            System.out.print("Error while stopping server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
