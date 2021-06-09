package com.github.michael_sharko;

import com.github.michael_sharko.handlers.GetFollowersUserServiceServlet;
import com.github.michael_sharko.handlers.GetSubscriptionsUserServiceServlet;
import com.github.michael_sharko.handlers.SubscriptionsUserServiceServlet;
import com.github.michael_sharko.handlers.UserServiceServlet;
import com.github.michael_sharko.utils.DatabaseManager;
import com.github.michael_sharko.utils.PropertyManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    private static Server server;

    public static void main(String[] args) {
        PropertyManager.load("./user.props");
        runServer();

        Runtime.getRuntime().addShutdownHook(
                new Thread(Main::stopServer)
        );
    }

    public static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8080);
        String contextPath = PropertyManager.getPropertyAsString("server.contextPath", "/");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath);

        context.addServlet(new ServletHolder(new UserServiceServlet()), "/users");
        context.addServlet(new ServletHolder(new SubscriptionsUserServiceServlet()), "/subscriptions");
        context.addServlet(new ServletHolder(new GetSubscriptionsUserServiceServlet()), "/getsubscriptions");
        context.addServlet(new ServletHolder(new GetFollowersUserServiceServlet()), "/getfollowers");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{context});

        runServer(port, handlers);
    }

    public static void runServer(int port, HandlerList handlers) {
        server = new Server(port);
        server.setHandler(handlers);

        String dbUrl = PropertyManager.getPropertyAsString("database.server", "localhost");
        String dbUser = PropertyManager.getPropertyAsString("database.user", "postgres");
        String dbPassword = PropertyManager.getPropertyAsString("database.password", "postgres");

        DatabaseManager.connectTo(dbUrl, dbUser, dbPassword);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        try {
            if (DatabaseManager.isConnected())
                DatabaseManager.disconnect();

            if (server.isRunning())
                server.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
