package co.edu.eci.arep.microframeworksweb;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {

    @Test
    void testQueryStringParsing() {
        Request request = new Request("name=John&age=30", "");
        assertEquals("John", request.getParam("name"));
        assertEquals("30", request.getParam("age"));
    }

    @Test
    void testParseQueryStringInvalidFormat() {
        Request req = new Request("invalidData", null);
        assertNull(req.getParam("invalidData"));
    }

    @Test
    void testRequestBody() {
        String body = "{\"message\":\"Hello\"}";
        Request request = new Request("", body);
        assertEquals(body, request.getBody());
    }

    @Test
    void testGetParamWhenNoData() {
        Request req = new Request("", null);
        assertNull(req.getParam("any"));
    }
}

