package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.practical3.handlers.MainServlet;




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

        runServer(port, contextStr);
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
