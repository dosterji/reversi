package plu.blue.reversi.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("example")
public class ExampleResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "This is an example response!";
    }
}

