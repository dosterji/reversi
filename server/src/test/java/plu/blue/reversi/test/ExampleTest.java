package plu.blue.reversi.test;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import plu.blue.reversi.server.LocalServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ExampleTest {

    private static HttpServer server = null;
    private static Client c = null;

    @BeforeClass
    public static void startServer() {
        server = LocalServer.startServer();
        c = ClientBuilder.newClient();
    }

    @AfterClass
    public static void stopServer() {
        server.shutdownNow();
    }

    @Test
    public void testExample() {
        WebTarget target = c.target("http://localhost:8080/reversi/example");
        String msg = target.request(MediaType.TEXT_PLAIN).get(String.class);
        Assert.assertEquals("This is an example response!", msg);
    }

}