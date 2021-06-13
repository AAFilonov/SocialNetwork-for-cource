package org.practical3.utils.testing;

import org.practical3.Main;
import org.practical3.utils.PropertyManager;

public class StaticServerForTests {

    static private boolean isStarted = false;


    public static void start() {

        if (!isStarted) {
            run();
            isStarted = true;
        }

    }

    private static void run() {
        PropertyManager.load("./main.props");
        Thread newThread = new Thread(() -> {
            try {
                Main.runServer();

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Main.stopServer();

                    }
                }, "Stop Jetty Hook"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newThread.start();
    }
}

