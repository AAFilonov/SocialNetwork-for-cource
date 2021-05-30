package org.practical3;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.practical3.common.DataBase;
import org.practical3.common.PostsDataBase;
import org.practical3.handlers.MainServlet;
import org.practical3.model.ConfigData;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main {


    private static Server server;
    private static DataBase db;
    private static ServletContextHandler context;
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


    private static void runServer() {
        //TODO  сделать чтения файла конфигов из аргумента командной строки?
        Path configPath = Paths.get(System.getProperty("user.dir")+"/config.conf");
        ConfigData configData = readConfigs(configPath);

        if(configData!=null) {
            setConnection(configData);
            if(db!=null) runServer(configData.ServerPort, configData.ServerPath);
        }

    }

    private static ConfigData readConfigs(Path configFile) {
        try {
            //TODO временное решение, заменить на на json
            List<String> args = Files.readAllLines(configFile);
            String ServerPort = args.get(0);
            String ServerPath = args.get(1);
            String DB_URL = args.get(2);
            String DB_Name = args.get(3);
            String User = args.get(4);
            String Password = args.get(5);
            return new ConfigData(ServerPort, ServerPath, DB_URL, DB_Name, User, Password);
        }
        catch (IOException ex){
            System.out.println( String.format("Error while open config file: %s", ex.getMessage()));
            return null;
        }
    }

    private static void setConnection(ConfigData configData) {
        try {
            db = new PostsDataBase(configData);
        }
       catch (Exception ex){
           System.out.println( String.format("Error while connect to database: %s", ex.getMessage()));
       }

    }

    public static void runServer(int port, String contextStr)
    {
        server = new Server(port);

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

        setServlets();
        try
        {
            server.start();
            System.out.println( String.format("PostService server start on port %d", port ));

            server.join();
        }catch(Throwable t){
            System.out.println( String.format("Error while stopping server: %s", t.getMessage()));
        }
    }


    private static void setServlets() {
        setServlet(new MainServlet((PostsDataBase) db),"/posts/*");

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
