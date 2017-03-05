package plu.blue.reversi.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

public class LocalServer {

    public static final String BASE_URI = "http://localhost:8080/reversi";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("plu.blue.reversi.server.resources");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
    }
}