package net.borlis.analyzers;

import net.borlis.analyzers.server.ServerInstance;
import org.eclipse.jetty.server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by user on 05.10.15.
 */
public class Main {

    private static ServerInstance serverInstance;
    private static Server server;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        serverInstance = new ServerInstance(9999, 9998, "123456", "123456", "/keystore.jks", "http/1.1");
        Future<Server> future = executor.submit(serverInstance);
        boolean listen = true;
        while (listen) {
            if (future.isDone()) {
                try {
                    //server = future.get();
                    listen = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    serverInstance.stop();
                }
            }
        }
    }
}
