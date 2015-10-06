package net.borlis.analyzers.server;


import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;
import java.util.concurrent.Callable;

/**
 * Created by user on 05.10.15.
 */
public class ServerInstance implements Callable<Server> {

    private Server server;
    private HttpConfiguration https;
    private Integer port;
    private Integer sslPort;
    private String keyStorePassword;
    private String keyStoreManagerPassword;
    private String keyStorePath;
    private String sslConnectionFactoryPath;


    public ServerInstance(
            Integer port,
            Integer sslPort,
            String keyStorePassword,
            String keyStoreManagerPassword,
            String keyStorePath,
            String sslConnectionFactoryPath
    ) {
        this.port = port;
        this.sslPort = sslPort;
        this.keyStorePassword = keyStorePassword;
        this.keyStoreManagerPassword = keyStoreManagerPassword;
        this.keyStorePath = keyStorePath;
        this.sslConnectionFactoryPath = sslConnectionFactoryPath;
    }

    private String start() {
        try {
            server = new org.eclipse.jetty.server.Server();
            ServerConnector connector = new ServerConnector(server);
            connector.setPort(port);
            https = new HttpConfiguration();
            https.addCustomizer(new SecureRequestCustomizer());
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(ServerInstance.class.getResource(keyStorePath).toExternalForm());
            sslContextFactory.setKeyStorePassword(keyStorePassword);
            sslContextFactory.setKeyManagerPassword(keyStoreManagerPassword);
            ServerConnector sslConnector = new ServerConnector(server,
                    new SslConnectionFactory(sslContextFactory, sslConnectionFactoryPath),
                    new HttpConnectionFactory(https));
            sslConnector.setPort(sslPort);
            server.setConnectors(new Connector[]{connector, sslConnector});
            WebAppContext context = new WebAppContext();
            context.setServer(server);
            context.setContextPath("/");
            ProtectionDomain protectionDomain = ServerInstance.class.getProtectionDomain();
            URL location = protectionDomain.getCodeSource().getLocation();
            context.setWar(location.toExternalForm());
            server.setHandler(context);
            server.start();
            return server.getURI() + " " + server.getState();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    private String stop() {
        try {
            server.stop();
            return server.getURI() + " " + server.getState();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }


    public Server call() throws Exception {
        if (!server.getState().equals("STARTED") || !server.getState().equals("STARTING")) {
            server.start();
        }
        return server;
    }
}
