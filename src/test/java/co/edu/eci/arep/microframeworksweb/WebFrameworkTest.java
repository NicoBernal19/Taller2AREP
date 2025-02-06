package co.edu.eci.arep.microframeworksweb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class WebFrameworkTest {

    @BeforeAll
    static void setupRoutes() {
        WebFramework.getRoutes().put("GET", new HashMap<>());
        WebFramework.getRoutes().put("POST", new HashMap<>());

        WebFramework.getRoutes().get("GET").put("/hello", (req, resp) -> "Hello " + (req.getParam("name") != null ? req.getParam("name") : "World") + "!");
        WebFramework.getRoutes().get("POST").put("/submit", (req, resp) -> "Received: " + req.getParam("data"));
    }

    @Test
    void testHandleGetRequestWithName() {
        Request req = new Request("name=Lucas", null);
        Response resp = new Response();
        String response = WebFramework.getRoutes().get("GET").get("/hello").apply(req, resp);

        assertEquals("Hello Lucas!", response);
    }

    @Test
    void testHandleGetRequestWithoutName() {
        Request req = new Request("", null);
        Response resp = new Response();
        String response = WebFramework.getRoutes().get("GET").get("/hello").apply(req, resp);

        assertEquals("Hello World!", response);
    }
}
