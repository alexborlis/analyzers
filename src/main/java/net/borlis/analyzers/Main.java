package net.borlis.analyzers;

import net.borlis.analyzers.server.ServerInstance;

/**
 * Created by user on 05.10.15.
 */
public class Main {

    private ServerInstance serverInstance;

    public static void main(String[] args) {
        ServerInstance serverInstance = new ServerInstance(9999, 9998, "123456", "123456", "/keystore.jks", "http/1.1");
    }
}
